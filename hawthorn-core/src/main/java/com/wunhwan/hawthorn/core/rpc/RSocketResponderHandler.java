package com.wunhwan.hawthorn.core.rpc;

import io.rsocket.RSocket;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public class RSocketResponderHandler implements RSocket {

    /**
     * RSocket Client
     */
    protected final RSocket requester;


    public RSocketResponderHandler(RSocket requester) {
        this.requester = requester;
    }


}
