import com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.*
import javax.swing.event.ListSelectionListener
import javax.swing.event.MenuEvent
import javax.swing.event.MenuListener
import javax.swing.table.AbstractTableModel
import kotlin.system.exitProcess

fun ShowInputErrorBox() {
    JOptionPane.showMessageDialog(null, "Nezināmas izcelsmes ievades kļūda", "Kļūda!", JOptionPane.ERROR_MESSAGE)

}

class AppFrame : JFrame() {
    val mainMenuBar: JMenuBar

    object cellTableModel : AbstractTableModel() {
        override fun getColumnName(col: Int): String {
            when (col){
                0 -> return "Tips"
                1 -> return "Nosaukums"
                2 -> return "Lokācija"
                3 -> return "Rotācija"
                4 -> return "Darbība"
                else -> { return "???" }
            }
        }

        override fun getRowCount(): Int {
            //return rowData.length
            //println("Giving row count as " + Application.cell.entities.size)
            return Application.cell.entities.size
        }

        override fun getColumnCount(): Int {
            //return columnNames.length
            return 5
        }

        override fun getValueAt(row: Int, col: Int): Any? {
            //return rowData.get(row).get(col)

            // TODO: aizstāt izņēmuma pārbaudīšanu ar kādu citu veidu kā pārbaudīt vai indekss atroda sarakstā
            try {
                when (col) {
                    0 -> return Application.cell.entities[row].type
                    1 -> return Application.cell.entities[row].name
                    2 -> return Application.cell.entities[row].location
                    3 -> return Application.cell.entities[row].rotation
                    4 -> return Application.cell.entities[row].action
                }
            } catch (e: Exception) {
                return null
            }
            return 420
        }

        override fun isCellEditable(row: Int, col: Int): Boolean {
            return if (col == 4) false else true
        }

        override fun setValueAt(value: Any, row: Int, col: Int) {
            //rowData.get(row).get(col) = value
            when (col) {
                0 -> { Application.cell.entities[row] = Application.cell.entities[row].convertTo(value as EntityType); cellTableModel.fireTableDataChanged() }
                1 -> try { Application.cell.entities[row].name = value as String } catch (e: Exception) { ShowInputErrorBox() }
                2 -> try { Application.cell.entities[row].location = Vec3.fromString(value as String) } catch (e: Exception) { ShowInputErrorBox() }
                3 -> try { Application.cell.entities[row].rotation = Vec3.fromString(value as String) } catch (e: Exception) { ShowInputErrorBox() }
                4 -> Application.cell.entities[row].action = value as String
            }
            fireTableCellUpdated(row, col)
        }
    }

    object materialTableModel : AbstractTableModel() {
        override fun getColumnName(col: Int): String {
            when (col) {
                0 -> return "Nosaukums"
                1 -> return "Materiāla veids"
                else -> { return "???" }
            }
        }

        override fun getRowCount(): Int {
            return Application.materials.size
        }

        override fun getColumnCount(): Int {
            return 2
        }

        override fun getValueAt(row: Int, col: Int): Any? {
            // TODO: aizstāt izņēmuma pārbaudīšanu ar kādu citu veidu kā pārbaudīt vai indekss atroda sarakstā
            try {
                when (col) {
                    0 -> return Application.materials[row].name
                    1 -> return Application.materials[row].type
                }
            } catch (e: Exception) {
                return null
            }
            return 420
        }

        override fun isCellEditable(row: Int, col: Int): Boolean {
            return true
        }

        override fun setValueAt(value: Any, row: Int, col: Int) {
            when (col) {
                0 -> try { Application.materials[row].name = value as String } catch (e: Exception) { ShowInputErrorBox() }
                1 -> Application.materials[row].type = value as MaterialType
            }
            fireTableCellUpdated(row, col)
        }
    }

    object languageTableModel : AbstractTableModel() {
        override fun getColumnName(col: Int): String {
            when (col){
                0 -> return "Nosaukums"
                1 -> return "Simbolu virkne"
                else -> { return "???" }
            }
        }

        override fun getRowCount(): Int {
            return Application.language.size
        }

        override fun getColumnCount(): Int {
            return 2
        }

        override fun getValueAt(row: Int, col: Int): Any? {
            // TODO: aizstāt izņēmuma pārbaudīšanu ar kādu citu veidu kā pārbaudīt vai indekss atroda sarakstā
            try {
                when (col) {
                    0 -> return Application.language[row].name
                    1 -> return Application.language[row].string
                }
            } catch (e: Exception) {
                return null
            }
            return 420
        }

