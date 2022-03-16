package com.wunhwan.hawthorn.transport.tcp;

import com.wunhwan.hawthorn.core.transfer.RSocketClientTransport;
import io.rsocket.DuplexConnection;
import reactor.core.publisher.Mono;

/**
 * todo...
 *
 * @author 开发-郑文焕
 * @since todo...
 **/
public class TcpRSocketClientTransport implements RSocketClientTransport {

    @Override
    public Mono<DuplexConnection> connect() {
        return null;
    }
}
