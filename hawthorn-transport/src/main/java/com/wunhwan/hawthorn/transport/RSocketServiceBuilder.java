package com.wunhwan.hawthorn.transport;

import com.wunhwan.hawthorn.core.RSocketClient;
import com.wunhwan.hawthorn.core.metadata.ServiceMetadata;
import com.wunhwan.hawthorn.transport.tcp.TcpRSocketClientTransport;

import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

/**
 * todo...
 *
 * @author 开发-郑文焕
 * @since todo...
 **/
public class RSocketServiceBuilder<T> {

    private final Class<T> service;
    private ServiceMetadata serviceMetadata;

    private RSocketServiceBuilder(Class<T> service) {
        this.service = service;
    }

    public static <T> RSocketServiceBuilder<T> service(Class<T> service) {
        RSocketServiceBuilder<T> builder = new RSocketServiceBuilder<>(service);
        builder.serviceMetadata = new ServiceMetadata(builder.service);

        return builder;
    }

    @SuppressWarnings("unchecked")
    public T build() {
        if (Modifier.isInterface(service.getModifiers())) {
            RSocketClient rSocketClient = new DefaultRSocketClient(new TcpRSocketClientTransport(serviceMetadata.getHostname()));
            RSocketRequesterProxy proxy = new RSocketRequesterProxy(this.serviceMetadata, rSocketClient);

            return (T) Proxy.newProxyInstance(
                    service.getClassLoader(),
                    new Class[]{service},
                    proxy
            );
        }

        return null;
    }

}
