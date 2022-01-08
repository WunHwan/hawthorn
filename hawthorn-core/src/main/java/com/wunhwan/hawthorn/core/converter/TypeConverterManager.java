package com.wunhwan.hawthorn.core.converter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public class TypeConverterManager implements ConverterContainer {

    private Map<Class<?>, ReadConverter<?>> inputTypeConverterMap = new HashMap<>();

    private Map<Class<?>, WriteConverter<?>> outputTypeConverterMap = new HashMap<>();

    @Override
    public void inputTypeConverterRegister(Collection<ReadConverter<?>> inputTypeConverters) {
        inputTypeConverters.forEach(converter -> inputTypeConverterMap.put(converter.getClass(), converter));
    }

    @Override
    public void outputTypeConverterRegister(Collection<WriteConverter<?>> outputTypeConverters) {
        outputTypeConverters.forEach(converter -> outputTypeConverterMap.put(converter.getClass(), converter));
    }

    @Override
    public Map<Class<?>, ReadConverter<?>> getReadOnlyInputTypeConverter() {
        return Map.copyOf(inputTypeConverterMap);
    }

    @Override
    public Map<Class<?>, WriteConverter<?>> getReadOnlyOutputTypeConverters() {
        return Map.copyOf(outputTypeConverterMap);
    }
}
