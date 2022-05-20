enum class MaterialType {
    FLAT,
    ALPHA,
    WATER
}

class Material (name: String, type: MaterialType) {
    var name: String = name
        set(value) {
            if (!Application.Utils.nameFormat.matches(value)) throw Exception("AMOGUS")
            field = value
        }
    var type: MaterialType = type
}