        override fun isCellEditable(row: Int, col: Int): Boolean {
            return true
        }

        override fun setValueAt(value: Any, row: Int, col: Int) {
            when (col) {
                0 -> try { Application.language[row].name = value as String } catch (e: Exception) { ShowInputErrorBox() }
                1 -> Application.language[row].string = value as String
            }
            fireTableCellUpdated(row, col)
        }
    }

    init {
        // uztaisīt lielo logu programmas logu, kurā notiks visas darbības
        title = "Tramvaju rediģējamā programma"
        setSize(800, 600)
        setLocationRelativeTo(null)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true

        // uztaisīt augšā celiņu ar visām fails/rediģēt utt. pogām
        mainMenuBar = JMenuBar()

        // fails izvēlne & apakšizvēlnes
        val fileMenu: JMenu = JMenu("Fails")

        val fileMenuSave: JMenuItem = JMenuItem("Saglabāt")
        fileMenuSave.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK)
        fileMenuSave.addActionListener { Application.Save() }
        fileMenu.add(fileMenuSave)

        val fileMenuError: JMenuItem = JMenuItem("Uztaisīt atteici")
        fileMenuError.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK)
        fileMenuError.addActionListener { exitProcess(-1) }
        fileMenu.add(fileMenuError)

        val fileMenuQuit: JMenuItem = JMenuItem("Apglabāt")
        fileMenuQuit.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK)
        fileMenuQuit.addActionListener { exitProcess(0) }
        fileMenu.add(fileMenuQuit)

        // rediģēšanas izvēlne

        val editMenu: JMenu = JMenu("Rediģēt")

        val editMenuNew: JMenuItem = JMenuItem("Jauns")
        editMenuNew.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK)
        editMenuNew.toolTipText = "Pievieno jaunu ierastu"
        editMenu.add(editMenuNew)

        val editMenuRemove: JMenuItem = JMenuItem("Noņemt")
        editMenuRemove.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK)
        editMenuRemove.toolTipText = "Noņem izvēlēto ierakstu"
        editMenu.add(editMenuRemove)

        // toletes izvēlne

        val toiletMenu: JMenu = JMenu()
        val toiletIconSpin = ImageIcon(javaClass.getResource("/tolet20x20.gif"))
        val toiletIconStop = ImageIcon(javaClass.getResource("/toletstop20x20.gif"))
        toiletMenu.icon = toiletIconStop

        val toiletMenuPause: JMenuItem = JMenuItem("Iegriezt")
        toiletMenuPause.addActionListener(){
            if (toiletMenu.icon == toiletIconStop) {
                toiletMenu.icon = toiletIconSpin
                toiletMenuPause.text = "Apstādināt"
            } else {
                toiletMenu.icon = toiletIconStop
                toiletMenuPause.text = "Iegriezt"
            }
        }
        toiletMenu.add(toiletMenuPause)


        // pabeigt augšā celiņu
        mainMenuBar.add(fileMenu)
        mainMenuBar.add(editMenu)
        mainMenuBar.add(Box.createHorizontalGlue())
        mainMenuBar.add(toiletMenu)

        jMenuBar = mainMenuBar

        // paneļi

        val mainPane = JTabbedPane()

        // šūnu panelis
        val cellTableColumnNames = arrayOf("benis", "benis", "benis!")
        val cellPanel: JPanel = JPanel(false)
        val cellTable: JTable = JTable(cellTableModel)

        val cellTableEntityTypeComboBox: JComboBox<EntityType> = JComboBox()
        cellTableEntityTypeComboBox.addItem(EntityType.DEFAULT)
        cellTableEntityTypeComboBox.addItem(EntityType.STATICWOBJ)
        cellTableEntityTypeComboBox.addItem(EntityType.CRATE)
        cellTable.columnModel.getColumn(0).cellEditor = DefaultCellEditor(cellTableEntityTypeComboBox)

        val cellScrollPane: JScrollPane = JScrollPane(cellTable)
        //val cellPropertyScrollPane: JScrollPane = JScrollPane(cellTable)
        //val cellSplit: JSplitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, cellScrollPane, cellPropertyScrollPane)

        //val cellPropertyScrollPane: JScrollPane = CellEntityPropertiesPane(Application.cell.entities[0] as Staticwobj)
        val cellPropertyScrollPane: JScrollPane = JScrollPane()
        val cellSplit: JSplitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT, cellPropertyScrollPane, cellScrollPane)
        cellSplit.dividerLocation = 200
        cellTable.fillsViewportHeight = true
        cellTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)
        //cellPanel.add(cellScrollPane)
        cellPanel.layout = BorderLayout()
        cellPanel.add(cellSplit, BorderLayout.CENTER)

        cellTable.getSelectionModel().addListSelectionListener(ListSelectionListener {
            // print first column value from selected row
            if (!it.valueIsAdjusting) {
                if (cellTable.getSelectedRow() >= 0) {
                    println(cellTable.getValueAt(cellTable.getSelectedRow(), 0).toString())
                    //println(cellTable.selectedRow)
                    cellSplit.leftComponent = CellEntityPropertiesPane(Application.cell.entities[cellTable.selectedRow])
                    cellSplit.dividerLocation = 200
                } else {
                    cellSplit.leftComponent = JPanel()
                    cellSplit.dividerLocation = 200
                }
            }
        })

        mainPane.addTab("Šūnas", null, cellPanel, "Pasaules šūnu rediģēšana")


        // materiāļu panelis
        val materialPanel: JPanel = JPanel(false)
        materialPanel.layout = BorderLayout()

        val materialTable: JTable = JTable(materialTableModel)
        materialTable.autoCreateRowSorter = true
        materialTable.fillsViewportHeight = true

        val materialScrollPane: JScrollPane = JScrollPane(materialTable)
        materialPanel.add(materialScrollPane)

        val materialTableMaterialTypeComboBox: JComboBox<MaterialType> = JComboBox()
        // TODO: uztaisīt lai nav šitā, bet automātiski
        MaterialType.values().forEach { materialTableMaterialTypeComboBox.addItem(it) }
        /*materialTableMaterialTypeComboBox.addItem(MaterialType.FLAT)
        materialTableMaterialTypeComboBox.addItem(MaterialType.ALPHA)
        materialTableMaterialTypeComboBox.addItem(MaterialType.WATER)
        materialTableMaterialTypeComboBox.addItem(MaterialType.LIGHTMAP)*/
        materialTable.columnModel.getColumn(1).cellEditor = DefaultCellEditor(materialTableMaterialTypeComboBox)

        mainPane.addTab("Materiāli", null, materialPanel, "Materiālu īpašību rediģēšana")

        // valodas panelis
        val languagePanel: JPanel = JPanel(false)
        languagePanel.layout = BorderLayout()

        val languageTable: JTable = JTable(languageTableModel)
        languageTable.autoCreateRowSorter = true
        languageTable.fillsViewportHeight = true
        languageTable.columnModel.getColumn(0).preferredWidth = 150
        languageTable.columnModel.getColumn(1).preferredWidth = 650

        val languageScrollPane: JScrollPane = JScrollPane(languageTable)
        languagePanel.add(languageScrollPane)

        mainPane.addTab("Valoda", null, languagePanel, "Valodas simbolu virkņu rediģēšana")

        val importantPanel: JPanel = JPanel(false)
        mainPane.addTab("Svarīgi", null, importantPanel, "Toletes griešanās")

        mainPane.addChangeListener {
            //println(mainPane.selectedIndex)
        }
        // pirmais panelis

        add(mainPane)

        // rediģēšanas izvēlnes darbības
        editMenuNew.addActionListener {
            when (mainPane.selectedIndex) {
                0 -> {
                    Application.cell.addBlankEntity()
                    cellTableModel.fireTableDataChanged()
                }
                1 -> {
                    Application.materials.addBlankMaterial()
                    materialTableModel.fireTableDataChanged()
                }
                2 -> {
                    Application.language.addBlankString()
                    languageTableModel.fireTableDataChanged()
                }
            }
        }

        editMenuRemove.addActionListener {
            when (mainPane.selectedIndex) {
                0 -> {
                    Application.cell.removeEntity(cellTable.selectedRow)
                    cellTableModel.fireTableDataChanged()
                }
                1 -> {
                    Application.materials.removeMaterial(materialTable.selectedRow)
                    materialTableModel.fireTableDataChanged()
                }
                2 -> {
                    Application.language.removeString(languageTable.selectedRow)
                    languageTableModel.fireTableDataChanged()
                }
            }
        }
    }


    public fun Benigonis(){
        //JOptionPane.showMessageDialog(null, "BENIGONIS")
        println("BENIGONIS!!!!")
    }

    }

fun main(args: Array<String>) {
    Application.Init()
    SwingUtilities.invokeLater { val framerino = AppFrame() }
}