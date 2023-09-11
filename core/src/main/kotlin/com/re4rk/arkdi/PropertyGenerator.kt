package com.re4rk.arkdi

import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import kotlin.reflect.KParameter

class PropertyGenerator {
    fun generate(list: List<KParameter>): List<PropertySpec> {
        return list.map { parameter -> generate(parameter) }
    }

    private fun generate(kParameter: KParameter): PropertySpec {
        return PropertySpec.builder(
            kParameter.name!!,
            kParameter.type.asTypeName(),
        ).build()
    }
}
