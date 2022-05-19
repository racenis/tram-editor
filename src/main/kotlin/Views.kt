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
            val modelField = JTextField(15)
            val collisionmodelField = JTextField(15)
            val lightmapField = JTextField(15)
            val modelFieldLabel = JLabel("Modelis")
            val collisionmodelFieldLabel = JLabel("Fizikas modelis")
            val lightmapFieldLabel = JLabel("Gaismas tekstūra")

            modelField.text = obj.model
            modelField.maximumSize = Dimension(Int.MAX_VALUE, modelField.preferredSize.height)
            modelField.document.addDocumentListener(TextFieldChangeListener { obj.model = modelField.text })

            collisionmodelField.text = obj.collisionmodel
            collisionmodelField.maximumSize = Dimension(Int.MAX_VALUE, collisionmodelField.preferredSize.height)

            lightmapField.text = obj.lightmap
            lightmapField.maximumSize = Dimension(Int.MAX_VALUE, collisionmodelField.preferredSize.height)

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
            val modelField = JTextField(15)
            val collisionmodelField = JTextField(15)
            val modelFieldLabel = JLabel("Modelis")
            val collisionmodelFieldLabel = JLabel("Fizikas modelis")

            modelField.maximumSize = Dimension(Int.MAX_VALUE, modelField.preferredSize.height)
            collisionmodelField.maximumSize = Dimension(Int.MAX_VALUE, collisionmodelField.preferredSize.height)

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