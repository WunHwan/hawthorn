package com.wunhwan.hawthorn.core.converter;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * todo...
 *
 * @author WunHwan
 * @since todo...
 */
public interface ToObjectConverter<T> {

    T convert(byte[] bytes);

    static String byteToString(byte[] bytes) {
        Objects.requireNonNull(bytes);

        return new String(bytes, StandardCharsets.UTF_8);
    }
}
