plugins {
    `java-plugin`
}

dependencies {
    compileOnly(libs.bundles.kyori)
    compileOnly(libs.fusion.kyori)

    compileOnlyApi(libs.pl3xmap)

    compileOnlyApi(project(":api"))
}