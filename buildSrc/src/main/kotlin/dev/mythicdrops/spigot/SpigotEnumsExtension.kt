package dev.mythicdrops.spigot

import org.gradle.api.provider.Property

typealias EnumFilterBlock = (enumValue: Enum<*>) -> Boolean

/**
 * Allows configuring which Spigot enum to generate Kotlin files for.
 */
abstract class SpigotEnumsExtension {
    /**
     * Spigot enum to generate Kotlin files for.
     */
    abstract val spigotEnum: Property<Class<*>>

    /**
     * Filter block to invoke for filtering out unwanted enum entries.
     */
    abstract val filterBlock: Property<EnumFilterBlock>
}
