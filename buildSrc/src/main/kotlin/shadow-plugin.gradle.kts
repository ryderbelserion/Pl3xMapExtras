plugins {
    id("com.gradleup.shadow")
    id("java-plugin")
}

tasks {
    shadowJar {
        archiveClassifier.set("")

        from(rootProject.layout.projectDirectory.dir("configs")) {
            into("/")
        }

        exclude("META-INF/**")
    }
}