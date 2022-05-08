package com.wunhwan.hawthorn.transport.ws;

import io.rsocket.DuplexConnection;
import io.rsocket.transport.ClientTransport;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public class WebsocketRSocketClientTransport implements ClientTransport {

    private final WebsocketClientTransport transport;

    public WebsocketRSocketClientTransport(URI uri) {
        this.transport = WebsocketClientTransport.create(uri);
    }

    @Override
    public Mono<DuplexConnection> connect() {
        return transport.connect();
    }
}
