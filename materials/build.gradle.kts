spigotEnums {
    filterBlock.set {
        !it.name.contains("LEGACY")
    }
    spigotEnum.set(org.bukkit.Material::class.java)
}
