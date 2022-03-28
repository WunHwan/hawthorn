package com.wunhwan.hawthorn.core.annotation;

import com.wunhwan.hawthorn.core.metadata.RSocketMimeType;

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

    String version() default "0";

    String group() default "";

    String endpoint() default "";

    RSocketMimeType dataEncodingType() default RSocketMimeType.JSON;

    RSocketMimeType acceptEncodingType() default RSocketMimeType.JSON;

}
