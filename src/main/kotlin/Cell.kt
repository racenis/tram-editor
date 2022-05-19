data class Vec3(val x: Float, val y: Float, val z: Float) {
    override fun toString(): String = "($x, $y, $z)"

    companion object{
        fun fromString(str: String): Vec3 {
            val format = Regex("""\(([0-9]+\.?[0-9]*), ([0-9]+\.?[0-9]*), ([0-9]+\.?[0-9]*)\)""")
            // tas ir simbolu virkne formā '(0.0, 0.0, 0.0)', ar jebkādiem skaitļiem

            if (format matches str){
                val result = format.find(str)!!.groupValues
                val x = result[1].toFloat()
                val y = result[2].toFloat()
                val z = result[3].toFloat()
                return Vec3(x, y, z)
            } else {
                throw Exception("AMOGUS!")
            }
        }
    }
}

enum class EntityType {
    DEFAULT,
    STATICWOBJ,
    CRATE
}

abstract class Entity(/*type: EntityType, */name: String, location: Vec3, rotation: Vec3, action: String) {
    abstract val type: EntityType
    var name: String = name
    var location: Vec3 = location
    var rotation: Vec3 = rotation
    var action: String = action
    fun convertTo(typeToConvert: EntityType): Entity {
        when (typeToConvert){
            EntityType.DEFAULT -> return this
            EntityType.STATICWOBJ -> return Staticwobj(name, location, rotation, action, "none", "none", "none")
            EntityType.CRATE -> return Crate(name, location, rotation, action, "none", "none")
        }
    }
}

class Staticwobj(name: String, location: Vec3, rotation: Vec3, action: String, model: String, collisionmodel: String, lightmap: String) : Entity(name, location, rotation, action) {
    override val type: EntityType = EntityType.STATICWOBJ
    var model: String = model
    var collisionmodel: String = collisionmodel
    var lightmap: String = lightmap
}

class Crate(name: String, location: Vec3, rotation: Vec3, action: String, model: String, collisionmodel: String) : Entity(name, location, rotation, action) {
    override val type: EntityType = EntityType.CRATE
    var model: String = model
    var collisionmodel: String = collisionmodel
}

class Cell(name: String) {
    val name: String = name
    val entities = mutableListOf<Entity>()
}