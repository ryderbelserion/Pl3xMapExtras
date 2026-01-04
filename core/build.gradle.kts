plugins {
    `java-plugin`
}

dependencies {
    compileOnly(libs.bundles.adventure)

    compileOnly(libs.fusion.core)

    compileOnlyApi(libs.pl3xmap)
}