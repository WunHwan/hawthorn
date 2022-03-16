package com.wunhwan.hawthorn.core.transfer;

import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.frame.decoder.PayloadDecoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * todo...
 *
 * @author 开发-郑文焕
 * @since todo...
 **/
final class DefaultRSocketClient implements RSocketClient {

    private final Mono<RSocket> connector;

    public DefaultRSocketClient(RSocketClientTransport transport) {
        this.connector = RSocketConnector.create()
                .payloadDecoder(PayloadDecoder.ZERO_COPY)
                .connect(transport);
    }

    @Override
    public Mono<Void> fireAndForget(byte[] bytes) {
        return Mono.defer(() -> connector)
                .flatMap(rSocket -> {

                    return rSocket.fireAndForget(null);
                });
    }

    @Override
    public <T> Mono<T> requestAndResponse(byte[] bytes, Class<T> returnType) {
        return null;
    }

    @Override
    public <T> Flux<T> requestAndChannel(byte[] bytes, Class<T> returnType) {
        return null;
    }
}
