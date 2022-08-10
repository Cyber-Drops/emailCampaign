package clientemail.exception;

import javax.swing.*;

public class EmailFormatError extends Exception {
    public EmailFormatError(){
        JOptionPane.showMessageDialog(null, "Formato email non valido.");
    }
}
