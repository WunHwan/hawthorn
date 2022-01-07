package com.wunhwan.hawthorn.core.converter;

import java.util.Collection;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public interface ConverterContainer {

    void inputTypeConverterRegister(Collection<InputTypeConverter<?>> inputTypeConverters);

    void outputTypeConverterRegister(Collection<OutputTypeConverter<?>> outputTypeConverters);



}
