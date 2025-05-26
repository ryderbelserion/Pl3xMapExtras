plugins {
    `config-java`
}

repositories {
    maven("https://api.modrinth.com/maven/")
}

dependencies {
    compileOnly(libs.bundles.adventure)

    compileOnly(libs.fusion.core)

    compileOnly(libs.annotations)

    compileOnlyApi(libs.pl3xmap)
}