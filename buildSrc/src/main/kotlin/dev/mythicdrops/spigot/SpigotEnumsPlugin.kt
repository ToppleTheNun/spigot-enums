package dev.mythicdrops.spigot

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJsProjectExtension
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

open class SpigotEnumsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create("spigotEnums", SpigotEnumsExtension::class)

        target.pluginManager.withPlugin("org.jetbrains.kotlin.js") {
            target.logger.info("Registering 'generateSpigotEnum' task")
            target.tasks.register("generateSpigotEnum", GenerateSpigotEnumsTask::class.java) {
                enumFilterBlock.set(extension.filterBlock)
                spigotEnum.set(extension.spigotEnum)
                outputDirectory.set(SpigotEnumsUtils.getRootOutputPath(target.layout.buildDirectory))
            }
            target.logger.info("Adding buildConfigKt generated directory to Kotlin src...")
            target.extensions.findByType(KotlinJsProjectExtension::class.java)
                ?.sourceSets?.findByName("main")
                ?.kotlin?.srcDir(SpigotEnumsUtils.getRootOutputPath(target.layout.buildDirectory))
            target.logger.info("Adding 'generateSpigotEnum' as a dependency for Kotlin compilation tasks...")
            target.tasks.withType<Kotlin2JsCompile>().configureEach {
                dependsOn("generateSpigotEnum")
            }
        }
    }
}
