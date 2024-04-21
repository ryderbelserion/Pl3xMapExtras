plugins {
    `java-library`

    `maven-publish`
}

base {
    archivesName.set(rootProject.name)
}

repositories {
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
    toolchain.languageVersion.set(JavaLanguageVersion.of("17"))
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
}