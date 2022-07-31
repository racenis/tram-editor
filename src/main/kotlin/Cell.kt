import java.io.File

data class Vec3(val x: Float, val y: Float, val z: Float) {
    override fun toString(): String = "($x, $y, $z)"

    companion object{
        fun fromString(str: String): Vec3 {
            val format = Regex("""\((-?[0-9]+\.?[0-9]*), (-?[0-9]+\.?[0-9]*), (-?[0-9]+\.?[0-9]*)\)""")
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
    CRATE;

    override fun toString(): String {
        when (ordinal) {
            EntityType.DEFAULT.ordinal -> return "Vaniļas"
            EntityType.STATICWOBJ.ordinal -> return "Statiskais Obj."
            EntityType.CRATE.ordinal -> return "Kaste"
            else -> return "???"
        }
    }

    companion object {
        fun toFileString (type: EntityType) = type.name.lowercase()
        fun fromFileString (string: String) = EntityType.valueOf(string.uppercase())
    }
}

abstract class Entity(/*type: EntityType, */name: String, location: Vec3, rotation: Vec3, action: String) {
    open val type: EntityType get() = EntityType.DEFAULT
    var name: String = name
        set(value) {
            if (!Application.Utils.nameFormat.matches(value)) throw Exception("AMOGUS")
            field = value
        }

    var location: Vec3 = location
    var rotation: Vec3 = rotation
    var action: String = action

    fun entityString(): String {
        return name + " " + location.x + " " + location.y + " " + location.z + " " + rotation.x + " " + rotation.y + " " + rotation.z + " " + action
    }

    fun convertTo(typeToConvert: EntityType): Entity {
        if (this.type == typeToConvert) return this
        when (typeToConvert){
            EntityType.DEFAULT -> return this
            EntityType.STATICWOBJ -> return Staticwobj(name, location, rotation, action, "none", "none", "none")
            EntityType.CRATE -> return Crate(name, location, rotation, action, "none", "none")
        }
    }

    companion object {
        fun fromString (string: String) : Entity {
            val tokens = string.split(' ')
            //println(tokens)

            val name = tokens[1]
            val location = Vec3(tokens[2].toFloat(), tokens[3].toFloat(), tokens[4].toFloat())
            val rotation = Vec3(tokens[5].toFloat(), tokens[6].toFloat(), tokens[7].toFloat())
            val action = tokens[8]

            when (tokens.first()) {
                "staticwobj" -> return Staticwobj(name, location, rotation, action, tokens[9], tokens[9], tokens[10])
                "crate" -> return Crate(name, location, rotation, action, tokens[9], tokens[10])
                else -> throw Exception("Unrecognized entity type: " + tokens.first())
            }
        }
    }
}

class Staticwobj(name: String, location: Vec3, rotation: Vec3, action: String, model: String, collisionmodel: String, lightmap: String) : Entity(name, location, rotation, action) {
    override val type: EntityType get() = EntityType.STATICWOBJ
    var model: String = model
    var collisionmodel: String = collisionmodel
    var lightmap: String = lightmap

    override fun toString(): String {
        return "staticwobj " + entityString() + " " + model + " " + lightmap
    }
}

class Crate(name: String, location: Vec3, rotation: Vec3, action: String, model: String, collisionmodel: String) : Entity(name, location, rotation, action) {
    override val type: EntityType get() = EntityType.CRATE
    var model: String = model
    var collisionmodel: String = collisionmodel

    override fun toString(): String {
        return "crate " + entityString() + " " + model + " " + collisionmodel
    }
}

class Cell(name: String) {
    fun addBlankEntity() {
        entities.add(Staticwobj("none", Vec3(0.0f, 0.0f, 0.0f), Vec3(0.0f, 0.0f, 0.0f), "none", "none", "none", "fullbright"))
    }

    fun removeEntity(entityIndex: Int) {
        entities.removeAt(entityIndex)
    }

    val name: String = name
    val entities = mutableListOf<Entity>()

    fun LoadFromDisk() {
        entities.clear()
        File("data/" + name + ".cell", ).forEachLine(charset("windows-1257")) {
            entities.add(Entity.fromString(it))
        }
    }

    fun WriteToDisk() {
        val file = File("data/" + name + ".cell", )
        var fileContent = ""
        entities.forEach { fileContent += it.toString() + "\n" }
        file.writeText(fileContent, charset("windows-1257"))
    }
}