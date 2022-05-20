class LanguageString (name: String, string: String) {
    var name = name
        set(value) {
            if (!Application.Utils.nameFormat.matches(value)) throw Exception("AMOGUS")
            field = value
        }
    var string: String = string
}

class Language(name: String) {
    val name: String = name
    val strings = mutableListOf<LanguageString>()
}