package com.wunhwan.hawthorn.core.converter;

import java.util.Collection;
import java.util.Map;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public interface ConverterContainer {

    void inputTypeConverterRegister(Collection<ReadConverter<?>> inputTypeConverters);

    void outputTypeConverterRegister(Collection<WriteConverter<?>> outputTypeConverters);

    Map<Class<?>, ReadConverter<?>> getReadOnlyInputTypeConverter();

    Map<Class<?>, WriteConverter<?>> getReadOnlyOutputTypeConverters();

}
