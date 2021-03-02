package dev.mythicdrops.spigot

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class GenerateSpigotEnumsTask : DefaultTask() {
    @get:Input
    abstract val spigotEnum: Property<Class<*>>

    @get:Input
    abstract val enumFilterBlock: Property<EnumFilterBlock>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    init {
        description = "Generates a Kotlin file with information for a Spigot enum"
        group = "build"
    }

    @TaskAction
    fun generateBuildConfig() {
        if (!this.spigotEnum.isPresent) {
            logger.info("Doing nothing as spigotEnum is not set")
            return
        }
        val spigotEnum = this.spigotEnum.get()
        if (!spigotEnum.isEnum) {
            throw IllegalArgumentException("${spigotEnum.name} is not an enum")
        }
        val outputDirectory = this.outputDirectory.asFile.get()
        val fileSpec = buildFileSpec(spigotEnum)
        outputDirectory.mkdirs()
        fileSpec.writeTo(outputDirectory)
    }

    private fun buildFileSpec(spigotEnum: Class<*>): FileSpec {
        val packageName = project.group.toString()
        val className = spigotEnum.simpleName
        val spigotEnumClass = ClassName(packageName, className)
        return FileSpec.builder(packageName, className)
            .addType(
                buildSealedClassSpec(spigotEnum, spigotEnumClass)
            )
            .build()
    }

    private fun buildSealedClassSpec(spigotEnum: Class<*>, spigotEnumClass: ClassName): TypeSpec {
        val typeSpecBuilder = TypeSpec.classBuilder(spigotEnumClass).addModifiers(KModifier.SEALED).addAnnotation(ClassName("kotlin.js", "JsExport"))
        val enumConstants = spigotEnum.enumConstants.filterIsInstance<Enum<*>>()
        val filteredEnumConstants = if (enumFilterBlock.isPresent) enumConstants.filter(enumFilterBlock.get()) else enumConstants
        filteredEnumConstants.forEach {
            typeSpecBuilder.addType(TypeSpec.classBuilder(ClassName(spigotEnumClass.packageName, it.name)).superclass(spigotEnumClass).build())
        }
        return typeSpecBuilder.build()
    }
}
