plugins {
    id("root-plugin")
}

dependencies {
    compileOnlyApi(libs.pl3xmap)

    compileOnly(libs.jetbrains)

    api(libs.jalu)
}