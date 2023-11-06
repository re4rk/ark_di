package com.re4rk.arkdi

import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ksp.writeTo

class ArkComponentGenerator {
    fun generate(
        codeGenerator: CodeGenerator,
        logger: KSPLogger,
        newFiles: List<KSFile>,
    ) {
        val fileSpecBuilder = FileSpec.builder("com.re4rk.arkdi", "ArkComponent")
        val typeSpecBuilder = TypeSpec.classBuilder("ArkComponent")

        logger.warn("ArkComponentGenerator")
        val classes = newFiles.flatMap {
            it.declarations.map { declaration -> declaration as KSClassDeclaration }
        }

        typeSpecBuilder.superclass(Injector::class.asClassName())

        val parameterMap = classes.associateBy {
            Provider::class.asClassName().parameterizedBy(
                it.getDeclaredFunctions()
                    .first { it.simpleName.asString() == "get" }.returnType?.toTypeName()!!,
            )
        }

        classes.forEach {
            processKsFile(fileSpecBuilder, typeSpecBuilder, it, parameterMap)
        }

        fileSpecBuilder
            .addType(typeSpecBuilder.build())
            .build()
            .writeTo(codeGenerator = codeGenerator, aggregating = false)
    }

    private fun processKsFile(
        fileSpecBuilder: FileSpec.Builder,
        typeSpecBuilder: TypeSpec.Builder,
        ksClassDeclaration: KSClassDeclaration,
        classMap: Map<ParameterizedTypeName, KSClassDeclaration>,
    ) {
        ksClassDeclaration.accept(
            object : KSVisitorVoid() {

                override fun visitClassDeclaration(
                    classDeclaration: KSClassDeclaration,
                    data: Unit,
                ) {
                    val shortName = classDeclaration.simpleName.getShortName()
                    val packageName = classDeclaration.packageName.asString()

                    fileSpecBuilder.addImport(packageName, shortName)

                    val args = classDeclaration.primaryConstructor!!.parameters
                        .mapNotNull { classMap[it.type.toTypeName()] }

                    val argsString = args.joinToString { "get$it()" }

                    typeSpecBuilder.addFunction(
                        FunSpec.builder("get$shortName")
                            .addStatement("return $shortName($argsString)")
                            .build(),
                    )

                    super.visitClassDeclaration(classDeclaration, data)
                }
            },
            Unit,
        )
    }
}
