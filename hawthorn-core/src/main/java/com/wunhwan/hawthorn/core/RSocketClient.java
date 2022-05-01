package com.wunhwan.hawthorn.core;

import io.rsocket.Payload;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface RSocketClient {

    Mono<Void> fireAndForget(Payload payload);

    Mono<Payload> requestResponse(Payload payload);

    Flux<Payload> requestAndChannel(Publisher<Payload> payloads);

    default <T> Flux<Payload> requestAndSteam(Publisher<Payload> payloads) {
        return requestAndChannel(payloads);
    }

}
