data class Vec3(val x: Float, val y: Float, val z: Float) {
    override fun toString(): String = "($x, $y, $z)"
}

enum class EntityType {
    DEFAULT,
    STATICWOBJ,
    CRATE
}

abstract class Entity(/*type: EntityType, */name: String, location: Vec3, rotation: Vec3, action: String) {
    abstract val type: EntityType
    val name: String = name
    val location: Vec3 = location
    val rotation: Vec3 = rotation
    val action: String = action
}

class Staticwobj(name: String, location: Vec3, rotation: Vec3, action: String, model: String, collisionmodel: String, lightmap: String) : Entity(name, location, rotation, action) {
    override val type: EntityType = EntityType.STATICWOBJ
    var model: String = model
    val collisionmodel: String = collisionmodel
    val lightmap: String = lightmap
}

class Crate(name: String, location: Vec3, rotation: Vec3, action: String, model: String, collisionmodel: String) : Entity(name, location, rotation, action) {
    override val type: EntityType = EntityType.CRATE
    val model: String = model
    val collisionmodel: String = collisionmodel
}

class Cell(name: String) {
    val name: String = name
    val entities = mutableListOf<Entity>()
}