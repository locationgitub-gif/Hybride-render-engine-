plugins {
    id("fabric-loom")
    id("java")
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")

    implementation(project(":common"))
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
    from(project(":common").sourceSets.main.get().output)
    archiveBaseName.set("hybridcore")
}
