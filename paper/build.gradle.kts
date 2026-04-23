plugins {
    `paper-plugin`
}

project.group = "${rootProject.group}"

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots/")

    maven("https://repo.glaremasters.me/repository/bloodshot/")

    maven("https://repo.essentialsx.net/snapshots/")

    maven("https://repo.essentialsx.net/releases/")

    maven("https://maven.enginehub.org/repo/")

    maven("https://repo.olziedev.com/")
}

dependencies {
    implementation(libs.fusion.paper)

    implementation(libs.metrics)

    implementation(platform(libs.plot.bom))
    compileOnly(libs.plot.bukkit)
    compileOnly(libs.plot.core)

    compileOnly(libs.essentials) {
        exclude("org.bstats", "*")
        exclude("org.spigotmc", "*")
    }

    compileOnly(libs.griefprevention)
    compileOnly(libs.griefdefender)
    compileOnly(libs.playerwarps)
    compileOnly(libs.worldguard)

    api(project(":core"))
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

    runPaper.folia.registerTask()

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")
        jvmArgs("-Dcom.mojang.eula.agree=true")

        downloadPlugins {
            modrinth("pl3xmap", libs.versions.pl3xmap.get())
            modrinth("luckperms", libs.versions.luckperms.get())
        }

        defaultCharacterEncoding = Charsets.UTF_8.name()

        minecraftVersion(libs.versions.minecraft.get())
    }
}