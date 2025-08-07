
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        id("com.android.application") version "8.7.3" apply false
        id("org.jetbrains.kotlin.android") version "2.2.0" apply false
        id("org.jetbrains.kotlin.jvm") version "2.2.0" apply false
        id("org.jetbrains.kotlin.plugin.compose") version "2.2.0" apply false
    }
}

rootProject.name = "logic-tournament"

// Inclui os teus módulos no projeto
include("backend")
include("games")
include("common-serialization")
include("common")
include(":androidapp")