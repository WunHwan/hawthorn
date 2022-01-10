package com.wunhwan.hawthorn.core.reflective;

import com.wunhwan.hawthorn.core.context.TargetContext;
import com.wunhwan.hawthorn.core.metadata.MethodMetadata;
import com.wunhwan.hawthorn.core.transfer.RSocketClient;
import reactor.core.CorePublisher;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
final class TargetProxy implements InvocationHandler {

    private final TargetContext targetContext;

    TargetProxy(TargetContext targetContext) {
        this.targetContext = targetContext;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        //
        Optional<MethodMetadata> existMetadata = targetContext.targetMetadata().getMetadata(method);
        if (existMetadata.isEmpty()) {
            throw new IllegalArgumentException("can not found method:{ " + method.getName() + " } metadata");
        }

        //
        MethodMetadata methodMetadata = existMetadata.get();
        RSocketClient socketClient = targetContext.socketClient();

        //
        Class<?> returnType = method.getReturnType();

        if (isReactive(returnType)) {

        }

        return null;
    }

    private static Mono<Void> fireAndForget(RSocketClient socketClient, byte[] bytes) {
        return socketClient.fireAndForget(bytes);
    }

    private static boolean isReactive(Class<?> clazz) {
        return CorePublisher.class.isAssignableFrom(clazz);
    }
}
