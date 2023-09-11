package com.re4rk.arkdi.util

import com.re4rk.arkdi.ArkDIProcessorProvider
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.kspSourcesDir
import com.tschuchort.compiletesting.symbolProcessorProviders
import java.io.File

internal val kspWorkingDir = File("build/tmp/kotlin-compile-test")

internal fun runCompile(block: KotlinCompilation.() -> Unit): KotlinCompilation.Result {
    val compilation = KotlinCompilation().apply {
        inheritClassPath = true
        symbolProcessorProviders = listOf(ArkDIProcessorProvider())

        workingDir = kspWorkingDir
        block()
    }

    return compilation.compile()
}

fun KotlinCompilation.getGeneratedFiles(): List<File> {
    this.apply {
        workingDir = kspWorkingDir
    }
    return this.kspSourcesDir.listFilesRecursively()
}
