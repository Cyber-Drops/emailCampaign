package clientemail.exception;

import javax.swing.*;

public class FileConfigException extends Exception{
    /**
     * Eccezione sollevata in caso il file di configurazione non sia presente
     */
    public FileConfigException(){
        JOptionPane.showMessageDialog(null, "Errore con il file config.conf, verifica la sua presenza");
    }
}
