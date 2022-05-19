data class LanguageString (var name: String, var string: String)

class Language(name: String) {
    val name: String = name
    val strings = mutableListOf<LanguageString>()
}