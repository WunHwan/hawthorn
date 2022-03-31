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
public @interface RSocketClient {

    @AliasFor(value = RSocketService.class, attribute = "serviceId")
    String serviceId() default "";

    @AliasFor(value = RSocketService.class, attribute = "version")
    String version() default "0";

    @AliasFor(value = RSocketService.class, attribute = "group")
    String group() default "";

    @AliasFor(value = RSocketService.class, attribute = "endpoint")
    String endpoint() default "";

    @AliasFor(value = RSocketService.class, attribute = "dataEncoding")
    String dataEncode() default "json";

    @AliasFor(value = RSocketService.class, attribute = "acceptEncode")
    String acceptEncode() default "json";

    String name();

    String address();

    String port();

}
