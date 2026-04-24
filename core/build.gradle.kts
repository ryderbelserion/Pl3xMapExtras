plugins {
    `java-plugin`
}

repositories {
    maven("https://libraries.minecraft.net/")
}

dependencies {
    compileOnly(libs.brigadier)

    api(project(":api"))
}