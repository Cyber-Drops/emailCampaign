package clientemail.view;

import clientemail.utils.Contatto;
import clientemail.utils.Rubrica;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Grafica dei dati di un oggetto di tipo Contatto, con comandi per aggiungere il contatto.
 */
public class ContattoUI {
    private JPanel panel1;
    private boolean pressed = false;
    private JTextField emailContattoUI;
    private JTextField nomeContattoUI;
    private JTextField cognomeContattoUI;
    private JTextField telefonoContattoUI;
    private JPanel contattoUIPanel;
    private JButton confermaContattoButton;

    private JButton indietroContattoUIButton;

    private static ContattoUI contattoUIInstance;

    /**
     * Costruttore della grafica per aggiungere un contatto, inizializza anche i componenti della
     * finestra.
     * Alla conferma di aggiunta del contatto, crea un oggetto di tipo Contatto con i dati inseriti
     * dall'utente nei vari form, aggiungendolo alla Rubrica, salvandolo nel file rubrica Gson ed aggiorna
     * la JTable della rubrica a livello grafico
     */
    private ContattoUI(){
        confermaContattoButton.addActionListener(e->{
            String email = emailContattoUI.getText();
            String nome = nomeContattoUI.getText();
            String cognome = cognomeContattoUI.getText();
            String telefono = telefonoContattoUI.getText();
            List<String> dati = new ArrayList<>();
            dati.add(email);
            dati.add(nome);
            dati.add(cognome);
            dati.add(telefono);
            Contatto contatto = new Contatto(dati); //Nuova istanza di Contatto
            Rubrica.getRubricaInstance().getContattiRubrica().add(contatto);
            Rubrica.getRubricaInstance().salvaRubricaGson();
            RubricaUI.getRubricaUIinstance().aggiornaRubricaUI(contatto);
            setConfermaButtonIsPressed(true);
            System.out.println(Rubrica.getRubricaInstance());
        });
        indietroContattoUIButton.addActionListener(e->{
            resetUIForm();
            PanelManage.loadRubricaPanel(e);
        });
    }

    public boolean confermaButtonIsPressed(){
        return this.pressed;
    }
    public void setConfermaButtonIsPressed(boolean pressed){
        this.pressed = pressed;
    }

    public static void setContattoUIInstance(){
        contattoUIInstance = new ContattoUI(); //Chiamato nel PanelManage startUI()
    }
    public static ContattoUI getContattoUIInstance(){
        return contattoUIInstance;
    }

    public JPanel getContattoUIPanel(){
        return contattoUIPanel; // Per il caricamento della finestra per aggiungere il contatto.
    }

    public JTextField getEmailContattoUI() {
        return emailContattoUI;
    }

    public JTextField getNomeContattoUI() {
        return nomeContattoUI;
    }

    public JTextField getCognomeContattoUI() {
        return cognomeContattoUI;
    }

    public JTextField getTelefonoContattoUI() {
        return telefonoContattoUI;
    }

    /**
     * Ripulisce i campi nel form della finestra per aggiungere il contatto
     */
    public void resetUIForm(){
        emailContattoUI.setText("");
        nomeContattoUI.setText("");
        cognomeContattoUI.setText("");
        telefonoContattoUI.setText("");

    }
}
