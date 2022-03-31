description = "RPC Framework base on RSocket application protocol."

plugins {
    id("org.springframework.boot") version "2.6.2" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
}

allprojects {
    group = "com.wunhwan"
    version = "2022.01.26-SNAPSHOT"

    extra.apply {
        set("Version.rsocket", "1.1.1")
        set("Version.eclipse-collections", "11.0.0")
        set("Version.commons-lang3", "3.12.0")
    }

    extra.apply {
        set("rsocket-core", "io.rsocket:rsocket-core:${extra.get("Version.rsocket")}")
        set("rsocket-transport-netty", "io.rsocket:rsocket-transport-netty:${extra.get("Version.rsocket")}")
        set(
            "eclipse-collections",
            "org.eclipse.collections:eclipse-collections:${extra.get("Version.eclipse-collections")}"
        )
        set(
            "eclipse-collections-api",
            "org.eclipse.collections:eclipse-collections-api:${extra.get("Version.eclipse-collections")}"
        )
        set(
            "commons-lang3",
            "org.apache.commons:commons-lang3:${extra.get("Version.commons-lang3")}"
        )

        set("jupiter-api", "org.junit.jupiter:junit-jupiter-api")
        set("jupiter-engine", "org.junit.jupiter:junit-jupiter-engine")
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

    configurations.all {
        resolutionStrategy {
            // make fail when dependency version conflict
            failOnVersionConflict()

            eachDependency {
                // fix；slf4j
                if (requested.group == "org.slf4j") {
                    useVersion("1.7.20")
                }
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