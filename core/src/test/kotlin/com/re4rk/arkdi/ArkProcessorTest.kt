package com.re4rk.arkdi

import com.re4rk.arkdi.util.getGeneratedFiles
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
        assertThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)
    }

    @Test
    fun `함수에 @Provides를 붙일 수 있다`() {
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

    @Test
    fun `함수에 @Provides를 붙이면 팩토리 함수를 만든다`() {
        // given
        val source = toSource(
            """
        import com.re4rk.arkdi.Provides
        
        class KClass {
            @Provides
            fun test() = "test"
        }
            """.trimIndent(),
        )
        // when
        val result = runCompile { sources = listOf(source) }

        // then
        assertThat(result.exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)

        // and
        assertThat(KotlinCompilation().getGeneratedFiles().size).isEqualTo(1)
    }
}
