package com.wunhwan.hawthorn.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * todo...
 *
 * @author 开发-郑文焕
 * @since todo...
 **/
public class AnnotationUtil {

    public static Map<String, String> attributes(Annotation annotation) {
        Class<? extends Annotation> clazz = annotation.getClass();
        Method[] declaredMethods = clazz.getDeclaredMethods();

        Map<String,String> attributes = new HashMap<>(declaredMethods.length);

        for (Method method : clazz.getDeclaredMethods()) {

        }
    }

}
