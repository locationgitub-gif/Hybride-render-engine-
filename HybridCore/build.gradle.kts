plugins {
    id("fabric-loom") version "1.9.2" apply false
    id("java")
}

subprojects {
    apply(plugin = "java")

    group = property("maven_group") as String
    version = property("mod_version") as String

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release = 21
    }
}
