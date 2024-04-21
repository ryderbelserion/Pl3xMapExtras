plugins {
    id("com.github.johnrengelman.shadow")

    alias(libs.plugins.run.paper)

    `paper-plugin`
}

dependencies {
    implementation(project(":common"))
}

tasks {
    assemble {
        dependsOn(reobfJar)

        doLast {
            copy {
                from(reobfJar.get())
                into(rootProject.projectDir.resolve("jars"))
            }
        }
    }

    shadowJar {
        archiveClassifier.set("")
    }

    runServer {
        jvmArgs("-Dnet.kyori.ansi.colorLevel=truecolor")

        defaultCharacterEncoding = Charsets.UTF_8.name()

        downloadPlugins {
            modrinth("pl3xmap", "1.20.4-492")
        }

        minecraftVersion("1.20.4")
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