plugins {
    `config-paper`
}

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi")

    maven("https://oss.sonatype.org/content/repositories/snapshots")

    maven("https://repo.glaremasters.me/repository/bloodshot")

    maven("https://repo.essentialsx.net/snapshots")

    maven("https://repo.essentialsx.net/releases")

    maven("https://maven.enginehub.org/repo")

    maven("https://repo.olziedev.com")
}

dependencies {
    implementation(libs.fusion.paper)

    implementation(libs.metrics)

    api(project(":core"))

    compileOnly("com.olziedev", "playerwarps-api", "6.30.0") {}
    compileOnly("net.essentialsx", "EssentialsX", "2.21.0-SNAPSHOT") {
        exclude("org.bstats", "*")
        exclude("org.spigotmc", "*")
    }

    compileOnly("com.sk89q.worldguard", "worldguard-bukkit", "7.0.8-SNAPSHOT")
    compileOnly("com.github.TechFortress", "GriefPrevention", "16.18.2")
    compileOnly("com.cjburkey.claimchunk", "claimchunk", "0.0.25-FIX3")
    compileOnly("com.griefdefender", "api", "2.1.0-SNAPSHOT")

    implementation(platform("com.intellectualsites.bom:bom-1.18.x:1.28"))
    compileOnly("com.plotsquared:PlotSquared-Core")
    compileOnly("com.plotsquared:PlotSquared-Bukkit")
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        listOf(
            "org.bstats"
        ).forEach {
            relocate(it, "libs.$it")
        }
    }

    processResources {
        inputs.properties(
            "name" to rootProject.name,
            "version" to rootProject.version,
            "description" to rootProject.description,
            "minecraft" to libs.versions.minecraft.get(),
            "group" to rootProject.group
        )

        with(copySpec {
            from("src/main/resources/paper-plugin.yml") {
                expand(inputs.properties)
            }
        })
    }

    runPaper.folia.registerTask()

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")
        jvmArgs("-Dcom.mojang.eula.agree=true")

        downloadPlugins {
            modrinth("pl3xmap", libs.versions.pl3xmap.get())
            modrinth("luckperms", "v5.5.0-bukkit")
        }

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }
}
