package com.wunhwan.hawthorn.transport;

import com.wunhwan.hawthorn.core.RSocketClient;
import com.wunhwan.hawthorn.core.metadata.MethodMetadata;
import com.wunhwan.hawthorn.core.metadata.ServiceMetadata;
import com.wunhwan.hawthorn.core.protocol.ProtocolSerializable;
import com.wunhwan.hawthorn.core.protocol.ProtocolSerializationFactory;
import io.rsocket.Payload;
import io.rsocket.frame.FrameType;
import io.rsocket.util.DefaultPayload;
import org.apache.commons.lang3.StringUtils;
import reactor.core.CorePublisher;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
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
    private final RSocketClient socketClient;

    public RSocketRequesterProxy(ServiceMetadata serviceMetadata, RSocketClient socketClient) {
        this.serviceMetadata = serviceMetadata;
        this.socketClient = socketClient;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
        TransportDescribe transportDescribe = new TransportDescribe(serviceMetadata.getDataEncoding(), routeing, Map.of());

        final Optional<ProtocolSerializable> serializableOptional = ProtocolSerializationFactory.lookup(transportDescribe.getProtocol());
        if (serializableOptional.isEmpty()) {
            throw new IllegalArgumentException("can not match protocol:{ " + transportDescribe.getProtocol() + " } type");
        }
        final ProtocolSerializable protocolSerializable = serializableOptional.get();
        final byte[] bytes = protocolSerializable.serialize(transportDescribe);
        Payload payload = DefaultPayload.create(bytes);


        FrameType frameType = methodMetadata.getFrameType();

        switch (frameType) {
            case REQUEST_FNF:
                return socketClient.fireAndForget(payload);
            case REQUEST_RESPONSE:
                return socketClient.requestAndResponse(payload);
            default:
                throw new IllegalArgumentException("not find support FrameType");
        }
    }

    private static Mono<Void> fireAndForget(RSocketClient socketClient, TransportDescribe transportDescribe, byte[] bytes) {
        return socketClient.fireAndForget(DefaultPayload.create(bytes));
    }

    private static boolean isReactive(Class<?> clazz) {
        return CorePublisher.class.isAssignableFrom(clazz);
    }

    private static String splicRoute(ServiceMetadata serviceMetadata, MethodMetadata methodMetadata) {
        return StringUtils.join(serviceMetadata.getServiceId(), methodMetadata.route(), ".");
    }
}
