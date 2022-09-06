package clientemail.view;

import clientemail.utils.Contatto;

import javax.swing.*;

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
            String telefono = cognomeContattoUI.getText();
            if (telefonoContattoUI.getText().isEmpty()) {
                Contatto contatto = new Contatto(email, nome, cognome);
                System.out.println(contatto);
            }else {
                Contatto contatto1 = new Contatto(email, nome, cognome, telefono);
                System.out.println(contatto1);
            }
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
