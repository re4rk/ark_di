plugins {
    kotlin("jvm") version "1.9.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.10" apply false
    id("com.re4rk.plugin.ark-di-plugin") version "0.1" apply false
}

group = "com.re4rk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }

    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
