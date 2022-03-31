package com.wunhwan.hawthorn.core.metadata;

import com.wunhwan.hawthorn.core.annotation.RSocketService;
import io.rsocket.frame.FrameType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public class MethodMetadata {

    private final Method method;

    private final String endpoint;

    private final int paramCount;

    private final FrameType frameType;

    private final Class<?> returnType;

    public MethodMetadata(Method method) {
        this.method = method;
        this.paramCount = method.getParameterCount();
        this.returnType = method.getReturnType();

        if (method.isAnnotationPresent(RSocketService.class)) {
            final RSocketService annotation = method.getAnnotation(RSocketService.class);

            this.endpoint = annotation.endpoint();
        } else {
            this.endpoint = null;
        }

        if (Mono.class.equals(returnType)) {
            this.frameType = FrameType.REQUEST_RESPONSE;
        } else if (Flux.class.equals(returnType)) {
            this.frameType = FrameType.REQUEST_CHANNEL;
        } else {
            this.frameType = FrameType.REQUEST_RESPONSE;
        }
    }

    public Method method() {
        return this.method;
    }

    public String endpoint() {
        return this.endpoint;
    }

    public int paramCount() {
        return this.paramCount;
    }

    public Optional<FrameType> frameType() {
        return Optional.of(frameType);
    }

    public Class<?> returnType() {
        return this.returnType;
    }
}
