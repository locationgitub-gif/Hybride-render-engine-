plugins {
    id("fabric-loom") version "1.9.2"
    id("java")
}

group   = "com.denotas"
version = "1.0.0"

repositories {
    maven("https://maven.fabricmc.net/")
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings("net.fabricmc:yarn:1.21.1+build.3:v2")
    modImplementation("net.fabricmc:fabric-loader:0.16.5")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.102.0+1.21.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release  = 21
}

loom {
    accessWidenerPath = file("src/main/resources/hybridcore.accesswidener")
    mixin {
        defaultRefmapName.set("hybridcore.refmap.json")
    }
}

tasks.processResources {
    inputs.property("version", project.version)
    filteringCharset = "UTF-8"
    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

tasks.jar {
    archiveBaseName.set("hybridcore")
}
