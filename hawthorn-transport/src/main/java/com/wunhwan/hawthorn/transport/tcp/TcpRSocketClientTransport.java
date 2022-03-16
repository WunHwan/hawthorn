package com.wunhwan.hawthorn.transport.tcp;

import com.wunhwan.hawthorn.core.transfer.RSocketClientTransport;
import io.rsocket.DuplexConnection;
import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Mono;

/**
 * todo...
 *
 * @author 开发-郑文焕
 * @since todo...
 **/
public class TcpRSocketClientTransport implements RSocketClientTransport {

    private final TcpClientTransport transport;

    public TcpRSocketClientTransport(String address, int port) {
        this.transport = TcpClientTransport.create(address, port);
    }

    @Override
    public Mono<DuplexConnection> connect() {
        return transport.connect();
    }
}
