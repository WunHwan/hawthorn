package com.wunhwan.hawthorn.core.converter;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * todo...
 *
 * @author WunHwan
 * @since todo...
 */
public interface ToByteConverter<T> {

    byte[] convert(T source);

    default byte[] stringToByte(String source) {
        Objects.requireNonNull(source);

        return source.getBytes(StandardCharsets.UTF_8);
    }

}
