plugins {
    kotlin("jvm") version "1.9.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0" apply false
    id("org.jetbrains.kotlin.kapt") version "1.9.10" apply false
}

group = "com.re4rk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}
