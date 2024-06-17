import git.formatLog
import git.latestCommitHash
import git.latestCommitMessage

plugins {
    id("io.papermc.hangar-publish-plugin") version "0.1.2"
    id("com.modrinth.minotaur") version "2.+"

    id("io.github.goooler.shadow")

    `root-plugin`
}

val buildNumber: String = if (System.getenv("BUILD_NUMBER") != null) System.getenv("BUILD_NUMBER") else "SNAPSHOT"

rootProject.version = "${libs.versions.minecraft.get()}-$buildNumber"

val content: String = formatLog(latestCommitHash(), latestCommitMessage(), rootProject.name)

subprojects.filter { it.name != "api" }.forEach {
    it.project.version = rootProject.version
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))

    projectId.set(rootProject.name.lowercase())

    versionType.set("release")

    versionName.set("${rootProject.name} ${rootProject.version}")
    versionNumber.set(rootProject.version as String)

    changelog.set(content)

    uploadFile.set(rootProject.projectDir.resolve("jars/${rootProject.name}-${rootProject.version}.jar"))

    gameVersions.set(listOf(libs.versions.minecraft.get()))

    loaders.addAll(listOf("purpur", "paper", "folia"))

    autoAddDependsOn.set(false)
    detectLoaders.set(false)

    dependencies {
        optional.version("GriefPrevention")

        required.version("Pl3xMap")
    }
}