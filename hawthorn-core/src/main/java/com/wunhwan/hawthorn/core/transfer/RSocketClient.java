package com.wunhwan.hawthorn.core.transfer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public interface RSocketClient {

    Mono<Void> fireAndForget(byte[] bytes);

    <T> Mono<T> requestAndResponse(byte[] bytes, Class<T> returnType);

    <T> Flux<T> requestAndChannel(byte[] bytes, Class<T> returnType);

    default <T> Flux<T> requestAndSteam(byte[] bytes, Class<T> returnType) {
        return requestAndChannel(bytes, returnType);
    }

}
