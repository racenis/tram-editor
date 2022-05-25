import java.awt.Dimension
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener


class TextFieldChangeListener(action: () -> Unit) : DocumentListener {
    override fun changedUpdate(e: DocumentEvent?) { action() }
    override fun removeUpdate(e: DocumentEvent?) { action() }
    override fun insertUpdate(e: DocumentEvent?) { action() }
    val action = action
}

fun CellEntityPropertiesPane(obj: Entity): JScrollPane {
    val panel: JPanel = JPanel()
    val pane: JScrollPane = JScrollPane(panel)
    panel.layout = BoxLayout(panel, BoxLayout.PAGE_AXIS)

    when (obj) {
        is Staticwobj -> {
            val modelField = JComboBox(Application.resources.getResourceNameList { res -> res == ResourceType.STATIC_MODEL || res == ResourceType.DYNAMIC_MODEL })
            val collisionmodelField = JComboBox(Application.resources.getResourceNameList { res -> res == ResourceType.COLLISION_MODEL })
            val lightmapField = JComboBox(Application.resources.getResourceNameList { res -> res == ResourceType.LIGHTMAP })
            val modelFieldLabel = JLabel("Modelis")
            val collisionmodelFieldLabel = JLabel("Fizikas modelis")
            val lightmapFieldLabel = JLabel("Gaismas tekstūra")

            modelField.isEditable = true
            modelField.selectedItem = obj.model
            modelField.maximumSize = Dimension(Int.MAX_VALUE, modelField.preferredSize.height)
            modelField.addActionListener { obj.model = modelField.selectedItem as String }

            collisionmodelField.isEditable = true
            collisionmodelField.selectedItem = obj.collisionmodel
            collisionmodelField.maximumSize = Dimension(Int.MAX_VALUE, collisionmodelField.preferredSize.height)
            collisionmodelField.addActionListener { obj.collisionmodel = collisionmodelField.selectedItem as String }

            lightmapField.isEditable = true
            lightmapField.selectedItem = obj.lightmap
            lightmapField.maximumSize = Dimension(Int.MAX_VALUE, collisionmodelField.preferredSize.height)
            lightmapField.addActionListener {obj.lightmap = lightmapField.selectedItem as String}

            modelFieldLabel.labelFor = modelField
            collisionmodelFieldLabel.labelFor = collisionmodelField
            lightmapFieldLabel.labelFor = collisionmodelField

            panel.add(modelFieldLabel)
            panel.add(modelField)
            panel.add(collisionmodelFieldLabel)
            panel.add(collisionmodelField)
            panel.add(lightmapFieldLabel)
            panel.add(lightmapField)
        }
        is Crate -> {
            val modelField = JComboBox(Application.resources.getResourceNameList { res -> res == ResourceType.STATIC_MODEL || res == ResourceType.DYNAMIC_MODEL })
            val collisionmodelField = JComboBox(Application.resources.getResourceNameList { res -> res == ResourceType.COLLISION_MODEL })
            val modelFieldLabel = JLabel("Modelis")
            val collisionmodelFieldLabel = JLabel("Fizikas modelis")

            modelField.isEditable = true
            modelField.selectedItem = obj.model
            modelField.maximumSize = Dimension(Int.MAX_VALUE, modelField.preferredSize.height)
            modelField.addActionListener { obj.model = modelField.selectedItem as String }

            collisionmodelField.isEditable = true
            collisionmodelField.selectedItem = obj.collisionmodel
            collisionmodelField.maximumSize = Dimension(Int.MAX_VALUE, collisionmodelField.preferredSize.height)
            collisionmodelField.addActionListener { obj.collisionmodel = collisionmodelField.selectedItem as String }

            modelFieldLabel.labelFor = modelField
            collisionmodelFieldLabel.labelFor = collisionmodelField

            panel.add(modelFieldLabel)
            panel.add(modelField)
            panel.add(collisionmodelFieldLabel)
            panel.add(collisionmodelField)
        }
    }


    panel.add(Box.createVerticalGlue())

    return pane
}

/*
fun CellEntityPropertiesPane(obj: Crate): JScrollPane {
    val panel: JPanel = JPanel()
    val pane: JScrollPane = JScrollPane(panel)
    panel.layout = BoxLayout(panel, BoxLayout.PAGE_AXIS)



    panel.add(Box.createVerticalGlue())

    return pane
}*/