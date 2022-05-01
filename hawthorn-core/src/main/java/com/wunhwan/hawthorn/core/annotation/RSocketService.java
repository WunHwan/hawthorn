package com.wunhwan.hawthorn.core.annotation;

import java.lang.annotation.*;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 2022.01.26
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RSocketService {

    String serviceId() default "";

    String version() default "1.0";

    String group() default "";

    String endpoint() default "";

    String dataEncoding() default "json";

    String acceptEncoding() default "json";

}
