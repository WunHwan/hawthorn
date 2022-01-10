package com.wunhwan.hawthorn.core.metadata;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public interface TargetMetadata {

    Class<?> type();

    String url();

    String protocol();

    String agreementType();

    void addMethodMetadata(Collection<MethodMetadata> collection);

    Optional<MethodMetadata> getMetadata(Method method);

}
