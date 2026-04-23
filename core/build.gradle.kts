plugins {
    `java-plugin`
}

repositories {
    maven("https://libraries.minecraft.net/")
}

dependencies {
    compileOnly(libs.bundles.kyori)
    compileOnly(libs.fusion.kyori)
    compileOnly(libs.brigadier)

    compileOnlyApi(libs.pl3xmap)

    api(project(":api"))
}