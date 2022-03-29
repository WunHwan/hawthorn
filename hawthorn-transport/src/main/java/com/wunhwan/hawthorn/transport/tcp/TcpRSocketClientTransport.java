package com.wunhwan.hawthorn.transport.tcp;

import com.wunhwan.hawthorn.core.RSocketClientTransport;
import io.rsocket.DuplexConnection;
import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Mono;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
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
