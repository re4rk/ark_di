plugins {
    kotlin("jvm") version "1.9.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0" apply true
    `kotlin-dsl`
}

dependencies {

    // plugin
    implementation(gradleApi())

    // asm
    implementation("org.ow2.asm:asm:9.2")

    // build gradle
    implementation("com.android.tools.build:gradle:8.1.1")

    // android gradle plugin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
}

gradlePlugin {
    plugins {
        register("ArkDiPlugin") {
            id = "ArkDiPlugin"
            implementationClass = "com.re4rk.plugin.ArkDiPlugin"
        }
    }
}
