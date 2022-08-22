package clientemail.utils;




import clientemail.send.Sender;
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
    private  static String senderEamil = Sender.getUsernameEmail();
    private static String adminEmail = "learning@cyber-drops.com";//"simtemp8@gmail.com";
    private static String cc = "";
    private static String objectEmail = "Critical error Campagna Email App";
    private static String corpoMessaggio;

    //Invio Email Amministratore Errore
    public static void sendLogEmail(StackTraceElement[] stackTraceElement, String errorMessage, int answer){
        /**
         * Crea un nuovo messaggio in caso di crash dell'app da inviare all'admin.
         * Ripulisce la lista dei file allegati, altrimenti arriverebbero nella mail.
         * @param stackTraceElement array contenente lo stacktrace per il debug
         * @param errorMessage Stringa contenente il messaggio di errore per il debug
         */
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
