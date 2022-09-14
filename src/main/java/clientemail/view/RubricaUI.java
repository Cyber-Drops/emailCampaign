package clientemail.view;

import clientemail.utils.Contatto;
import clientemail.utils.Rubrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;

/**
 * Visualizzazione grafica della rubrica, con i vari comandi per interagire con la stessa.
 */
public class RubricaUI {
    private JPanel panel1;
    private JScrollPane rubricaScrollPane;
    private JTable rubricaTable;
    private DefaultTableModel model;
    private JButton aggiungiButton;
    private JButton RimuovuButton;
    private JButton modificaButton;
    private JButton cercaButton;
    private JButton indietroRubricaUI;
    private final static RubricaUI rubricaUIinstance = new RubricaUI();

    /**
     * Costruttore della grafica di una rubrica, rappresentata da una tabella Jtable, popolata
     * dai dati dei contatti (Oggetto Contatto) contenuto nella Rubrica.
     * Inoltre inizializza i componenti della UI
     */
    public RubricaUI() {
        List<Contatto> rubricaList = Rubrica.getRubricaInstance().getContattiRubrica();
        String[][] s = new String[rubricaList.size()][4];// Variabile per inizializzare righe e colonne
        Object[] columnNames = { "", "" , "",""};// Variabile pèer inizializzare il nome delle colonne
        model = new DefaultTableModel(s,columnNames);// Creo il modello in base al numero righe, colonne e nome colonne
        rubricaTable.setModel(model);
        rubricaTable.getColumnModel().getColumn(0).setHeaderValue("Email");// Setto i nomi delle colonne
        rubricaTable.getColumnModel().getColumn(1).setHeaderValue("Nome");
        rubricaTable.getColumnModel().getColumn(2).setHeaderValue("Cognome");
        rubricaTable.getColumnModel().getColumn(3).setHeaderValue("Telefono");
        rubricaTable.getTableHeader().repaint();// Aggiorno la grafice della JTable

        //Creo un oggetto tabella ordinata passando il modello della mia JTable
        TableRowSorter<TableModel> sorterTable = new TableRowSorter<>(rubricaTable.getModel());
        rubricaTable.setRowSorter(sorterTable); //Imposto le righe come ordinabili
        sorterTable.setSortable(0,true); //Imposto le colonne come ordinabili, cliccando sull'intestazione
        sorterTable.setSortable(1,true);
        sorterTable.setSortable(2,true);
        sorterTable.setSortable(3,true);

        aggiungiButton.addActionListener(e->{
            PanelManage.loadConattoPanel(e);

        });
        indietroRubricaUI.addActionListener(e->{
            PanelManage.loadCreaCaricaConfigPanel();
        });
    }

    public static RubricaUI getRubricaUIinstance() {
        return rubricaUIinstance;
    }

    public JPanel getPanel1() {
        return panel1;// Usato per ricaricare la finestra della rubrica
    }

    /**
     * Aggiorna la JTable rappresentante la rubrica dopo aver aggiunto un contatto
     * @param contatto Oggetto di tipo Contatto, contatto appena aggiunto alla Rubrica
     */
    public void aggiornaRubricaUI(Contatto contatto){
        //TODO impostare per l'aggiunta di un contatto l'indice dell'ultima riga, senza far ricaricare tutta la rubrica
        //rubricaTable.setAutoCreateRowSorter(true);
        List<Contatto> rubricaList = Rubrica.getRubricaInstance().getContattiRubrica();
        model.addRow(rubricaList.toArray());// Si assicura che il contatto venga aggiunto in ultima posizione
        int row = model.getRowCount()-1;
        //System.out.println(row);
        int col = 0;
            for (Object dato : contatto.getDatiContatto()) {
                model.setValueAt(dato, row, col);
                col++;
            }
    }

    /**
     * Overload di aggiornaRubrica(Contatto), aggiorna la rubrica a livello grafico prelevando
     * la rubrica con i suoi contatti ed i suoi dati dal file Gson rubrica.
     */
    public void aggiornaRubricaUI(){
        //rubricaTable.setAutoCreateRowSorter(true);
        List<Contatto> rubricaList = Rubrica.getRubricaInstance().getContattiRubrica();
        //model.addRow(rubricaList.toArray());
        //int row = model.getRowCount() - 1;
        int row = 0;
        System.out.println(row);
        //int row = 0;
        if (!rubricaList.isEmpty()) {
            for (Contatto contatto : rubricaList) {
                //model.addRow(rubricaList.toArray());
                System.out.println(contatto);
                int col = 0;
                for (Object dato : contatto.getDatiContatto()) {
                    model.setValueAt(dato, row, col);
                    col++;
                }
                row++;
            }
        }
    }
}