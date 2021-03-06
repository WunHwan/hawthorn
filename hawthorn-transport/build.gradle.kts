dependencies {
    // module dep
    implementation(project(":hawthorn-core"))

    // core
    implementation("${rootProject.extra.get("rsocket-transport-netty")}")
    implementation("${rootProject.extra.get("byte-buddy")}")

    // util
    implementation("${rootProject.extra.get("commons-lang3")}")

    // test
    testImplementation("${rootProject.extra.get("jupiter-engine")}")
    testImplementation("${rootProject.extra.get("jupiter-api")}")
}