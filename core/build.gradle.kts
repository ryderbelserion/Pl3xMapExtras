plugins {
    `config-java`
}

repositories {
    maven("https://libraries.minecraft.net/")
}

dependencies {
    compileOnly(libs.bundles.adventure)
    compileOnly(libs.fusion.core)
    compileOnly(libs.google.gson)
    compileOnly(libs.brigadier)

    compileOnlyApi(libs.pl3xmap)
}