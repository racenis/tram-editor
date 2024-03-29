object Application {
    val name = "uwu ;33"
    val materials = MaterialList("texture")
    val resources = ResourceList()
    val cell = Cell("demo5")
    val language = Language("lv")

    object Utils {
        val nameFormat = Regex("""[A-Za-z0-9/\-]+""")
    }

    fun Init () {
        cell.LoadFromDisk()
        language.LoadFromDisk()
        materials.LoadFromDisk()
        resources.LoadFromDisk()
    }

    fun Save() {
        cell.WriteToDisk()
        language.WriteToDisk()
        materials.WriteToDisk()
    }
}

fun AppMakeData(){
    Application.cell.entities.add(Staticwobj("Lielais Priekšnieks", Vec3(0.0f, 0.0f, 0.0f), Vec3(0.0f, 0.0f, 0.0f), "none", "benis", "benis", "fullbright"))
    Application.cell.entities.add(Staticwobj("Liela Priekšnieka Vietnieks", Vec3(0.0f, 0.0f, 0.0f), Vec3(0.0f, 0.0f, 0.0f), "none", "benis", "benis", "fullbright"))
    //Application.cell.entities.add(Crate("Mazais Priekšnieks", Vec3(0.0f, 0.0f, 0.0f), Vec3(0.0f, 0.0f, 0.0f), "none", "benis", "benis"))

    //Application.materials.add(Material("benadola-mat", MaterialType.FLAT))
    //Application.materials.add(Material("benadola-mat2", MaterialType.FLAT))
    //Application.materials.add(Material("benadola-matee", MaterialType.ALPHA))
    //Application.materials.add(Material("benadola-fluid", MaterialType.WATER))

    //Application.language.strings.add(LanguageString("benadrols", "Benadrolium."))
    //Application.language.strings.add(LanguageString("bembadols", "Bembadolium est."))
    //Application.language.strings.add(LanguageString("dendralols", "Dendralol ding ding bat."))
}