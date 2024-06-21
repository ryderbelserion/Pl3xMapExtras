plugins {
    alias(libs.plugins.paperweight)
    alias(libs.plugins.shadowJar)
    alias(libs.plugins.runPaper)

    `paper-plugin`
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots")

    maven("https://repo.glaremasters.me/repository/bloodshot")

    maven("https://repo.papermc.io/repository/maven-public")

    maven("https://repo.triumphteam.dev/snapshots")

    maven("https://repo.essentialsx.net/releases")

    maven("https://repo.crazycrew.us/snapshots")

    maven("https://maven.enginehub.org/repo")

    maven("https://repo.olziedev.com")
}

dependencies {
    paperweight.paperDevBundle(libs.versions.paper)

    implementation(project(":pl3xmapextras-core"))

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
            modrinth("pl3xmap", "1.20.6-498")
        }

        minecraftVersion(libs.versions.minecraft.get())
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
    }

    processResources {
        inputs.properties("name" to rootProject.name)
        inputs.properties("version" to project.version)
        inputs.properties("group" to project.properties["group"])
        inputs.properties("apiVersion" to libs.versions.minecraft.get())
        inputs.properties("description" to project.properties["description"])
        inputs.properties("apiVersion" to libs.versions.minecraft.get())
        inputs.properties("website" to project.properties["website"])

        filesMatching("paper-plugin.yml") {
            expand(inputs.properties)
        }
    }
}