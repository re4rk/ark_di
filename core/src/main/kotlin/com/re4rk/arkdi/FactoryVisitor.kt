package com.re4rk.arkdi

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.writeTo

class FactoryVisitor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : KSVisitorVoid() {

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
        logger.warn("visitFunctionDeclaration")
        val qualifiedName: String = function.qualifiedName?.getQualifier().toString()
        val shortName: String = function.qualifiedName?.getShortName().toString()

        FileSpec.builder("com.re4rk.arkdi", "${shortName}Factory")
            .addImport(qualifiedName, shortName)
            .addType(FactoryGenerator().generate(function))
            .build()
            .writeTo(codeGenerator = codeGenerator, aggregating = false)
    }

}
