import sun.nio.cs.UTF_32LE
import java.io.File
import java.nio.charset.Charset

class LanguageString (name: String, string: String) {
    var name = name
        set(value) {
            if (!Application.Utils.nameFormat.matches(value)) throw Exception("AMOGUS")
            field = value
        }
    var string: String = string
}

class Language (name: String) {
    val name: String = name
    val size get() = strings.size
    private val strings = mutableListOf<LanguageString>()

    operator fun get(index: Int) = strings[index]
    fun addBlankString() = strings.add(LanguageString("", ""))
    fun removeString(index: Int) = strings.removeAt(index)

    fun LoadFromDisk() {
        strings.clear()
        File("data/" + name + ".lang", ).forEachLine(charset("windows-1257")) {
            val split = it.split(' ', ignoreCase = true, limit = 2)
            strings.add(LanguageString(split.first(), split.last()))
        }
    }

    fun WriteToDisk() {
        val file = File("data/" + name + ".lang", )
        var fileContent = ""
        strings.forEach { fileContent += it.name + " " + it.string + "\n" }
        file.writeText(fileContent, charset("windows-1257"))
    }
}

