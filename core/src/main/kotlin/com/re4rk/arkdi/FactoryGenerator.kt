package com.re4rk.arkdi

import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.toTypeName

class FactoryGenerator {
    fun generate(function: KSFunctionDeclaration): TypeSpec {
        val returnTypeName = function.returnType?.toTypeName() ?: throw Exception("returnType is null")

        return TypeSpec.classBuilder("${function.simpleName.getShortName()}Factory")
            .addSuperInterface(returnTypeName)
            .addConstructor(function.parameters)
            .addProperties(function.parameters)
            .addGetter(function)
            .build()
    }

    private fun TypeSpec.Builder.addSuperInterface(typeName: TypeName): TypeSpec.Builder {
        val parameters = Factory::class.asClassName().parameterizedBy(typeName)

        return this.addSuperinterface(parameters)
    }

    private fun TypeSpec.Builder.addProperties(parameters: List<KSValueParameter>): TypeSpec.Builder {
        PropertyGenerator().generate(parameters)
            .forEach { propertySpec -> this.addProperty(propertySpec) }
        return this
    }

    private fun generateProviderType(typeName: TypeName): TypeName {
        return Provider::class.asClassName().parameterizedBy(typeName)
    }

    private fun TypeSpec.Builder.addConstructor(parameters: List<KSValueParameter>): TypeSpec.Builder {
        return this.primaryConstructor(
            FunSpec.constructorBuilder()
                .addParameters(parameters)
                .addStatement(parameters)
                .build(),
        )
    }

    private fun FunSpec.Builder.addParameters(parameters: List<KSValueParameter>): FunSpec.Builder {
        parameters.forEach {
            this.addParameter(
                it.name!!.asString(),
                generateProviderType(it.type.resolve().toTypeName()),
            )
        }
        return this
    }

    private fun FunSpec.Builder.addStatement(parameters: List<KSValueParameter>): FunSpec.Builder {
        parameters.forEach {
            this.addStatement("this.%N = %N", it.name!!.asString(), it.name!!.asString())
        }
        return this
    }

    private fun TypeSpec.Builder.addGetter(function: KSFunctionDeclaration): TypeSpec.Builder {
        val parameters = function.parameters.map {
            ParameterSpec.builder(
                it.name!!.asString(),
                generateProviderType(it.type.resolve().toTypeName()),
            ).build()
        }

        return this.addFunction(
            FunSpec.builder("get")
                .addModifiers(KModifier.OVERRIDE)
                .addParameters(parameters)
                .returns(function.returnType?.toTypeName() ?: throw Exception("returnType is null"))
                .addStatement(buildReturnStatementString(function))
                .build(),
        )
    }

    private fun buildReturnStatementString(function: KSFunctionDeclaration): String {
        val functionName = function.simpleName.getShortName()
        val parameters = function.parameters.joinToString { it.name!!.asString() }
        return "return $functionName.get($parameters)"
    }
}
