dependencies {
    // core
    implementation("${rootProject.extra.get("rsocket-core")}")

    // extension util
    implementation("${rootProject.extra.get("eclipse-collections")}")
    implementation("${rootProject.extra.get("eclipse-collections-api")}")

    // test
    testImplementation("${rootProject.extra.get("jupiter-engine")}")
    testImplementation("${rootProject.extra.get("jupiter-api")}")
}