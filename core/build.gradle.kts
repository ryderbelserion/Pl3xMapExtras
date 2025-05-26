plugins {
    `config-java`
}

dependencies {
    compileOnly(libs.bundles.adventure)

    compileOnly(libs.fusion.core)

    compileOnly(libs.annotations)

    compileOnlyApi(libs.pl3xmap)
}