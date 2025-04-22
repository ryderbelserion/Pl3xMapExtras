plugins {
    id("root-plugin")
}

rootProject.group = "om.ryderbelserion.map"

val buildNumber: String? = System.getenv("BUILD_NUMBER")
val isPublishing: String? = System.getenv("IS_PUBLISHING")

rootProject.version = if (buildNumber != null && isPublishing == null) "${libs.versions.minecraft.get()}-$buildNumber" else "1.2.0"