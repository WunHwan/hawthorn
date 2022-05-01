package com.wunhwan.hawthorn.core.metadata;

import com.wunhwan.hawthorn.core.annotation.RSocketService;
import io.rsocket.frame.FrameType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public class MethodMetadata {

    private final Method method;

    private final String route;

    private final int paramCount;

    private final FrameType frameType;

    private final Class<?> returnType;

    public MethodMetadata(Method method) {
        this.method = method;
        this.paramCount = method.getParameterCount();

        if (method.isAnnotationPresent(RSocketService.class)) {
            final RSocketService annotation = method.getAnnotation(RSocketService.class);

            this.route = annotation.endpoint();
        } else {
            this.route = method.getName();
        }


        final ParameterizedType returnParameterizedType = (ParameterizedType) method.getGenericReturnType();
        final Type[] actualTypeArguments = returnParameterizedType.getActualTypeArguments();
        if (actualTypeArguments.length == 0) {
            throw new IllegalArgumentException("method generic size must bigger to one");
        }

        // try to get real type from first index
        this.returnType = (Class<?>) actualTypeArguments[0];
        if (Void.class.equals(this.returnType)) {
            this.frameType = FrameType.REQUEST_FNF;
        } else if (Mono.class.isAssignableFrom(this.returnType)) {
            this.frameType = FrameType.REQUEST_RESPONSE;
        } else if (Flux.class.isAssignableFrom(this.returnType)) {
            this.frameType = FrameType.REQUEST_CHANNEL;
        } else {
            throw new RuntimeException("can not match return-type");
        }
    }

    public Method method() {
        return this.method;
    }

    public String route() {
        return this.route;
    }

    public int paramCount() {
        return this.paramCount;
    }

    public FrameType frameType() {
        return frameType;
    }

    public Class<?> returnType() {
        return this.returnType;
    }
}
