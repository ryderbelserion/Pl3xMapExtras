import com.ryderbelserion.feather.includeProject

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Pl3xMapExtras"

pluginManagement {
    repositories {
        maven("https://repo.crazycrew.us/releases")

        gradlePluginPortal()
    }
}

plugins {
    id("com.ryderbelserion.feather-settings") version "0.0.4"
}

listOf("paper", "core").forEach(::includeProject)