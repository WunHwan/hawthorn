dependencies {
    val rsocketVersion: String by rootProject.extra
    val junitJupiter:String by rootProject.extra

    implementation("org.springframework:spring-context")
    implementation("io.rsocket:rsocket-core:$rsocketVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiter")
}