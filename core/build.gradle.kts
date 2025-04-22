plugins {
    id("root-plugin")
}

dependencies {
    compileOnlyApi(libs.pl3xmap)

    compileOnly(libs.bundles.adventure)

    compileOnly(libs.fusion.core)

    compileOnly(libs.jetbrains)
}