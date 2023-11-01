plugins {
    kotlin("jvm") version "1.9.10"
    id("org.jetbrains.kotlin.kapt")
}

dependencies {

    // plugin
    implementation(gradleApi())

    // asm
    implementation("org.ow2.asm:asm:9.2")

    // android gradle plugin
    implementation("com.android.tools.build:gradle:3.3.0")
}
