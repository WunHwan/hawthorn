package com.wunhwan.hawthorn.transport.server;

import com.wunhwan.hawthorn.core.metadata.ServiceMetadata;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationTargetException;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 **/
public class RSocketServiceBuilder<T> {

    private final T instance;
    private ServiceMetadata serviceMetadata;

    private RSocketServiceBuilder(T instance) {
        this.instance = instance;
    }

    public T build() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final String hostname = serviceMetadata.getHostname();
        final int port = serviceMetadata.getPort();

        // guess service is rsocket-server
        // use byte-buddy replace cglib library
        final RSocketResponderInterceptor responderInterceptor = new RSocketResponderInterceptor(this.serviceMetadata, null);

        return (T) new ByteBuddy()
                // intercept method must be public level
                .subclass(instance.getClass(), new ConstructorStrategy.ForDefaultConstructor()).method(ElementMatchers.any().and(ElementMatchers.isPublic()))
                .intercept(MethodDelegation.to(responderInterceptor))
                .make()
                .load(instance.getClass().getClassLoader())
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();
    }

    public static <T> RSocketServiceBuilder<T> service(T instance) {
        return new RSocketServiceBuilder<>(instance);
    }

}
