import java.io.File
import java.util.*

enum class ResourceType {
    STATIC_MODEL,
    DYNAMIC_MODEL,
    COLLISION_MODEL,
    LIGHTMAP
}

data class Resource (val name: String, val type: ResourceType)

class ResourceList {
    val size get() = resources.size
    private val resources = mutableListOf<Resource>()

    operator fun get(index: Int) = resources[index]

    fun getResourceNameList (comp: (ResourceType) -> Boolean): Vector<String> {
        val ls: Vector<String> = Vector()
        resources.filter { comp(it.type) }.forEach { ls.add(it.name) }
        return ls
    }

    fun LoadFromDisk() {
        File("data/textures/lightmap").walk().filter {
            it.isFile &&
            it.path.endsWith(".png")
        }.forEach {
            resources.add(Resource(
                it.path.replace('\\', '/').removePrefix("data/textures/lightmap/").removeSuffix(".png"),
                ResourceType.LIGHTMAP))
        }

        File("data/models").walk().filter {
            it.isFile &&
            (it.path.endsWith(".stmdl") ||
            it.path.endsWith(".dymdl") ||
            it.path.endsWith(".collmdl"))
        }.forEach {
            val str = it.path.replace('\\', '/').removePrefix("data/models/")
            when {
                str.endsWith(".stmdl") -> resources.add(Resource(str.removeSuffix(".stmdl"), ResourceType.STATIC_MODEL))
                str.endsWith(".dymdl") -> resources.add(Resource(str.removeSuffix(".dymdl"), ResourceType.DYNAMIC_MODEL))
                str.endsWith(".collmdl") -> resources.add(Resource(str.removeSuffix(".collmdl"), ResourceType.COLLISION_MODEL))
            }

        }

        print(resources)

    }
}

