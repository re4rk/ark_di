package com.re4rk.arkdi

import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.primaryConstructor

class FactoryGenerator {
    fun <T : Any> generate(clazz: KClass<T>): TypeSpec {
        return TypeSpec.classBuilder("Factory")
            .addSuperInterface(clazz.asTypeName())
            .addProperties(clazz.primaryConstructor)
            .build()
    }

    private fun TypeSpec.Builder.addSuperInterface(typeName: TypeName): TypeSpec.Builder {
        val factoryType = Factory::class.asClassName().parameterizedBy(typeName)

        return this.addSuperinterface(factoryType)
    }

    private fun TypeSpec.Builder.addProperties(constructor: KFunction<*>?): TypeSpec.Builder {
        if (constructor == null) return this

        return this.addProperties(PropertyGenerator().generate(constructor.parameters))
    }
}
