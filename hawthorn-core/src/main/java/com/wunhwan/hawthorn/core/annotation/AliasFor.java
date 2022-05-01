package com.wunhwan.hawthorn.core.annotation;

import java.lang.annotation.*;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AliasFor {

    Class<?> value();

    String attribute();

}
