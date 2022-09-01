package clientemail.utils;


import clientemail.send.Sender;
import clientemail.view.Config;
import clientemail.view.SenderUI;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.Arrays;

public class Log {
    /**
     * senderEmail->email utente
     * adminEmail->email amministratore app
     */
    private  static String senderEamil = Config.getInstanceConfig().getFromAddressConfigFile();
    private static String adminEmail = "simtemp8@gmail.com";//"learning@cyber-drops.com";
    private static String cc = "";
    private static String objectEmail = "Critical error Campagna Email App";
    private static String corpoMessaggio;

    /**
     *Crea un nuovo messaggio (Pulito) ed invia l'email all'amministratore con il consenso dell'utente
     * in caso di errore non gestito.
     * @param stackTraceElement tipo Array contiene gli elementi dello stackTrace per debug
     * @param errorMessage tipo String il messaggio di errore per debug
     * @param answer tipo int il consenso dell'utente
     */
    public static void sendLogEmail(StackTraceElement[] stackTraceElement, String errorMessage, int answer){
        System.out.println(senderEamil);
        corpoMessaggio = Arrays.toString(stackTraceElement).concat(System.lineSeparator()).concat(errorMessage).concat(System.lineSeparator().concat("Risposta: ".concat(String.valueOf(answer))));
        Session session = Sender.createSession(senderEamil);
        MimeMessage message = Sender.componiMessaggio(session, senderEamil, adminEmail, cc, objectEmail , corpoMessaggio);
        try {
            Transport.send(message);
            JOptionPane.showMessageDialog(null, "Errore notificato all'admin");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
