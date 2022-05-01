package com.wunhwan.hawthorn.core.protocol;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public interface ProtocolSerializable {

    String protocol();

    byte[] serialize(Object obj);

    <T> T deserialize(byte[] bytes, Class<T> type);

}
