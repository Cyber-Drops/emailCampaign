package clientemail.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RubricaUI {
    private List<String[]> contatto = new ArrayList<>();
    private List<List> rubrica = new ArrayList<>();
    private JPanel panel1;
    private JScrollPane rubricaScrollPane;
    private JTable rubricaTable;
    private JButton aggiungiButton;
    private JButton RimuovuButton;
    private JButton modificaButton;
    private JButton cercaButton;
    private final static RubricaUI rubricaUIinstance = new RubricaUI();

    public RubricaUI() {
        /*
        String[][] s = new String[4][4];
        Object[] columnNames = { "", "" , "",""};
        s[0][0] = "ciao";
        DefaultTableModel model = new DefaultTableModel(s,columnNames);
        rubricaTable.setModel(model);
        rubricaTable.getColumnModel().getColumn(0).setHeaderValue("Email");
        rubricaTable.getColumnModel().getColumn(1).setHeaderValue("Nome");
        rubricaTable.getColumnModel().getColumn(2).setHeaderValue("Cognome");
        rubricaTable.getColumnModel().getColumn(3).setHeaderValue("Telefono");
        rubricaTable.getTableHeader().repaint();
         */
        aggiungiButton.addActionListener(e->{
            PanelManage.loadConattoPanel(e);
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