dependencies {
    implementation("io.rsocket:rsocket-core:${rootProject.extra["rsocketVersion"]}")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.extra["junit-jupiter"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${rootProject.extra["junit-jupiter"]}")
}