import com.ryderbelserion.feather.tools.latestCommitHistory

plugins {
    alias(libs.plugins.minotaur)
    alias(libs.plugins.hangar)

    `java-plugin`
}

val buildNumber: String? = if (System.getenv("NEXT_BUILD_NUMBER") != null) System.getenv("NEXT_BUILD_NUMBER") else "SNAPSHOT"

rootProject.version = "${libs.versions.minecraft.get()}-$buildNumber"

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))

    projectId.set(rootProject.name.lowercase())

    versionType.set("release")

    versionName.set("${rootProject.name} ${rootProject.version}")
    versionNumber.set(rootProject.version as String)

    changelog.set(latestCommitHistory("5fd83e", rootProject.name, "ryderbelserion").replace("\n", ""))
    uploadFile.set(rootProject.projectDir.resolve("jars/${rootProject.name}-${rootProject.version}.jar"))

    syncBodyFrom.set(rootProject.file("README.md").readText(Charsets.UTF_8))

    gameVersions.set(listOf(libs.versions.minecraft.get()))

    loaders.addAll(listOf("purpur", "paper", "folia"))

    autoAddDependsOn.set(false)
    detectLoaders.set(false)

    dependencies {
        optional.version("griefprevention", "16.18.3")

        required.version("pl3xmap", "1.21-500")
    }
}