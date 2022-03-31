package com.wunhwan.hawthorn.transport;

import com.wunhwan.hawthorn.core.RSocketClient;
import com.wunhwan.hawthorn.core.metadata.MethodMetadata;
import com.wunhwan.hawthorn.core.metadata.ServiceMetadata;
import com.wunhwan.hawthorn.core.protocol.ProtocolSerializable;
import com.wunhwan.hawthorn.core.protocol.ProtocolSerializationFactory;
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
final class MethodProxy implements InvocationHandler {

    private final ServiceMetadata serviceMetadata;
    private final RSocketClient socketClient;

    public MethodProxy(ServiceMetadata serviceMetadata, RSocketClient socketClient) {
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
        final String routeEndpoint = splicRoute(serviceMetadata, methodMetadata);
        // rsokcet transfer package
        TransportDescribe transportDescribe = new TransportDescribe(serviceMetadata.dataEncoding(), routeEndpoint, Map.of());

        final Optional<ProtocolSerializable> serializableOptional = ProtocolSerializationFactory.lookup(transportDescribe.getProtocol());
        if (serializableOptional.isEmpty()) {
            throw new IllegalArgumentException("can not match protocol:{ " + transportDescribe.getProtocol() + " } type");
        }
        final ProtocolSerializable protocolSerializable = serializableOptional.get();
        final byte[] bytes = protocolSerializable.serialize(transportDescribe);

        final Mono<Void> voidMono = fireAndForget(socketClient, bytes);

        if (isReactive(method.getReturnType())) {
            return voidMono;
        }
        return voidMono.block();
    }

    private static Mono<Void> fireAndForget(RSocketClient socketClient, byte[] bytes) {
        return socketClient.fireAndForget(null);
    }

    private static boolean isReactive(Class<?> clazz) {
        return CorePublisher.class.isAssignableFrom(clazz);
    }

    private static String splicRoute(ServiceMetadata serviceMetadata, MethodMetadata methodMetadata) {
        return StringUtils.join(serviceMetadata.serviceId(), methodMetadata.endpoint(), ".");
    }
}
