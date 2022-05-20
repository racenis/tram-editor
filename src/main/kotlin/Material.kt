import java.io.File
import java.util.*

enum class MaterialType {
    FLAT,
    ALPHA,
    WATER,
    LIGHTMAP;

    override fun toString(): String {
        when (ordinal) {
            MaterialType.FLAT.ordinal -> return "Plakans"
            MaterialType.ALPHA.ordinal -> return "Caurspīdīgs"
            MaterialType.WATER.ordinal -> return "Ūdens"
            MaterialType.LIGHTMAP.ordinal -> return "Gaismas"
            else -> return "???"
        }
    }

    companion object {
        fun toFileString (type: MaterialType) = type.name.lowercase()
        fun fromFileString (string: String) = MaterialType.valueOf(string.uppercase())
    }
}

class Material (name: String, type: MaterialType) {
    var name: String = name
        set(value) {
            if (!Application.Utils.nameFormat.matches(value)) throw Exception("AMOGUS")
            field = value
        }
    var type: MaterialType = type
}

class MaterialList (name: String) {
    val name: String = name
    val size get() = materials.size
    private val materials = mutableListOf<Material>()

    fun addBlankMaterial() = materials.add(Material("", MaterialType.FLAT))
    fun removeMaterial(index: Int) = materials.removeAt(index)
    //fun getMaterial(index: Int) = materials[index]
    operator fun get(index: Int) = materials[index]

    fun LoadFromDisk() {
        materials.clear()
        File("data/" + name + ".list", ).forEachLine(charset("windows-1257")) {
            val split = it.split(' ', ignoreCase = true, limit = 2)
            //println("name: ${split.first()}, type: ${split.last()}")
            //println(Material(split.first(), MaterialType.fromFileString(split.last())))
            materials.add(Material(split.first(), MaterialType.fromFileString(split.last())))
        }
    }

    fun WriteToDisk() {
        val file = File("data/" + name + ".list", )
        var fileContent = ""
        materials.forEach { fileContent += it.name + " " + MaterialType.toFileString(it.type) + "\n" }
        file.writeText(fileContent, charset("windows-1257"))
    }
}