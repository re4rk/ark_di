package com.re4rk.arkdi.util

import com.tschuchort.compiletesting.SourceFile
import org.intellij.lang.annotations.Language

internal fun toSource(@Language(value = "kotlin") string: String) = SourceFile.kotlin("KClass.kt", string)
