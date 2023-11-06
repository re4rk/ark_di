import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0" apply true
    `kotlin-dsl`

    id("maven-publish")
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

    // junit
    implementation("junit:junit:4.13.2")

    // assertj
    implementation("org.assertj:assertj-core:3.21.0")

    // javassist
    testImplementation("org.javassist:javassist:3.27.0-GA")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

group = "com.re4rk"
version = "0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

gradlePlugin {
    plugins {
        register("ArkDiPlugin") {
            id = "com.re4rk.plugin.ark-di-plugin"
            implementationClass = "com.re4rk.plugin.ArkDiPlugin"
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}
