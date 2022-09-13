package clientemail.view;

import clientemail.utils.Contatto;
import clientemail.utils.Rubrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;

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

    public RubricaUI() {
        List<Contatto> rubricaList = Rubrica.getRubricaInstance().getContattiRubrica();
        String[][] s = new String[rubricaList.size()][4];
        Object[] columnNames = { "", "" , "",""};
        model = new DefaultTableModel(s,columnNames);
        rubricaTable.setModel(model);
        rubricaTable.getColumnModel().getColumn(0).setHeaderValue("Email");
        rubricaTable.getColumnModel().getColumn(1).setHeaderValue("Nome");
        rubricaTable.getColumnModel().getColumn(2).setHeaderValue("Cognome");
        rubricaTable.getColumnModel().getColumn(3).setHeaderValue("Telefono");
        rubricaTable.getTableHeader().repaint();

        TableRowSorter<TableModel> sorterTable = new TableRowSorter<>(rubricaTable.getModel());
        rubricaTable.setRowSorter(sorterTable);
        sorterTable.setSortable(0,true);
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
        return panel1;
    }

    public void aggiornaRubricaUI(Contatto contatto){
        rubricaTable.setAutoCreateRowSorter(true);
        List<Contatto> rubricaList = Rubrica.getRubricaInstance().getContattiRubrica();
        model.addRow(rubricaList.toArray());
        int row = model.getRowCount()-1;
        //System.out.println(row);
        int col = 0;
            for (Object dato : contatto.getDatiContatto()) {
                model.setValueAt(dato, row, col);
                col++;
            }
    }
    public void aggiornaRubricaUI(){
        rubricaTable.setAutoCreateRowSorter(true);
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

    public void aggiungiContatto(String email, String nome, String cognome) {

    }

    public void aggiungiContatto(String email, String nome, String cognome, String telefono) {

    }
}