enum class MaterialType {
    FLAT,
    ALPHA,
    WATER
}

data class Material (val name: String, val type: MaterialType)
