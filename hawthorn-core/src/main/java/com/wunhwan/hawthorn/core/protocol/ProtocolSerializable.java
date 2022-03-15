package com.wunhwan.hawthorn.core.protocol;

import java.util.function.Function;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public interface ProtocolSerializable {

    String protocol();

    byte[] serialize(Object obj);


}
