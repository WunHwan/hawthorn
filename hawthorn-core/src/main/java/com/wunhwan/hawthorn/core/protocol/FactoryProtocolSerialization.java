package com.wunhwan.hawthorn.core.protocol;

import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.map.MutableMap;

import java.util.Optional;
import java.util.ServiceLoader;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public final class FactoryProtocolSerialization {

    private static final MutableMap<String, ProtocolSerializable> FACTORIES = Maps.mutable.empty();

    static {
        // SPI lookup ProtocolSerializable Factory
        for (ProtocolSerializable protocolSerializable : ServiceLoader.load(ProtocolSerializable.class)) {
            FACTORIES.put(protocolSerializable.protocol(), protocolSerializable);
        }
    }

    public static Optional<ProtocolSerializable> lookup(String protocol) {
        return Optional.ofNullable(FACTORIES.get(protocol));
    }

}
