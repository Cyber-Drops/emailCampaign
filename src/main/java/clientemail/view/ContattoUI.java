package clientemail.view;

import clientemail.utils.Contatto;
import clientemail.utils.Rubrica;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ContattoUI {
    private JPanel panel1;
    private JTextField emailContattoUI;
    private JTextField nomeContattoUI;
    private JTextField cognomeContattoUI;
    private JTextField telefonoContattoUI;
    private JPanel contattoUIPanel;
    private JButton confermaContattoButton;

    private static ContattoUI contattoUIInstance;


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
            Contatto contatto = new Contatto(dati);
            Rubrica.getRubricaInstance().getContattiRubrica().add(contatto);
            System.out.println(Rubrica.getRubricaInstance());
        });
    }

    public static void setContattoUIInstance(){
        contattoUIInstance = new ContattoUI();
    }
    public static ContattoUI getContattoUIInstance(){
        return contattoUIInstance;
    }

    public JPanel getContattoUIPanel(){
        return contattoUIPanel;
    }
}
