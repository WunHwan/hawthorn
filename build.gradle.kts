description = "RPC Framework base on RSocket application protocol."

plugins {
    id("org.springframework.boot") version "2.6.2" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
}

allprojects {
    group = "com.wunhwan"
    version = "2022.01.26-SNAPSHOT"

    extra.apply {

    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "io.spring.dependency-management")

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    repositories {
        mavenLocal()

        maven(url = "https://maven.aliyun.com/repository/public")

        mavenCentral()
    }

    configure<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension> {
        imports(delegateClosureOf<io.spring.gradle.dependencymanagement.dsl.ImportsHandler> {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        })
    }

    // fix；slf4j
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.slf4j") {
                useVersion("1.7.20")
            }
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<GenerateModuleMetadata> {
        enabled = false
    }

    tasks.withType<Test> {
        testLogging.showExceptions = true

        useJUnitPlatform()
    }
}