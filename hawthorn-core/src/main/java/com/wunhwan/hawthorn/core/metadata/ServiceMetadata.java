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
public class ServiceMetadata {

    private final Class<?> service;
    private final RSocketService rootMapping;
    private final Map<String, MethodMetadata> methodMetadataMap = new HashMap<>();

    public ServiceMetadata(Class<?> service) {
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

            final MethodMetadata methodMetadata = new MethodMetadata(method);
            String methodEndpoint = methodMetadata.endpoint();
            if (methodEndpoint == null) {
                methodEndpoint = methodMetadata.method().getName();
            }
            methodMetadataMap.put(methodEndpoint, methodMetadata);
        }
    }

    public String serviceId() {
        final String serviceId = rootMapping.serviceId();
        if (serviceId != null) {
            return serviceId;
        }

        return service.getCanonicalName();
    }

    public String version() {
        return rootMapping.version();
    }

    public String group() {
        return rootMapping.group();
    }

    public String endpoint() {
        return rootMapping.endpoint();
    }

    public RSocketMimeType dataEncodingType() {
        return rootMapping.dataEncodingType();
    }

    public RSocketMimeType acceptEncodingType() {
        return rootMapping.acceptEncodingType();
    }

    public List<MethodMetadata> getAllMethodMetadata() {
        return List.copyOf(methodMetadataMap.values());
    }

}
