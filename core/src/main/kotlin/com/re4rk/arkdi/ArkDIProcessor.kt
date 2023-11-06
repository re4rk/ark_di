package com.re4rk.arkdi

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

class ArkDIProcessor : SymbolProcessor {
    private lateinit var codeGenerator: CodeGenerator
    private lateinit var logger: KSPLogger

    fun init(codeGenerator: CodeGenerator, logger: KSPLogger) {
        this.codeGenerator = codeGenerator
        this.logger = logger
        logger.warn("Processor 시작")
    }

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger.warn("process 시작")
        val packagePath = "com.re4rk.arkdi"
        resolver.getSymbolsWithAnnotation(Provides::class.java.canonicalName)
            .filterIsInstance<KSFunctionDeclaration>().toList()
            .map { function ->
                logger.warn("function: $function")
                function.accept(FactoryVisitor(codeGenerator, logger, packagePath), Unit)
            }

        if (
            codeGenerator.generatedFile.isEmpty() &&
            resolver.getNewFiles().toList().none { it.fileName == "ArkComponent.kt" }
        ) {
            val provides = resolver.getNewFiles().filter { it.fileName.contains("provide") }.toList()
            ArkComponentGenerator().generate(codeGenerator, logger, provides)
        }

        resolver.getSymbolsWithAnnotation(Injectable::class.java.canonicalName)
            .filterIsInstance<KSClassDeclaration>().toList()
            .map { clazz ->
                logger.warn("clazz: $clazz")
                InjectableGenerator(codeGenerator).generate(clazz)
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
