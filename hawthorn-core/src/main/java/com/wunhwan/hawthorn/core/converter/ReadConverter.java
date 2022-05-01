package com.wunhwan.hawthorn.core.converter;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since 2022.01.26
 */
public interface ReadConverter<T> {

    T convert(byte[] bytes);

    static String inputToString(byte[] bytes) {
        Objects.requireNonNull(bytes);

        return new String(bytes, StandardCharsets.UTF_8);
    }
}
