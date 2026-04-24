plugins {
    `java-plugin`
}

project.description = "The api for Pl3xMapExtras."
project.group = "${rootProject.group}.api"

dependencies {
    compileOnlyApi(libs.bundles.kyori)
    compileOnlyApi(libs.fusion.kyori)
    compileOnlyApi(libs.pl3xmap)
}