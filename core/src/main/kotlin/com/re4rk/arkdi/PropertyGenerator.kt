package com.re4rk.arkdi

import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.toTypeName

class PropertyGenerator {
    fun generateProviderType(list: List<KSValueParameter>): List<PropertySpec> {
        return list.map { parameter -> generateProviderType(parameter) }
    }

    private fun generateProviderType(kParameter: KSValueParameter): PropertySpec {
        return PropertySpec
            .builder(
                kParameter.name!!.getShortName(),
                Provider::class.asClassName().parameterizedBy(kParameter.type.resolve().toTypeName()),
            )
            .mutable(false)
            .build()
    }
}
