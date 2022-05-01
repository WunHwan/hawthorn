package com.wunhwan.hawthorn.core.annotation;

import java.lang.annotation.*;

/**
 * todo...
 *
 * @author 开发-郑文焕
 * @since todo...
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AliasFor {

    Class<?> value();

    String attribute();

}
