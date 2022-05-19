import com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table
import java.awt.BorderLayout
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import javax.swing.*
import javax.swing.event.ListSelectionListener
import javax.swing.table.AbstractTableModel
import kotlin.system.exitProcess


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
            return Application.cell.entities.size
        }

        override fun getColumnCount(): Int {
            //return columnNames.length
            return 5
        }

        override fun getValueAt(row: Int, col: Int): Any {
            //return rowData.get(row).get(col)
            when (col) {
                0 -> return Application.cell.entities[row].type
                1 -> return Application.cell.entities[row].name
                2 -> return Application.cell.entities[row].location
                3 -> return Application.cell.entities[row].rotation
                4 -> return Application.cell.entities[row].action
            }
            return 420
        }

        override fun isCellEditable(row: Int, col: Int): Boolean {
            return if (col == 4) false else true
        }

        override fun setValueAt(value: Any, row: Int, col: Int) {
            //rowData.get(row).get(col) = value
            when (col) {
                0 -> Application.cell.entities[row] = Application.cell.entities[row].convertTo(value as EntityType)
                1 -> Application.cell.entities[row].name = value as String
                2 -> try { Application.cell.entities[row].location = Vec3.fromString(value as String) } catch (e: Exception) {JOptionPane.showMessageDialog(null, "Nezināmas izcelsmes ievades kļūda", "Kļūda", JOptionPane.ERROR_MESSAGE)}
                3 -> try { Application.cell.entities[row].rotation = Vec3.fromString(value as String) } catch (e: Exception) {JOptionPane.showMessageDialog(null, "Nezināmas izcelsmes ievades kļūda", "Kļūda", JOptionPane.ERROR_MESSAGE)}
                4 -> Application.cell.entities[row].action = value as String
            }
            fireTableCellUpdated(row, col)
        }
    }

    init {
        // uztaisīt lielo logu programmas logu, kurā notiks visas darbības
        title = "Jānis Lielais Dambergs!"
        setSize(800, 600)
        setLocationRelativeTo(null)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true

        // uztaisīt augšā celiņu ar visām fails/rediģēt utt. pogām
        mainMenuBar = JMenuBar()

        // fails izvēlne & apakšizvēlnes
        val fileMenu: JMenu = JMenu("Fails")
        fileMenu.accessibleContext.accessibleDescription = "Dzimumlocekļa palielināšana"

        val fileMenuSave: JMenuItem = JMenuItem("Saglabāt")
        fileMenuSave.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK)
        fileMenuSave.addActionListener { Benigonis() }
        fileMenu.add(fileMenuSave)

        val fileMenuError: JMenuItem = JMenuItem("Uztaisīt atteici")
        fileMenuError.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK)
        fileMenuError.addActionListener { exitProcess(-1) }
        fileMenu.add(fileMenuError)

        val fileMenuQuit: JMenuItem = JMenuItem("Apglabāt")
        fileMenuQuit.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK)
        fileMenuQuit.addActionListener { exitProcess(0) }
        fileMenu.add(fileMenuQuit)

        // pabeigt augšā celiņu
        mainMenuBar.add(fileMenu)
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

        cellTable.getSelectionModel().addListSelectionListener(ListSelectionListener { // do some actions here, for example
            // print first column value from selected row
            if (!it.valueIsAdjusting) {
                println(cellTable.getValueAt(cellTable.getSelectedRow(), 0).toString())
                //println(cellTable.selectedRow)
                cellSplit.leftComponent = CellEntityPropertiesPane(Application.cell.entities[cellTable.selectedRow])
                cellSplit.dividerLocation = 200
            }
        })

        mainPane.addTab("Šūnas", null, cellPanel, "Dzimumlocekļa palielināšana")

        val materialPanel: JPanel = JPanel(false)
        mainPane.addTab("Materiāli", null, materialPanel, "Dzimumlocekļa palielināšana")

        val languagePanel: JPanel = JPanel(false)
        mainPane.addTab("Valoda", null, languagePanel, "Dzimumlocekļa palielināšana")

        val importantPanel: JPanel = JPanel(false)
        mainPane.addTab("Svarīgi", null, importantPanel, "Dzimumlocekļa palielināšana")

        // pirmais panelis

        add(mainPane)

        //pack()
    }


    public fun Benigonis(){
        //JOptionPane.showMessageDialog(null, "BENIGONIS")
        println("BENIGONIS!!!!")
    }

    }

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    AppMakeData()

    SwingUtilities.invokeLater { val framerino = AppFrame() }
}