package clientemail.view;

import clientemail.utils.Contatto;
import clientemail.utils.Rubrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class RubricaUI {
    private JPanel panel1;
    private JScrollPane rubricaScrollPane;
    private JTable rubricaTable;
    private JButton aggiungiButton;
    private JButton RimuovuButton;
    private JButton modificaButton;
    private JButton cercaButton;
    private final static RubricaUI rubricaUIinstance = new RubricaUI();

    public RubricaUI() {
        List<Contatto> rubricaList = Rubrica.getRubricaInstance().getContattiRubrica();
        String[][] s = new String[rubricaList.size()][4];
        Object[] columnNames = { "", "" , "",""};
        DefaultTableModel model = new DefaultTableModel(s,columnNames);
        rubricaTable.setModel(model);
        rubricaTable.getColumnModel().getColumn(0).setHeaderValue("Email");
        rubricaTable.getColumnModel().getColumn(1).setHeaderValue("Nome");
        rubricaTable.getColumnModel().getColumn(2).setHeaderValue("Cognome");
        rubricaTable.getColumnModel().getColumn(3).setHeaderValue("Telefono");
        rubricaTable.getTableHeader().repaint();
        aggiungiButton.addActionListener(e->{
            PanelManage.loadConattoPanel(e);
            for (Contatto contatto : rubricaList) {
                for (Object dato : contatto) {
                    System.out.println(dato);
                }
            }
        });
    }

    public static RubricaUI getRubricaUIinstance() {
        return rubricaUIinstance;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void aggiungiContatto(String email, String nome, String cognome) {

    }

    public void aggiungiContatto(String email, String nome, String cognome, String telefono) {

    }
}