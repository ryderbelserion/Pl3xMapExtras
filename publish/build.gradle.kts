plugins {
    alias(libs.plugins.minotaur)
    alias(libs.plugins.hangar)
}

val content: String = rootProject.file("CHANGELOG.md").readText(Charsets.UTF_8)

tasks {
    modrinth {
        token.set(System.getenv("MODRINTH_TOKEN"))

        projectId.set(rootProject.name.lowercase())

        versionType.set("release")

        versionName.set("${rootProject.name} ${rootProject.version}")
        versionNumber.set(rootProject.version as String)

        uploadFile.set(rootProject.projectDir.resolve("jars/${rootProject.name}-${rootProject.version}.jar"))
        changelog.set(content)

        syncBodyFrom.set(rootProject.file("README.md").readText(Charsets.UTF_8))

        gameVersions.set(listOf(libs.versions.minecraft.get()))

        loaders.addAll(listOf("purpur", "paper", "folia"))

        autoAddDependsOn.set(false)
        detectLoaders.set(false)

        dependencies {
            optional.project("griefprevention", "essentialsx", "worldguard", "claimchunk")

            required.project("pl3xmap")
        }
    }

    hangarPublish {
        publications.register("plugin") {
            apiKey.set(System.getenv("HANGAR_KEY"))

            id.set(rootProject.name.lowercase())

            version.set(rootProject.version as String)

            channel.set("Release")

            changelog.set(content)

            platforms {
                paper {
                    jar.set(rootProject.projectDir.resolve("jars/${rootProject.name}-${rootProject.version}.jar"))

                    platformVersions.set(listOf(libs.versions.minecraft.get()))

                    dependencies {
                        url("Pl3xMap", "https://modrinth.com/plugin/pl3xmap") {
                            required = true
                        }

                        hangar("Essentials") {
                            required = false
                        }

                        hangar("GriefPrevention") {
                            required = false
                        }

                        url("GriefDefender", "https://www.spigotmc.org/resources/1-12-2-1-21-griefdefender-claim-plugin-grief-prevention-protection.68900/") {
                            required = false
                        }

                        url("PlotSquared", "https://www.spigotmc.org/resources/plotsquared-v7.77506/") {
                            required = false
                        }

                        url("ClaimChunk", "https://www.spigotmc.org/resources/claimchunk.44458/") {
                            required = false
                        }

                        url("WorldGuard", "https://enginehub.org/worldguard#downloads") {
                            required = false
                        }
                    }
                }
            }
        }
    }
}