package clientemail.utils;


import javax.swing.*;
import java.util.Arrays;

public class Log {


    //Invio Email Amministratore Errore
    public static void sendLogEmail(StackTraceElement[] stackTraceElement, String errorMessage){
        System.out.println("Error"+Arrays.toString(stackTraceElement)+"\n"+errorMessage);
        JOptionPane.showMessageDialog(null, stackTraceElement+ " \n"+errorMessage);
    }

}
