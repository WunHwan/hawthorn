dependencies {
    // module dep
    implementation(project(":hawthorn-core"))
    implementation(project(":hawthorn-transport"))

    // test
    testImplementation("${rootProject.extra.get("jupiter-engine")}")
    testImplementation("${rootProject.extra.get("jupiter-api")}")
}