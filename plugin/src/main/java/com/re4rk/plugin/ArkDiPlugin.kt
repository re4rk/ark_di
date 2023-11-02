package com.re4rk.plugin

import com.re4rk.plugin.util.android
import com.re4rk.plugin.util.variants
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class ArkDiPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("[ArkDiPlugin] apply")
        project.android().variants().all { variant ->

            val myTask = "myFirstTask${variant.name.capitalize()}"
            val task = project.tasks.create(myTask)

            task.group = "MyPluginTasks"
            task.doLast {
                File("${project.projectDir.path}/myFirstGeneratedFile.txt").apply {
                    writeText("Hello Gradle!\nPrinted at: ${SimpleDateFormat("HH:mm:ss").format(Date())}")
                }
            }

            true
        }
    }
}
