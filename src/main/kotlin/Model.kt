enum class ModelType {
    STATIC,
    DYNAMIC,
    COLLISION
}

data class Model (val name: String, val type: ModelType)

