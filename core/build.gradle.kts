plugins {
    `java-plugin`
}

project.description = "The core for Pl3xMapExtras."
project.group = "${rootProject.group}.common"
project.version = rootProject.version

repositories {
    maven("https://libraries.minecraft.net/")
}

dependencies {
    api(project(":api"))

    implementation(libs.hikari.cp)

    compileOnly(libs.brigadier)
}