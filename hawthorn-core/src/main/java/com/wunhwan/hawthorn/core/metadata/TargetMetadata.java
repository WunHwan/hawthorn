package com.wunhwan.hawthorn.core.metadata;

import com.wunhwan.hawthorn.core.converter.ConverterContainer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public class TargetMetadata {

    private Class<?> type;

    private Map<Method, MethodMetadata> methodMethodMetadataMap = new HashMap<>();

    private ConverterContainer converterContainer;

}
