allprojects {
    group = "com.wunhwan"
    version = "2022.01.26-SNAPSHOT"

    extra.apply {
        set("rsocketVersion", "1.1.1")
        set("junit-jupiter", "5.8.2")
    }
}

description = "RPC Framework base on RSocket application protocol."

subprojects {
    apply(plugin = "java-library")

    repositories {
        mavenLocal()

        maven(url = "https://maven.aliyun.com/repository/public")

        mavenCentral()
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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