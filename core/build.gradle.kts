import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.10"
    id("org.jetbrains.kotlin.kapt")
}

group = "com.re4rk"
version = "1.0-SNAPSHOT"

dependencies {
    // kotlinpoet
    implementation("com.squareup:kotlinpoet-ksp:1.10.2")
    // ksp
    implementation("com.google.devtools.ksp", "symbol-processing-api", "1.9.10-1.0.13")

    // kotlin-compile-testing
    testImplementation("com.github.tschuchortdev", "kotlin-compile-testing-ksp", "1.5.0")

    // junit 5
    testImplementation("org.junit.jupiter", "junit-jupiter", "5.8.2")

    // assertj
    testImplementation("org.assertj", "assertj-core", "3.22.0")

    // mockk
    testImplementation("io.mockk:mockk:1.12.0")
    kaptTest("io.mockk:mockk:1.12.0")

    // Reflection
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.10")
    implementation(kotlin("reflect"))

    // Inject
    implementation("javax.inject:javax.inject:1")

    // kotlin test
    testImplementation(kotlin("test"))
}

tasks {
    test {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"

        configureEach {
            kotlinOptions.freeCompilerArgs += "-Xopt-in=com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview"
        }
    }
}
