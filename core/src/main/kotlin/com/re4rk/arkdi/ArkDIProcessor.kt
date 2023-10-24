package com.re4rk.arkdi

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

class ArkDIProcessor : SymbolProcessor {
    private lateinit var codeGenerator: CodeGenerator
    private lateinit var logger: KSPLogger

    fun init(codeGenerator: CodeGenerator, logger: KSPLogger) {
        this.codeGenerator = codeGenerator
        this.logger = logger
        logger.warn("Processor 시작")
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(Provides::class.java.canonicalName)
            .filterIsInstance<KSFunctionDeclaration>()
            .map { function ->
                function.accept(FactoryVisitor(codeGenerator, logger), Unit)
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
