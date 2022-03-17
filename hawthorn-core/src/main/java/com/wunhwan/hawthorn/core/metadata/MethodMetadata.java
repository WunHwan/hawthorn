package com.wunhwan.hawthorn.core.metadata;

import io.rsocket.frame.FrameType;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public interface MethodMetadata {

    Method method();

    String endpoint();

    int paramCount();

    Optional<FrameType> frameType();

    Class<?> returnType();

}
