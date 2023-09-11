package com.re4rk.arkdi

import com.re4rk.arkdi.util.runCompile
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.assertj.core.api.Assertions.assertThat
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test

class ArkProcessorTest {
    @Test
    fun `어노테이션을 테스트한다`() {
        // given
        val source = toSource(
            """
        class KClass {
            fun foo() {
                // Classes from the test environment are visible to the compiled sources
                val testEnvClass = TestEnvClass()
            }
        }
            """.trimIndent(),
        )

        // when
        val result = runCompile { sources = listOf(source) }

        // then
        println(result.generatedFiles)
    }

    @Test
    fun `함수에 @Provides를 붙일 수 있다`() {
        // given
        val source = toSource(
            """
        object KClass {
            @Provides
            fun fakeFunction() = "fakeFunction"
        }
            """.trimIndent(),
        )

        // when
        val result = runCompile { sources = listOf(source) }

        // then
        assertThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)
    }

    private fun toSource(@Language(value = "kotlin") string: String) = SourceFile.kotlin("KClass.kt", string)

    private fun String.toSourceFile() = SourceFile.kotlin("KClass.kt", this)
}
