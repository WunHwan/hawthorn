package com.wunhwan.hawthorn.transport.ws;

import com.wunhwan.hawthorn.core.transfer.RSocketClientTransport;
import io.rsocket.DuplexConnection;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * todo...
 *
 * @author 开发-郑文焕
 * @since todo...
 **/
public class WebsocketRSocketClientTransport implements RSocketClientTransport {

    private final WebsocketClientTransport transport;

    public WebsocketRSocketClientTransport(URI uri) {
        this.transport = WebsocketClientTransport.create(uri);
    }

    @Override
    public Mono<DuplexConnection> connect() {
        return transport.connect();
    }
}
