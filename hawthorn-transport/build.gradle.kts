dependencies {
    // module dep
    implementation(project(":hawthorn-core"))

    // core
    implementation("${rootProject.extra.get("rsocket-transport-netty")}")

    // test
    testImplementation("${rootProject.extra.get("jupiter-engine")}")
    testImplementation("${rootProject.extra.get("jupiter-api")}")
}