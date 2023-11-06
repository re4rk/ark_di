rootProject.name = "ark_di"
include("test")
include("core")
include("plugin")

pluginManagement {
    repositories {
        mavenLocal()
        google()
        gradlePluginPortal()
    }
}
