package clientemail.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Rubrica {
    private JPanel panel1;
    private JScrollPane rubricaScrollPane;
    private JTable rubricaTable;
    private final static Rubrica rubricaUIinstance = new Rubrica();

    public Rubrica(){
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
    }
    public static Rubrica getRubricaUIinstance(){
        return rubricaUIinstance;
    }

    public JPanel getPanel1(){
        return panel1;
    }
}
