package clientemail.exception;

import javax.swing.*;

public class EmailFormatError extends Exception {
    /**
     * Eccezione sollevata quando la mail non è conforme
     */
    public EmailFormatError(){
        JOptionPane.showMessageDialog(null, "Formato email non valido.");
    }
}
