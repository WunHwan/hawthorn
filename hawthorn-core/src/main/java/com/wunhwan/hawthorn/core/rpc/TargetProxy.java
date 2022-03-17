package com.wunhwan.hawthorn.core.rpc;

import com.wunhwan.hawthorn.core.context.TargetContext;
import com.wunhwan.hawthorn.core.metadata.MethodMetadata;
import com.wunhwan.hawthorn.core.metadata.TargetMetadata;
import com.wunhwan.hawthorn.core.protocol.ProtocolSerializable;
import com.wunhwan.hawthorn.core.protocol.ProtocolSerializationFactory;
import com.wunhwan.hawthorn.core.transfer.RSocketClient;
import com.wunhwan.hawthorn.core.transfer.TargetDescribe;
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

    private final TargetContext targetContext;

    TargetProxy(TargetContext targetContext) {
        this.targetContext = targetContext;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isDefault()) {
            return method.invoke(proxy, args);
        } else if (method.getDeclaringClass().equals(Object.class)) {
            return method.invoke(this, args);
        }

        final TargetMetadata targetMetadata = targetContext.targetMetadata();
        Optional<MethodMetadata> existMetadata = targetMetadata.getMetadata(method);
        if (existMetadata.isEmpty()) {
            throw new IllegalArgumentException("can not found method:{ " + method.getName() + " } metadata");
        }

        // method metadata of proxy object
        final MethodMetadata metadata = existMetadata.get();
        // rsokcet transfer package
        TargetDescribe targetDescribe = new TargetDescribe(targetMetadata.protocol(), metadata.route(), Map.of());

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
