package com.wunhwan.hawthorn.transport.rpc;

import com.wunhwan.hawthorn.core.context.TargetContext;
import com.wunhwan.hawthorn.core.metadata.MethodMetadata;
import com.wunhwan.hawthorn.core.metadata.ServiceMetadata;
import com.wunhwan.hawthorn.core.metadata.TargetMetadata;
import com.wunhwan.hawthorn.core.protocol.ProtocolSerializable;
import com.wunhwan.hawthorn.core.protocol.ProtocolSerializationFactory;
import com.wunhwan.hawthorn.transport.TargetDescribe;
import io.rsocket.core.RSocketClient;
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
final class TargetProxy implements InvocationHandler {

    private final ServiceMetadata serviceMetadata;

    public TargetProxy(ServiceMetadata serviceMetadata) {
        this.serviceMetadata = serviceMetadata;
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
        // rsokcet transfer package
        TargetDescribe targetDescribe = new TargetDescribe(methodMetadata.hget(), metadata.route(), Map.of());

        final Optional<ProtocolSerializable> serializableOptional = ProtocolSerializationFactory.lookup(targetMetadata.protocol());
        if (serializableOptional.isEmpty()) {
            throw new IllegalArgumentException("can not match protocol:{ " + targetMetadata.protocol() + " } type");
        }
        final ProtocolSerializable protocolSerializable = serializableOptional.get();
        final byte[] bytes = protocolSerializable.serialize(targetDescribe);

        final Mono<Void> voidMono = fireAndForget(targetContext.socketClient(), bytes);

        if (isReactive(method.getReturnType())) {
            return voidMono;
        }
        return voidMono.block();
    }

    private static Mono<Void> fireAndForget(RSocketClient socketClient, byte[] bytes) {
        return socketClient.fireAndForget(null); // todo
    }

    private static boolean isReactive(Class<?> clazz) {
        return CorePublisher.class.isAssignableFrom(clazz);
    }
}
