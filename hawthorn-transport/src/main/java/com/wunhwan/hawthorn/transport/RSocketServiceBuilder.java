package com.wunhwan.hawthorn.transport;

import com.wunhwan.hawthorn.core.RSocketClient;
import com.wunhwan.hawthorn.core.metadata.ServiceMetadata;
import com.wunhwan.hawthorn.transport.tcp.TcpRSocketClientTransport;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public class RSocketServiceBuilder<T> {

    private final Object target;
    private final Class<T> service;
    private ServiceMetadata serviceMetadata;

    private RSocketServiceBuilder(Class<T> service) {
        this(null, service);
    }

    private RSocketServiceBuilder(Object target, Class<T> service) {
        this.target = target;
        this.service = service;
    }

    @SuppressWarnings("unchecked")
    public T build() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final boolean anInterface = Modifier.isInterface(service.getModifiers());
        final String hostname = serviceMetadata.getHostname();
        final int port = serviceMetadata.getPort();
        RSocketClient client = new DefaultRSocketClient(new TcpRSocketClientTransport(hostname, port));

        // guess service use jdk-proxy
        if (anInterface) {
            RSocketRequesterProxy proxy = new RSocketRequesterProxy(this.serviceMetadata, client);

            return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class[]{service}, proxy);
        }

        // use byte-buddy replace cglib library
        final RSocketResponderInterceptor responderInterceptor = new RSocketResponderInterceptor(this.serviceMetadata, null);

        return new ByteBuddy()
                // intercept method must be public level
                .subclass(service, new ConstructorStrategy.ForDefaultConstructor()).method(ElementMatchers.any().and(ElementMatchers.isPublic()))
                .intercept(MethodDelegation.to(responderInterceptor))
                .make()
                .load(service.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();
    }

    public static <T> RSocketServiceBuilder<T> service(Class<T> service) {
        RSocketServiceBuilder<T> builder = new RSocketServiceBuilder<>(service);
        builder.serviceMetadata = new ServiceMetadata(builder.service);

        return builder;
    }

}
