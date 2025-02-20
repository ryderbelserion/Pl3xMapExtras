plugins {
    `maven-publish`
    `java-library`
}

val buildNumber: String? = System.getenv("BUILD_NUMBER")

rootProject.version = if (buildNumber != null) "${libs.versions.minecraft.get()}-$buildNumber" else "1.2.1-FORK"

subprojects.filter { it.name != "api" }.forEach {
    it.project.version = rootProject.version
}

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "java-library")

    group = "com.ryderbelserion.map"
    description = "An addon adding extra additions to Pl3xMap."

    repositories {
        maven("https://repo.codemc.io/repository/maven-public")

        maven("https://repo.crazycrew.us/libraries")
        maven("https://repo.crazycrew.us/releases")

        maven("https://jitpack.io")

        exclusiveContent {
            forRepository {
                maven("https://api.modrinth.com/maven")
            }

            filter { includeGroup("maven.modrinth") }
        }

        mavenCentral()
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    tasks {
        compileJava {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(21)
        }

        javadoc {
            options.encoding = Charsets.UTF_8.name()
        }

        processResources {
            filteringCharset = Charsets.UTF_8.name()
        }
    }
}