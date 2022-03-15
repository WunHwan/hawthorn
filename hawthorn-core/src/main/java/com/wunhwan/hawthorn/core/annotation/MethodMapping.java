package com.wunhwan.hawthorn.core.annotation;

import java.lang.annotation.*;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 2022.01.26
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodMapping {

    String route();

    String protocol();

}
