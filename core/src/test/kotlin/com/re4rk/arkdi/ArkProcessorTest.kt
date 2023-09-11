package com.re4rk.arkdi

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders
import org.junit.jupiter.api.Test

class ArkProcessorTest {
    @Test
    fun `어노테이션을 테스트한다`() {
        val kotlinSource = SourceFile.kotlin(
            "KClass.kt",
            """
        class KClass {
            fun foo() {
                // Classes from the test environment are visible to the compiled sources
                val testEnvClass = TestEnvClass() 
            }
        }""",
        )

        val compilation = KotlinCompilation().apply {
            sources = listOf(kotlinSource)
            symbolProcessorProviders = listOf(ArkDIProcessorProvider())
        }

        val result = compilation.compile()

        println(result.generatedFiles)
    }
}
