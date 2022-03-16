package com.wunhwan.hawthorn.core.metadata;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * todo...
 *
 * @author 开发-郑文焕
 * @since todo...
 **/
class DefaultMethodMetadataBuilderTest {

    @Test
    void build() throws NoSuchMethodException {
        Class<DefaultMethodMetadataBuilderTest> testClass = DefaultMethodMetadataBuilderTest.class;
        Method method = testClass.getDeclaredMethod("test", String.class);

        MethodMetadata methodMetadata = MethodMetadata.builder()
                .method(method)
                .route("/test")
                .build();

        assert Objects.nonNull(methodMetadata);
        System.out.println(methodMetadata);
    }

    private void test(String name) {
        System.out.println("this is a test method, method name:" + name);
    }
}