package com.wunhwan.hawthorn.core.metadata;

import com.wunhwan.hawthorn.core.annotation.RSocketService;
import io.netty.buffer.ByteBuf;
import io.rsocket.metadata.CompositeMetadata;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public class ServiceMetadata implements CompositeMetadata.Entry {

    private final Class<?> service;

    private final Map<String, MethodMetadata> methodMetadataMap = new HashMap<>();

    private final String serviceId;

    private final String version;

    private final String group;

    private final String endpoint;

    private final String dataEncoding;

    private final String acceptEncoding;

    /**
     * If Class of Service is Interface, that mean the proxy is requester, and method has {@link com.wunhwan.hawthorn.core.annotation.RSocketClient}
     */
    private final String hostname;

    public ServiceMetadata(Class<?> service) {
        this.service = service;

        if (!service.isAnnotationPresent(RSocketService.class)) {
            throw new IllegalArgumentException("class [" + service.getCanonicalName() + "] miss @RSocketService");
        }

        RSocketService rSocketService = service.getAnnotation(RSocketService.class);
        this.serviceId = rSocketService.serviceId();
        this.version = rSocketService.version();
        this.group = rSocketService.group();
        this.endpoint = rSocketService.endpoint();
        this.dataEncoding = rSocketService.dataEncoding();
        this.acceptEncoding = rSocketService.acceptEncoding();

        for (Method method : service.getDeclaredMethods()) {
            if (Modifier.isNative(method.getModifiers())) {
                continue;
            }
            if (!Modifier.isPublic(method.getModifiers())) {
                continue;
            }

            final MethodMetadata methodMetadata = new MethodMetadata(method);
            String methodEndpoint = methodMetadata.route();
            if (methodEndpoint == null) {
                methodEndpoint = methodMetadata.method().getName();
            }
            methodMetadataMap.put(methodEndpoint, methodMetadata);
        }
    }

    public String getServiceId() {
        if (this.serviceId != null) {
            return this.serviceId;
        }

        return service.getCanonicalName();
    }

    public String getVersion() {
        return version;
    }

    public String getGroup() {
        return group;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getDataEncoding() {
        return dataEncoding;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public String getHostname() {
        return hostname;
    }

    public List<MethodMetadata> getAllMethodMetadata() {
        return List.copyOf(methodMetadataMap.values());
    }

    public Optional<MethodMetadata> getMethodMetadata(Method method) {
        if (Objects.isNull(method)) {
            throw new NullPointerException("method can not be null");
        }

        String endpoint;
        if (method.isAnnotationPresent(RSocketService.class)) {
            endpoint = method.getAnnotation(RSocketService.class).endpoint();
        } else {
            endpoint = method.getName();
        }

        return Optional.ofNullable(methodMetadataMap.get(endpoint));
    }

    @Override
    public ByteBuf getContent() {
        return null;
    }

    @Override
    public String getMimeType() {
        return null;
    }

    private static
}
