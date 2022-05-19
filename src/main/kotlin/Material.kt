enum class MaterialType {
    FLAT,
    ALPHA,
    WATER
}

data class Material (var name: String, var type: MaterialType)
