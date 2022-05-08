package com.wunhwan.hawthorn.transport.client;

import com.wunhwan.hawthorn.core.metadata.ServiceMetadata;
import com.wunhwan.hawthorn.transport.tcp.TcpRSocketClientTransport;
import io.rsocket.core.RSocketConnector;
import io.rsocket.frame.decoder.PayloadDecoder;

import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public class RSocketClientBuilder<T> {

    private final Class<T> service;
    private ServiceMetadata serviceMetadata;

    private RSocketClientBuilder(Class<T> service) {
        if (!Modifier.isInterface(service.getModifiers())) {
            throw new IllegalArgumentException("class type is not interface");
        }

        this.service = service;
    }

    @SuppressWarnings("unchecked")
    public T build() {
        final String hostname = serviceMetadata.getHostname();
        final int port = serviceMetadata.getPort();

        RSocketConnector.create()
                .payloadDecoder(PayloadDecoder.ZERO_COPY)
                .connect(new TcpRSocketClientTransport(hostname,port));

        final RSocketRequesterProxy proxy = new RSocketRequesterProxy(this.serviceMetadata, null);

        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, proxy);
    }

    public static <T> RSocketClientBuilder<T> service(Class<T> service) {
        RSocketClientBuilder<T> builder = new RSocketClientBuilder<>(service);
        builder.serviceMetadata = new ServiceMetadata(builder.service);

        return builder;
    }

}
