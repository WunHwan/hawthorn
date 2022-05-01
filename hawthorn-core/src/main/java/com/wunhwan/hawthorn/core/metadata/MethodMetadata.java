package com.wunhwan.hawthorn.core.metadata;

import com.wunhwan.hawthorn.core.annotation.RSocketService;
import io.rsocket.frame.FrameType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;

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
        this.returnType = method.getReturnType();

        if (method.isAnnotationPresent(RSocketService.class)) {
            final RSocketService annotation = method.getAnnotation(RSocketService.class);

            this.route = annotation.endpoint();
        } else {
            this.route = method.getName();
        }

        if (Mono.class.equals(returnType)) {
            this.frameType = FrameType.REQUEST_RESPONSE;
        } else if (Flux.class.equals(returnType)) {
            this.frameType = FrameType.REQUEST_CHANNEL;
        } else if (Void.class.equals(returnType)) {
            this.frameType = FrameType.REQUEST_FNF;
        } else {
            this.frameType = FrameType.REQUEST_RESPONSE;
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

    public FrameType getFrameType() {
        return frameType;
    }

    public Class<?> returnType() {
        return this.returnType;
    }
}
