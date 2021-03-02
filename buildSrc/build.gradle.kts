plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }
    maven {
        url = uri("https://repo.codemc.org/repository/nms")
    }
}

dependencies {
    api("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    implementation(kotlin("gradle-plugin", "1.4.31"))
    implementation("com.squareup:kotlinpoet:1.7.2")
}