data class LanguageString (val name: String, val string: String)

class Language(name: String) {
    val name: String = name
    val strings = mutableListOf<LanguageString>()
}