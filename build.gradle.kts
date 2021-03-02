plugins {
    kotlin("js") apply false
    id("lt.petuska.npm.publish") version "1.1.2" apply false
}

subprojects {
    pluginManager.apply("org.jetbrains.kotlin.js")
    pluginManager.apply("lt.petuska.npm.publish")
    pluginManager.apply(dev.mythicdrops.spigot.SpigotEnumsPlugin::class)

    repositories {
        mavenCentral()
    }

    configure<org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension> {
        js(IR) {
            binaries.library()
            browser()
            nodejs()
        }
    }

    System.getenv("ACTIONS_NPM_TOKEN")?.let {
        configure<lt.petuska.npm.publish.dsl.NpmPublishExtension> {
            repositories {
                repository("npmjs") {
                    registry = uri("https://registry.npmjs.org")
                    authToken = it
                }
            }
        }
    }
}
