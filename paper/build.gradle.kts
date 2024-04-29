plugins {
    id("io.github.goooler.shadow")

    alias(libs.plugins.run.paper)

    `paper-plugin`
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots/")

    maven("https://repo.glaremasters.me/repository/bloodshot")

    maven("https://repo.papermc.io/repository/maven-public/")

    maven("https://repo.essentialsx.net/releases/")

    maven("https://repo.crazycrew.us/snapshots/")

    maven("https://maven.enginehub.org/repo/")

    maven("https://repo.olziedev.com/")
}

dependencies {
    implementation(project(":common"))

    implementation("com.ryderbelserion.vital", "paper", "1.0-snapshot")

    // Warps
    compileOnly("com.olziedev", "playerwarps-api", "6.30.0") {}
    compileOnly("net.essentialsx", "EssentialsX", "2.20.1") {
        exclude("org.bstats", "*")
        exclude("org.spigotmc", "*")
    }

    // Claims
    compileOnly("com.sk89q.worldguard", "worldguard-bukkit", "7.0.8-SNAPSHOT")
    compileOnly("com.github.TechFortress", "GriefPrevention", "16.18")
    compileOnly("com.github.cjburkey01", "ClaimChunk", "0.0.22")
    compileOnly("com.griefdefender", "api", "2.1.0-SNAPSHOT")

    implementation(platform("com.intellectualsites.bom:bom-1.18.x:1.28"))
    compileOnly("com.plotsquared:PlotSquared-Core")
    compileOnly("com.plotsquared:PlotSquared-Bukkit")
}

tasks {
    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        downloadPlugins {
            modrinth("pl3xmap", "1.20.4-492")
        }

        minecraftVersion("1.20.6")
    }

    assemble {
        doLast {
            copy {
                from(shadowJar.get())
                into(rootProject.projectDir.resolve("jars"))
            }
        }
    }

    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set("")

        listOf(
            "com.ryderbelserion.vital"
        ).forEach {
            relocate(it, "libs.$it")
        }
    }

    processResources {
        inputs.properties("name" to rootProject.name)
        inputs.properties("version" to project.version)
        inputs.properties("group" to project.properties["group"])
        inputs.properties("description" to project.properties["description"])
        inputs.properties("website" to project.properties["website"])

        filesMatching("paper-plugin.yml") {
            expand(inputs.properties)
        }
    }
}