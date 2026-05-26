plugins {
    id("net.fabricmc.fabric-loom-remap") version "1.14-SNAPSHOT"
}

version = "1.0.0"
group = "sh.lyosha"

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
    // Fabric API pulled in for command registration (fabric-command-api-v2)
    // and lifecycle events. Mixins still target vanilla classes directly.
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_api_version")}")
}

base {
    archivesName = "TotemGhostFix"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}
