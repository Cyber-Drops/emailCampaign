package clientemail.view;

import clientemail.send.Sender;
import clientemail.utils.Contatto;
import clientemail.utils.Rubrica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
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
    private JButton rimuovuButton;
    private JButton modificaButton;
    private boolean modificaIsPressed = false;
    private JButton indietroRubricaUI;
    private JTextField ricercaField;
    private JButton caricaButtonRubrica;
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
        rimuovuButton.addActionListener(e->{
            int nRowSelected = rubricaTable.getSelectedRowCount();
            int firstSelecteRow = rubricaTable.getSelectedRow();
            if (nRowSelected == 1){
                Rubrica.getRubricaInstance().getContattiRubrica().remove(firstSelecteRow);//Rimuovo il contatto da Rubrica
                ((DefaultTableModel)rubricaTable.getModel()).removeRow(firstSelecteRow);//Rimuovo la riga dalla tab
            }else {
                for (int i = firstSelecteRow+nRowSelected-1; i >= firstSelecteRow; i-- ){
                    Rubrica.getRubricaInstance().getContattiRubrica().remove(i);//Rimuovo il contatto da Rubrica
                    ((DefaultTableModel)rubricaTable.getModel()).removeRow(i);
                }
            }
            Rubrica.getRubricaInstance().salvaRubricaGson();
        });
        modificaButton.addActionListener(e->{
            int firstSelecteRow = rubricaTable.getSelectedRow();
            Object email = rubricaTable.getValueAt(firstSelecteRow,0);
            Object nome = rubricaTable.getValueAt(firstSelecteRow,1);
            Object cognome = rubricaTable.getValueAt(firstSelecteRow,2);
            Object telefono = rubricaTable.getValueAt(firstSelecteRow,3);
            ContattoUI.getContattoUIInstance().getEmailContattoUI().setText((String) email);
            ContattoUI.getContattoUIInstance().getNomeContattoUI().setText((String) nome);
            ContattoUI.getContattoUIInstance().getCognomeContattoUI().setText((String) cognome);
            ContattoUI.getContattoUIInstance().getTelefonoContattoUI().setText((String) telefono);
            setModificaIsPressed(true);
            PanelManage.loadConattoPanel(e);
            Rubrica.getRubricaInstance().salvaRubricaGson();
        });
        indietroRubricaUI.addActionListener(e->{
            if (SenderUI.senderUIInstance.isCaricaDaRubricaButtonPressed()){
                PanelManage.loadSendPanel(e);
            }else {
                PanelManage.loadCreaCaricaConfigPanel();
            }
        });
        ricercaField.addKeyListener(new KeyListener() {
            List<Character> charsListRicerca = new ArrayList<>();
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                //ricercaString.append(e.getKeyChar());
                if (e.getKeyCode() == 8 && !charsListRicerca.isEmpty()){
                    System.out.println("carattere rimosso: " + charsListRicerca.get(charsListRicerca.size()-1));
                    charsListRicerca.remove(charsListRicerca.size()-1);
                }else {
                    charsListRicerca.add(e.getKeyChar());
                }
                aggiornaRubricaRicerca(charsListRicerca);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        caricaButtonRubrica.addActionListener(e->{
            String emailList = "";
            int firstSelectedRow = rubricaTable.getSelectedRow();
            int nRowSelected = rubricaTable.getSelectedRowCount();
            if (firstSelectedRow == -1){
                JOptionPane.showMessageDialog(null, "Seleziona almeno una riga!!");
            }else {
                System.out.println(firstSelectedRow+" "+nRowSelected);
                if (nRowSelected == 1){
                    emailList += rubricaTable.getValueAt(firstSelectedRow,0);
                    emailList += ",";
                }else {
                    for (int i = firstSelectedRow; i <= firstSelectedRow+firstSelectedRow; i++){
                        System.out.println("Dentro");
                        emailList += rubricaTable.getValueAt(i,0);
                        emailList += ",";
                    }
                }
                System.out.println(emailList);
                SenderUI.senderUIInstance.setTo(emailList);
                PanelManage.loadSendPanel(e);
            }

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
        deleteAllRow();
        System.out.println("aggiornata");
        //rubricaTable.setAutoCreateRowSorter(true);
        List<Contatto> rubricaList = Rubrica.getRubricaInstance().getContattiRubrica();
        for (Contatto contatto : rubricaList) {
            ((DefaultTableModel)rubricaTable.getModel()).addRow(contatto.getDatiContatto().toArray());
        }
        //model.addRow(rubricaList.toArray());
        //int row = model.getRowCount() - 1;
        /*
        int row = 0;
        //System.out.println(row);
        if (!rubricaList.isEmpty()) {
            for (Contatto contatto : rubricaList) {
                //model.addRow(rubricaList.toArray());
                //System.out.println(contatto);
                int col = 0;
                for (Object dato : contatto.getDatiContatto()) {
                    model.setValueAt(dato, row, col);
                    col++;
                }
                row++;
            }
        }
        */
    }

    /**
     * Dopo aver ripulito la JTable visualizza a video i contatti interessati dalla ricerca
     * @param contattoList contatti risultanti dalla ricerca
     */
    public void aggiornaRubricaUI(List<Contatto> contattoList){
        deleteAllRow();
        System.out.println("aggiornata Ricerca");
        if (!contattoList.isEmpty()) {
            for (Contatto contatto : contattoList) {
                ((DefaultTableModel)rubricaTable.getModel()).addRow(contatto.getDatiContatto().toArray());
            }
        }
    }

    /**
     * Aggiorna la Rubrica e la RubricaUI se viene richiesta la modifica del contatto
     */
    public void aggiornaRubricaModifica(){//Chiamato da ContattoUI, confermaButton, se e solo se siamo entrati
                                        //nel pannello ContattoUI tramite il bottone modifica che setta il
                                        //booleano modificaIsPressed su true
        int firstSelecteRow = rubricaTable.getSelectedRow();
        ((DefaultTableModel)rubricaTable.getModel()).removeRow(firstSelecteRow);
        setModificaIsPressed(false);
        System.out.println("secondo first: "+firstSelecteRow);
    }

    public boolean isModificaIsPressed() {
        return modificaIsPressed;
    }

    public void setModificaIsPressed(boolean modificaIsPressed) {
        this.modificaIsPressed = modificaIsPressed;
    }

    public void setEnableCaricaButton(){
        caricaButtonRubrica.setEnabled(true);
    }
    public void setDisableCaricaButton(){
        caricaButtonRubrica.setEnabled(false);
    }

    /**
     * Aggiorna la visualizzazione della rubrica con la lista di contatti che fanno math con la
     * ricerca
     * @param characterList contatti che fanno match con la stringa di ricerca
     */
    public void aggiornaRubricaRicerca(List<Character> characterList){
        deleteAllRow();
        if (characterList.isEmpty()){
            aggiornaRubricaUI();
        }
        String ricercaString = "";
        List<Contatto> contattiRicercati = new ArrayList<>();
        /*
        for (Character chars : characterList) {
            ricercaString += chars;
        }
         */
        System.out.println(Arrays.toString(contattiRicercati.toArray()));
        for (Contatto contatto : Rubrica.getRubricaInstance().getContattiRubrica()) {
            String stringaVerifica = contatto.getDatiContatto().get(0);
            char[] stringaVerificaArr = stringaVerifica.toCharArray();
            int index = 0;
            for (char ch : characterList) {
                System.out.println(ch+" "+stringaVerificaArr[index]);
                if (ch == stringaVerificaArr[index]) {
                    if (characterList.size()-1 == index){
                        contattiRicercati.add(contatto);
                        aggiornaRubricaUI(contattiRicercati);
                    }
                }else {
                    break;
                }
                index++;
            }
            /*
            if (stringaVerifica.startsWith(ricercaString)){
                contattiRicercati.add(contatto);
                aggiornaRubricaUI(contattiRicercati);
            }

             */

        }
        System.out.println(ricercaString);
    }

    /**
     * Prepara la JTable per la visualizzazione dei soli contatti ricercati cancellando tutte le righe
     */
    public void deleteAllRow(){
        for (int i = rubricaTable.getRowCount()-1; i >= 0 ; i--){
            ((DefaultTableModel)rubricaTable.getModel()).removeRow(i);
        }
    }
}