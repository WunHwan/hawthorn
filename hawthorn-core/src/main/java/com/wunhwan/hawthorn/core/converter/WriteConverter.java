package com.wunhwan.hawthorn.core.converter;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public interface WriteConverter<T> {

    byte[] convert(T source);

    default byte[] outputToByte(String source) {
        Objects.requireNonNull(source);

        return source.getBytes(StandardCharsets.UTF_8);
    }

}
