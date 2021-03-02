package dev.mythicdrops.spigot

import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Provider

object SpigotEnumsUtils {
    fun getRootOutputPath(projectBuildDir: DirectoryProperty): Provider<Directory> {
        return projectBuildDir.dir("generated-sources/spigot-enums")
    }
}