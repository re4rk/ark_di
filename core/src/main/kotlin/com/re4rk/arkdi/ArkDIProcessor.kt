package com.re4rk.arkdi

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.writeTo

class ArkDIProcessor : SymbolProcessor {
    private lateinit var codeGenerator: CodeGenerator
    private lateinit var logger: KSPLogger

    fun init(codeGenerator: CodeGenerator, logger: KSPLogger) {
        this.codeGenerator = codeGenerator
        this.logger = logger
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(Provides::class.java.canonicalName)
            .filterIsInstance<KSFunctionDeclaration>().toList()
            .map { function ->
                val qualifiedName: String = function.qualifiedName?.getQualifier().toString()
                val shortName: String = function.qualifiedName?.getShortName().toString()

                FileSpec.builder("com.re4rk.arkdi", "${shortName}Factory")
                    .addImport(qualifiedName, shortName)
                    .addType(FactoryGenerator().generate(function))
                    .build()
                    .writeTo(codeGenerator = codeGenerator, aggregating = false)
            }

        return emptyList()
    }

    override fun finish() {
        super.finish()
        logger.warn("Processor 끝")
    }

    override fun onError() {
        super.onError()
        logger.error("Processor 에러")
    }
}
