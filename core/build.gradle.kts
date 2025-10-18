plugins {
    `config-java`
}

dependencies {
    compileOnly(libs.bundles.adventure)

    compileOnly(libs.fusion.core)
    
    compileOnly(libs.google.gson)

    compileOnlyApi(libs.pl3xmap)
}