package com.re4rk.arkdi

import com.re4rk.arkdi.util.runCompile
import com.re4rk.arkdi.util.toSource
import com.tschuchort.compiletesting.KotlinCompilation
import org.assertj.core.api.Assertions.assertThat
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
}
