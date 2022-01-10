package com.wunhwan.hawthorn.core.metadata;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public interface MethodMetadata {

    Method method();

    String route();

    AnnotatedType[] annotatedType();

    Class<?>[] parameterClass();

    Class<?> returnClass();

    static MethodMetadata.Builder builder() {
        return new DefaultMethodMetadataBuilder();
    }

    interface Builder {

        MethodMetadata.Builder method(Method method);

        MethodMetadata.Builder route(String route);

        MethodMetadata build();
    }

}
