package com.wunhwan.hawthorn.core.metadata;

import java.util.List;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public interface ServiceMetadata {

    String serviceId();

    String version();

    String group();

    String endpoint();

    RSocketMimeType dataEncodingType();

    RSocketMimeType acceptEncodingType();

    List<MethodMetadata> getAllMethodMetadata();

}
