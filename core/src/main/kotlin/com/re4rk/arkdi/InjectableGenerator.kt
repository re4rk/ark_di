package com.re4rk.arkdi

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.toClassName
import com.squareup.kotlinpoet.ksp.writeTo

class InjectableGenerator(
    private val codeGenerator: CodeGenerator,
) {
    fun generate(clazz: KSClassDeclaration) {
        val fileSpecBuilder =
            FileSpec.builder("com.re4rk.arkdi", "ArkDi_" + clazz.simpleName.getShortName())
        val classBuilder = TypeSpec.classBuilder("ArkDi_" + clazz.simpleName.getShortName())

        classBuilder.superclass(clazz.toClassName())
            .addInitializerBlock(
                CodeBlock.builder()
                    .addStatement("ArkComponent().inject(this)")
                    .build(),
            )

        fileSpecBuilder
            .addType(classBuilder.build())
            .build()
            .writeTo(codeGenerator = codeGenerator, aggregating = false)
    }
}
