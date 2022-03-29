package com.wunhwan.hawthorn.transport;

import com.wunhwan.hawthorn.core.RSocketClient;
import com.wunhwan.hawthorn.core.RSocketClientTransport;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.core.RSocketConnector;
import io.rsocket.frame.decoder.PayloadDecoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
final class DefaultRSocketClient implements RSocketClient {

    private final Mono<RSocket> connector;

    public DefaultRSocketClient(RSocketClientTransport transport) {
        this.connector = RSocketConnector.create()
                // Enable Zero-Copy
                .payloadDecoder(PayloadDecoder.ZERO_COPY)
                .connect(transport);
    }

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        return Mono.defer(() -> connector)
                .flatMap(rSocket -> rSocket.fireAndForget(payload));
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
