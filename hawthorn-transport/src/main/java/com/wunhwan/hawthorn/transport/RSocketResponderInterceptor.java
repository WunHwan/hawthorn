package com.wunhwan.hawthorn.transport;

import com.wunhwan.hawthorn.core.metadata.ServiceMetadata;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketServer;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public class RSocketResponderInterceptor {

    private final ServiceMetadata serviceMetadata;

    private final RSocketServer socketServer;

    public RSocketResponderInterceptor(ServiceMetadata serviceMetadata, RSocketServer socketServer) {
        this.serviceMetadata = serviceMetadata;
        this.socketServer = socketServer;
    }

    @RuntimeType
    public void intercept(@This Object self, @Origin Method method, @AllArguments Object[] args, @SuperMethod Method superMethod) {
    }

}
