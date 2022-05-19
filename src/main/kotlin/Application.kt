object Application {
    val name = "uwu ;33"
    val materials = mutableListOf<Material>()
    val models = mutableListOf<Model>()
    val cell = Cell("demo5")
    val language = Language("lv")
}

fun AppMakeData(){
    Application.cell.entities.add(Staticwobj("Lielais Priekšnieks", Vec3(0.0f, 0.0f, 0.0f), Vec3(0.0f, 0.0f, 0.0f), "none", "benis", "benis", "fullbright"))
    Application.cell.entities.add(Staticwobj("Liela Priekšnieka Vietnieks", Vec3(0.0f, 0.0f, 0.0f), Vec3(0.0f, 0.0f, 0.0f), "none", "benis", "benis", "fullbright"))
    Application.cell.entities.add(Crate("Mazais Priekšnieks", Vec3(0.0f, 0.0f, 0.0f), Vec3(0.0f, 0.0f, 0.0f), "none", "benis", "benis"))
}