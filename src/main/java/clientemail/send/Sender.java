package clientemail.send;

import clientemail.exception.EmailFormatError;
import clientemail.utils.EmailConstructor;
import clientemail.utils.Log;
import clientemail.view.SenderUI;
import com.sun.mail.smtp.SMTPSenderFailedException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.io.File;
import java.sql.Struct;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sender {
    /**
     *  TODO Parsing di un file json per comporre: object email, corpo email ed allegati(allegato:percorso)
     *  TODO Nel file.conf (di configurazione) aggiungere le proprietà del email server (es. smtp.gmail.com.....)
     *
     */
    private static  String passwordEmail;// Password google account associata all'app
    private static String usernameEmail;
    private static String emailString = "";// Stringa delle email lette dal file, sono tutte separate da virgola
    public static List<File> attachFile = new ArrayList<>();// Lista degli allegati
    public static String mailStatus;

    public static String getEmailString() {
        return emailString;
    }

    public static String getPassword() {
        return passwordEmail;
    }

    public static void setEmailList(File fileMail){
        emailString = EmailConstructor.setEmailList(fileMail, emailString);
    }

    public static Session createSession(String from){
        // Imposto i parametri del server email, poi in file.conf
        String host = "smtp.gmail.com";

        // Recupero le proprietà del sistema e creo l'oggetto Proprierties
        Properties properties = System.getProperties();

        // Aggiungo i parametri per il server email
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Istanziazione di un oggetto di tipo sessione passando username e password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, passwordEmail);
            }
        });

        // setto il debug su true così da poter vedere eventuali errori
        //session.setDebug(true);
        return session;
    }

    public static MimeMessage componiMessaggio(Session session, String from, String to, String cc, String objectEamil, String corpoMessaggio) {
        // Create a default MimeMessage object. Con i dettagli come from, to e oggetto.
        MimeMessage message = new MimeMessage(session);
        try {
            // Creo un oggetto MimeBodyPart con il corpo della mail
            MimeBodyPart messaggeBody = new MimeBodyPart();

            // Setto il campo header con l'email From
            message.setFrom(new InternetAddress(from));
            // Controllo formato email

            if (!emailString.isEmpty()) {
                InternetAddress[] address = InternetAddress.parse(emailString);
                message.addRecipients(Message.RecipientType.TO, address);
            } else {
                // Set To: header field of the header.
                if (to.contains(",")) {
                    InternetAddress[] address = InternetAddress.parse(to);
                    message.addRecipients(Message.RecipientType.TO, address);
                } else {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                }
            }
            if (!cc.isEmpty()) {
                InternetAddress[] ccAddress = InternetAddress.parse(cc);
                message.addRecipients(Message.RecipientType.CC, ccAddress);
            }

            // Imposto il campo oggetto dell' header
            message.setSubject(objectEamil);

            // Imposto il corpo del messaggio
            messaggeBody.setText(corpoMessaggio);
            //Creo oggetto MimeMultipart per includere tutti i Mimepart
            Multipart multipartMessagge = new MimeMultipart();
            multipartMessagge.addBodyPart(messaggeBody);
            // setto l'allegato
            if (!attachFile.isEmpty()) {
                System.out.println(Arrays.toString(attachFile.toArray()));
                for (File file : attachFile) {
                    System.out.println(file.getName());
                    // Creo un oggetto MimeBodyPart per l'allegato
                    MimeBodyPart messaggeAttachment = new MimeBodyPart();
                    messaggeAttachment.setDataHandler(new DataHandler(new FileDataSource(file)));
                    messaggeAttachment.setFileName(file.getName());
                    multipartMessagge.addBodyPart(messaggeAttachment);
                }
            }
            //Aggiungo il MimeMultipart al MimeMessage
            message.setContent(multipartMessagge);

        } catch (AuthenticationFailedException authEx) {
            JOptionPane.showMessageDialog(null, "Password o Username Errati");
        } catch (AddressException addressEx) {
            JOptionPane.showMessageDialog(null, "Problemi con gli indirizzi email, si prega di verificare");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return message;
    }

    public static void send(String from, String to, String cc, String objectEamil, String corpoMessaggio){
        /**
         *
         */

        Session session = createSession(from);

        MimeMessage message = componiMessaggio(session, from, to, cc, objectEamil, corpoMessaggio);
        try {
            EmailConstructor.checkEmail(from,to,cc,emailString);
        } catch (EmailFormatError e) {
            return;
        }
        //MultiThreading, un processo gestisce la progressBar ed un processo l'invio della mail
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(() -> {
            try {
                executorService.execute(() -> SenderUI.progressBarFill(15, true));
                Transport.send(message);
                mailStatus = "sent";
                reset();
                executorService.shutdown();
            } catch (AuthenticationFailedException authFail){
                executorService.execute(() -> SenderUI.progressBarFill(20, false));
                executorService.shutdown();
                JOptionPane.showMessageDialog(null, "Autenticazione Fallita, controlla email from o password");
            }catch (SMTPSenderFailedException senderFailedException){
                JOptionPane.showMessageDialog(null, senderFailedException.getMessage());
            }catch (MessagingException e) {
                if (e.getMessage().contains("Your message exceeded Google's message size limits")) {
                    JOptionPane.showMessageDialog(null,e.getMessage().substring(10,60).concat(System.lineSeparator().concat("Usa Google Drive ed Invia il Link")));
                }
                mailStatus = "not sent";
                int answer = JOptionPane.showConfirmDialog(null, "Errore sconosciuto, invio Errore all'amministratore?\n" +
                        "SI acconsenti all' invio con la tua email un messaggio dell'errore all'amministratore con conseguente invio del tuo " +
                        "indirizzo email, NO non verrà inviato nulla.");
                if (0 == answer ){
                    attachFile.clear();
                    Log.sendLogEmail(e.getStackTrace(), e.getMessage(), answer);
                }

                System.exit(1);
            }
        });

    }
    public static void setPassword(String password) {
        passwordEmail = password;
    }
    public static void setUsername(String username){
        usernameEmail = username;
    }

    public static String getUsernameEmail() {
        return usernameEmail;
    }

    private static void reset(){
            if (SenderUI.senderUIInstance.reset()){
                emailString = "";
            }
    }

}

