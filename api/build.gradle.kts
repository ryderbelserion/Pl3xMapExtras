plugins {
    `java-plugin`
}

project.description = "The api for Pl3xMapExtras."
project.version = rootProject.property("api_version").toString()
project.group = "${rootProject.group}.api"

dependencies {
    compileOnlyApi(libs.bundles.global)
}