package com.re4rk.arkdi

import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.ksp.toTypeName

class PropertyGenerator {
    fun generate(list: List<KSValueParameter>): List<PropertySpec> {
        return list.map { parameter -> generate(parameter) }
    }

    private fun generate(kParameter: KSValueParameter): PropertySpec {
        return PropertySpec
            .builder(
                kParameter.name!!.getShortName(),
                kParameter.type.toTypeName(),
            )
            .mutable(true)
            .build()
    }
}
