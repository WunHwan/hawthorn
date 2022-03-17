package com.wunhwan.hawthorn.core.metadata;

import com.wunhwan.hawthorn.core.annotation.RSocketService;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * todo...
 *
 * @author wunhwantseng@gmail.com
 * @since todo...
 */
public class DefaultServiceMetadata implements ServiceMetadata {

    private final Class<?> service;
    private final RSocketService rootMapping;
    private Map<String, MethodMetadata> methodMetadataMap = new HashMap<>();

    public DefaultServiceMetadata(Class<?> service) {
        this.service = service;

        if (!service.isAnnotationPresent(RSocketService.class)) {
            throw new IllegalArgumentException("class [" + service.getCanonicalName() + "] miss @RSocketService");
        }

        this.rootMapping = service.getAnnotation(RSocketService.class);

        for (Method method : service.getDeclaredMethods()) {
            if (Modifier.isNative(method.getModifiers())) {
                continue;
            }
            if (!Modifier.isPublic(method.getModifiers())) {
                continue;
            }

            final MethodMetadata methodMetadata = new DefaultMethodMetadata(method);
            String methodEndpoint = methodMetadata.endpoint();
            if (methodEndpoint == null) {
                methodEndpoint = methodMetadata.method().getName();
            }
            methodMetadataMap.put(methodEndpoint, methodMetadata);
        }
    }

    @Override
    public String serviceId() {
        final String serviceId = rootMapping.serviceId();
        if (serviceId != null) {
            return serviceId;
        }

        return service.getCanonicalName();
    }

    @Override
    public String version() {
        return rootMapping.version();
    }

    @Override
    public String group() {
        return rootMapping.group();
    }

    @Override
    public String endpoint() {
        return rootMapping.endpoint();
    }

    @Override
    public RSocketMimeType dataEncodingType() {
        return rootMapping.dataEncodingType();
    }

    @Override
    public RSocketMimeType acceptEncodingType() {
        return rootMapping.acceptEncodingType();
    }

    @Override
    public List<MethodMetadata> getAllMethodMetadata() {
        return List.copyOf(methodMetadataMap.values());
    }
}
