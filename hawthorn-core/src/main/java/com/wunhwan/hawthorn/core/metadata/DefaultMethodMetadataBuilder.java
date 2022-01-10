package com.wunhwan.hawthorn.core.metadata;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
final class DefaultMethodMetadataBuilder implements MethodMetadata.Builder {

    private Method method;

    private String route;

    @Override
    public MethodMetadata.Builder method(Method method) {
        Objects.requireNonNull(method);

        this.method = method;
        return this;
    }

    @Override
    public MethodMetadata.Builder route(String route) {
        Objects.requireNonNull(route);

        this.route = route;
        return this;
    }

    @Override
    public MethodMetadata build() {
        return new MethodMetadata() {
            @Override
            public Method method() {
                return method;
            }

            @Override
            public String route() {
                return route;
            }

            @Override
            public AnnotatedType[] annotatedType() {
                return method().getAnnotatedParameterTypes();
            }

            @Override
            public Class<?>[] parameterClass() {
                return method().getParameterTypes();
            }

            @Override
            public Class<?> returnClass() {
                return method().getReturnType();
            }
        };
    }
}
