package com.wunhwan.hawthorn.transport.ws;

import com.wunhwan.hawthorn.core.transfer.RSocketClientTransport;
import io.rsocket.DuplexConnection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * todo...
 *
 * @author 开发-郑文焕
 * @since todo...
 **/
public class WebsocketRSocketClientTransport implements RSocketClientTransport {

    @Override
    public Mono<Void> fireAndForget(byte[] bytes) {
        return null;
    }

    @Override
    public <T> Mono<T> requestAndResponse(byte[] bytes, Class<T> returnType) {
        return null;
    }

    @Override
    public <T> Flux<T> requestAndChannel(byte[] bytes, Class<T> returnType) {
        return null;
    }

    @Override
    public Mono<DuplexConnection> connect() {
        return null;
    }
}
