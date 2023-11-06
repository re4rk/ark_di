package com.re4rk.arkdi

import javax.inject.Inject
import kotlin.reflect.full.memberProperties

open class Injector {
    fun <T : Any> inject(a: T) {
        val clazz = Class.forName(a::class.qualifiedName)
        val fields = clazz.declaredFields
        for (field in fields) {
            if (field.isAnnotationPresent(Inject::class.java)) {
                field.isAccessible = true
                field[clazz] = newInstance(field.type)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> newInstance(clazz: Class<T>): T {
        val generator = this::class.memberProperties.first { it.name == clazz.name }
        return generator.getter.call(this) as T
    }
}
