import git.formatLog
import git.latestCommitHash
import git.latestCommitMessage

plugins {
    id("com.modrinth.minotaur") version "2.+"

    id("com.github.johnrengelman.shadow")

    `root-plugin`
}

val buildNumber: String = System.getenv("NEXT_BUILD_NUMBER") ?: "SNAPSHOT"

rootProject.version = "1.20.4-$buildNumber"

subprojects.forEach {
    it.project.version = rootProject.version
}

tasks {
    shadowJar {
        subprojects
            .filter { it.name != "common" }
            .forEach { dependsOn(":${it.name}:shadowJar") }
    }

    build {
        dependsOn(shadowJar)
    }
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))

    projectId.set(rootProject.name.lowercase())

    versionType.set("release")

    versionName.set("${rootProject.name} ${rootProject.version}")
    versionNumber.set(rootProject.version as String)

    changelog.set(formatLog(latestCommitHash(), latestCommitMessage(), rootProject.name))

    uploadFile.set(rootProject.projectDir.resolve("jars/${rootProject.name}-${rootProject.version}.jar"))

    gameVersions.set(listOf(
        "1.20.4"
        //"1.20.5"
    ))

    loaders.add("paper")
    loaders.add("purpur")

    autoAddDependsOn.set(false)
    detectLoaders.set(false)

    dependencies {
        required.project("pl3xmap")
    }
}