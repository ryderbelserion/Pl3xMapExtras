plugins {
    alias(libs.plugins.paperweight)
    alias(libs.plugins.runPaper)
    alias(libs.plugins.shadow)

    `paper-plugin`
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots")

    maven("https://repo.glaremasters.me/repository/bloodshot")

    maven("https://repo.essentialsx.net/snapshots")

    maven("https://repo.essentialsx.net/releases")

    maven("https://repo.olziedev.com")
}

dependencies {
    paperweight.paperDevBundle(libs.versions.paper)

    implementation(project(":common"))

    // Warps
    compileOnly("com.olziedev", "playerwarps-api", "6.30.0") {}
    compileOnly("net.essentialsx", "EssentialsX", "2.21.0-SNAPSHOT") {
        exclude("org.bstats", "*")
        exclude("org.spigotmc", "*")
    }

    // Claims
    compileOnly("com.sk89q.worldguard", "worldguard-bukkit", "7.0.8-SNAPSHOT")
    compileOnly("com.github.TechFortress", "GriefPrevention", "16.18.2")
    compileOnly("com.github.cjburkey01", "ClaimChunk", "0.0.22")
    compileOnly("com.griefdefender", "api", "2.1.0-SNAPSHOT")

    implementation(platform("com.intellectualsites.bom:bom-1.18.x:1.28"))
    compileOnly("com.plotsquared:PlotSquared-Core")
    compileOnly("com.plotsquared:PlotSquared-Bukkit")
}

paperweight {
    reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION
}

tasks {
    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

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
        archiveBaseName.set("${rootProject.name}-${project.version}")
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