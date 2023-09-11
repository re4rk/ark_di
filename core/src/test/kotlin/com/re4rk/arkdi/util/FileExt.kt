package com.re4rk.arkdi.util

import java.io.File

internal fun File.listFilesRecursively(): List<File> {
    return listFiles()?.flatMap { file ->
        if (file.isDirectory) {
            file.listFilesRecursively()
        } else {
            listOf(file)
        }
    } ?: emptyList()
}
