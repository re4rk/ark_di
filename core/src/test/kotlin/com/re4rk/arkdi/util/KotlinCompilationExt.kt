package com.re4rk.arkdi.util

import com.re4rk.arkdi.ArkDIProcessorProvider
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.symbolProcessorProviders

internal fun runCompile(block: KotlinCompilation.() -> Unit): KotlinCompilation.Result {
    val compilation = KotlinCompilation().apply {
        block()
        symbolProcessorProviders = listOf(ArkDIProcessorProvider())
    }

    return compilation.compile()
}
