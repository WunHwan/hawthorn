package com.wunhwan.hawthorn.core.metadata;

import io.netty.buffer.ByteBuf;
import io.rsocket.metadata.CompositeMetadata;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public class RoutingMetadata implements  CompositeMetadata.Entry{
    @Override
    public ByteBuf getContent() {
        return null;
    }

    @Override
    public String getMimeType() {
        return null;
    }
}
