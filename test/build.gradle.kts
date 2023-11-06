plugins {
    kotlin("jvm")

    // KSP
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"

    id("com.re4rk.plugin.ark-di-plugin")
}

group = "org.example"
version = "1.0-SNAPSHOT"

dependencies {
    // KSP
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.10-1.0.13")
    ksp(project(":core"))

    implementation(project(mapOf("path" to ":core")))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // inject
    implementation("javax.inject:javax.inject:1")
}

tasks.test {
    useJUnitPlatform()
}
