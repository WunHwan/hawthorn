dependencies {
    implementation("io.rsocket:rsocket-core")
    implementation("org.eclipse.collections:eclipse-collections-api:${rootProject.extra.get("eclipse-collections")}")
    implementation("org.eclipse.collections:eclipse-collections:${rootProject.extra.get("eclipse-collections")}")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}