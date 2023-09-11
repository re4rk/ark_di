package com.re4rk.arkdi

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class Provides(val value: String = "")
