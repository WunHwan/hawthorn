package com.wunhwan.hawthorn.transport.client;

import com.wunhwan.hawthorn.core.metadata.MethodMetadata;
import com.wunhwan.hawthorn.core.metadata.ServiceMetadata;
import com.wunhwan.hawthorn.core.protocol.FactoryProtocolSerialization;
import com.wunhwan.hawthorn.core.protocol.ProtocolSerializable;
import com.wunhwan.hawthorn.transport.TransportDescriber;
import io.rsocket.Payload;
import io.rsocket.core.RSocketClient;
import io.rsocket.frame.FrameType;
import io.rsocket.util.DefaultPayload;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
@SuppressWarnings({"all"})
final class RSocketRequesterProxy implements InvocationHandler {

    private final ServiceMetadata serviceMetadata;
    private final Mono<RSocketClient> rsocket;

    public RSocketRequesterProxy(ServiceMetadata serviceMetadata, Mono<RSocketClient> rsocket) {
        this.serviceMetadata = serviceMetadata;
        this.rsocket = rsocket;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // def
        final Class<?> returnType = method.getReturnType();

        if (method.isDefault()) {
            return method.invoke(proxy, args);
        } else if (method.getDeclaringClass().equals(Object.class)) {
            return method.invoke(this, args);
        }

        Optional<MethodMetadata> methodMetadataOpt = serviceMetadata.getMethodMetadata(method);
        if (methodMetadataOpt.isEmpty()) {
            throw new IllegalArgumentException("can not found method:{ " + method.getName() + " } metadata");
        }

        // method metadata of proxy object
        final MethodMetadata methodMetadata = methodMetadataOpt.get();
        // transport route endpoint
        final String routeing = splicRoute(serviceMetadata, methodMetadata);
        // rsokcet transfer package
        TransportDescriber transportDescriber = new TransportDescriber(serviceMetadata.getDataEncoding(), routeing, Map.of());

        Optional<ProtocolSerializable> serializableOptional = FactoryProtocolSerialization.lookup(transportDescriber.getProtocol());
        if (serializableOptional.isEmpty()) {
            throw new IllegalArgumentException("can not match protocol:{ " + transportDescriber.getProtocol() + " } type");
        }
        final ProtocolSerializable protocolSerializable = serializableOptional.get();

        // RSocket Transport FrameType
        FrameType frameType = methodMetadata.frameType();
        // remote procedure cell by rsocket client
        final Mono<?> remoteProcedureCell = Mono.defer(() -> {
            final byte[] bytes = protocolSerializable.serialize(transportDescriber);

            return rsocketRemote(rsocket, frameType, DefaultPayload.create(bytes));
        });

        // response handler
        // if return-type is void,return do nothing
        if (FrameType.REQUEST_FNF.equals(frameType)) {
            return remoteProcedureCell;
        }
        // orelse parser body by return-type
        return remoteProcedureCell.flatMap(it -> {
            final Payload payload = (Payload) it;
            final String data = payload.getDataUtf8();

            return Mono.fromCallable(() -> {
                final Object deserialize = protocolSerializable.deserialize(data.getBytes(StandardCharsets.UTF_8), methodMetadata.returnType());

                return deserialize;
            });
        });
    }

    private static String splicRoute(ServiceMetadata serviceMetadata, MethodMetadata methodMetadata) {
        return StringUtils.join(serviceMetadata.getServiceId(), methodMetadata.route(), ".");
    }

    private static Mono<?> rsocketRemote(Mono<RSocketClient> rsocket, FrameType frameType, Payload payload) {
        final Mono<Payload> payloadMono = Mono.just(payload);

        // Match FrameType
        return Mono.defer(() -> {
            switch (frameType) {
                case REQUEST_FNF:
                    return rsocket.flatMap(client -> client.fireAndForget(payloadMono));
                case REQUEST_RESPONSE:
                    return rsocket.flatMap(client -> client.requestResponse(payloadMono));
                default: {
                    return Mono.error(new IllegalArgumentException("not find support FrameType"));
                }
            }
        });
    }
}
