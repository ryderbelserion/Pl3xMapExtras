plugins {
    `java-library`

    `maven-publish`
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")

    exclusiveContent {
        forRepository {
            maven("https://api.modrinth.com/maven/")
        }

        filter { includeGroup("maven.modrinth") }
    }

    maven("https://jitpack.io/")

    mavenCentral()
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of("21"))
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
}