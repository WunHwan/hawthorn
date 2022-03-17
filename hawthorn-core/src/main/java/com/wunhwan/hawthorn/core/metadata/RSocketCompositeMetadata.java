package com.wunhwan.hawthorn.core.metadata;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.rsocket.metadata.CompositeMetadata;
import io.rsocket.metadata.CompositeMetadataCodec;

import java.util.HashMap;
import java.util.Map;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public class RSocketCompositeMetadata implements CompositeMetadata.Entry {

    private final Map<String, ByteBuf> metadata = new HashMap<>();
    private  RoutingMetadata routingMetadata;

    public static RSocketCompositeMetadata from(ByteBuf content) {
        RSocketCompositeMetadata compositeMetadata = new RSocketCompositeMetadata();
        compositeMetadata.load(content);

        return compositeMetadata;
    }

    public void load(ByteBuf content) {
        CompositeMetadata compositeMetadata = new CompositeMetadata(content, false);
        for (CompositeMetadata.Entry entry : compositeMetadata) {
            this.metadata.put(entry.getMimeType(), entry.getContent());
        }
    }


    public ByteBuf getMetadata(RSocketMimeType mimeType) {
        return metadata.get(mimeType.getType());
    }

    public RSocketCompositeMetadata addMetadata(CompositeMetadata.Entry entry) {
        metadata.put(entry.getMimeType(), entry.getContent());

        return this;
    }


    @Override
    public ByteBuf getContent() {
        final CompositeByteBuf compositeByteBuf = PooledByteBufAllocator.DEFAULT.compositeBuffer();

        for (Map.Entry<String, ByteBuf> entry : metadata.entrySet()) {
            CompositeMetadataCodec.encodeAndAddMetadata(compositeByteBuf, PooledByteBufAllocator.DEFAULT, entry.getKey(), entry.getValue());
        }
        return compositeByteBuf;
    }

    @Override
    public String getMimeType() {
        return null;
    }
}
