package com.re4rk.arkdi

import GradleTestRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class NoTransformTest {

    @get:Rule
    val testProjectDir = TemporaryFolder()

    lateinit var gradleRunner: GradleTestRunner

    @Before
    fun setup() {
        gradleRunner = GradleTestRunner(testProjectDir)
    }

    // Simple functional test to verify transformation.
    @Test
    fun testAssemble() {
        gradleRunner.addDependencies(
            "implementation 'androidx.appcompat:appcompat:1.1.0'",
            // "implementation 'com.google.dagger:hilt-android:LOCAL-SNAPSHOT'",
            // "annotationProcessor 'com.google.dagger:hilt-compiler:LOCAL-SNAPSHOT'",
        )
        gradleRunner.addAndroidOption(
            "buildFeatures.buildConfig = false",
        )

        val result = gradleRunner.build()
        val assembleTask = result.getTask(":assembleDebug")
        Assert.assertEquals(TaskOutcome.SUCCESS, assembleTask.outcome)
    }
}
