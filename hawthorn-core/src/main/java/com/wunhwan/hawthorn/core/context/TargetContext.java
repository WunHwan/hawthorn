package com.wunhwan.hawthorn.core.context;

import com.wunhwan.hawthorn.core.metadata.TargetMetadata;
import com.wunhwan.hawthorn.core.transfer.RSocketClient;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface TargetContext {

    Class<?> type();

    TargetMetadata targetMetadata();

    RSocketClient socketClient();

}
