package clientemail.exception;

import javax.swing.*;

public class FileConfigException extends Exception{
    public FileConfigException(){
        JOptionPane.showMessageDialog(null, "Errore con il file config.conf, verifica la sua presenza");
    }
}
