package clientemail.send;

import clientemail.exception.EmailFormatError;
import clientemail.view.SenderUI;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sender {
    /** TODO Controllo correttezza file email.txt (ogni stringa separata da virgola deve essere nel formato email)
     *  TODO Controllo correttezza dei campi nel formato email sia to che from
     */
    private static  String password;// Password google account associata all'app
    private static String emailString = "";// Stringa delle email lette dal file, sono tutte separate da virgola
    public static List<File> attachFile = new ArrayList<>();// Lista degli allegati
    private static String mailStatus;

    public static void setEmailList(File fileMail){
        /**
         * Metodo che scansiona il file txt contenente le email separate da virgola
         */
        try {
            Scanner scanner = new Scanner(fileMail);
            while (scanner.hasNext()){
                emailString = emailString.concat(scanner.nextLine());
            }
            emailString = emailString.concat(",");
            SenderUI.senderUIInstance.setToText(emailString);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean checkEmailFormat (String stringEmailList){
        String[] listaEmail;
        if (stringEmailList.contains(",")) {
            listaEmail = stringEmailList.split(",");
            System.out.println(Arrays.toString(listaEmail));
        }else {
            listaEmail = new String[1];
            listaEmail[0] = stringEmailList;
        }
        for (String email : listaEmail) {
            if (email.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")) {
                //System.out.println(email);
                return true;
            }
        }
        return false;
    }

    public static void send(String from, String to, String cc, String objectEamil, String corpoMessaggio){
        /**
         *
         */
        try {
            ArrayList<Boolean> formatoEmailValido = new ArrayList<>();
            if (!emailString.isEmpty()){
                formatoEmailValido.add(checkEmailFormat(emailString));
            }
            if (!from.isEmpty()){
                formatoEmailValido.add(checkEmailFormat(from));
            }
            if (!to.isEmpty() && emailString.isEmpty()){
                formatoEmailValido.add(checkEmailFormat(to));
            }
            if (!cc.isEmpty()){
                formatoEmailValido.add(checkEmailFormat(cc));
            }
            //System.out.println(Arrays.toString(formatoEmailValido.toArray()));
            if (formatoEmailValido.contains(false) || from.contains(",")) {
                throw new EmailFormatError();
                }
        }catch (EmailFormatError emailErr){
            return;
            }
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Istanziazione di un oggetto di tipo sessione passando username e password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        // setto il debug su true così da poter vedere eventuali errori
        session.setDebug(true);

        try {
            // Create a default MimeMessage object. Con i dettagli come from, to e oggetto.
            MimeMessage message = new MimeMessage(session);
            // Creo un oggetto MimeBodyPart con il corpo della mail
            MimeBodyPart messaggeBody = new MimeBodyPart();
            // Creo un oggetto MimeBodyPart per l'allegato

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Controllo formato email

            if (!emailString.isEmpty()){
                InternetAddress[] address = InternetAddress.parse(emailString);
                message.addRecipients(Message.RecipientType.TO, address );
            }else {
                // Set To: header field of the header.
                if (to.contains(",")){
                    InternetAddress[] address = InternetAddress.parse(to);
                    message.addRecipients(Message.RecipientType.TO, address );
                }else {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                }
            }
            if (!cc.isEmpty()){
                InternetAddress[] ccAddress = InternetAddress.parse(cc);
                message.addRecipients(Message.RecipientType.CC, ccAddress);
            }

            // Set Subject: header field
            message.setSubject(objectEamil);

            // Now set the actual message
            messaggeBody.setText(corpoMessaggio);
            //Creo oggetto MimeMultipart per includere tutti i Mimepart
            Multipart multipartMessagge = new MimeMultipart();
            multipartMessagge.addBodyPart(messaggeBody);
            // setto l'allegato
            if (!attachFile.isEmpty()){
                System.out.println(Arrays.toString(attachFile.toArray()));
                for (File file : attachFile) {
                    System.out.println(file.getName());
                    MimeBodyPart messaggeAttachment = new MimeBodyPart();
                    messaggeAttachment.setDataHandler(new DataHandler(new FileDataSource(file)));
                    messaggeAttachment.setFileName(file.getName());
                    multipartMessagge.addBodyPart(messaggeAttachment);
                }
            }

            //Aggiungo il MimeMultipart al MimeMessage
            message.setContent(multipartMessagge);

            //MultiThreading, un processo gestisce la progressBar ed un processo l'invio della mail
            executorService.execute(() -> progressBarFill(30));
            executorService.execute(() -> {
                try {
                    Transport.send(message);
                    mailStatus = "sent";
                    reset();
                    executorService.shutdown();
                } catch (MessagingException e) {
                    mailStatus = "not sent";
                    throw new RuntimeException(e);
                }
            });
        }catch (AuthenticationFailedException authEx){
            JOptionPane.showMessageDialog(null,"Password o Username Errati");
        }catch (AddressException addressEx){
            JOptionPane.showMessageDialog(null,"Problemi con gli indirizzi email, si prega di verificare");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
    public static void setPassword(String password) {
        Sender.password = password;
    }
    private static void reset(){
        int answer = JOptionPane.showConfirmDialog(null,"Voui ripulire i campi ed il corpo della email?");
        if (answer == 0){
            SenderUI.senderUIInstance.reset();
            SenderUI.senderUIInstance.setToFocusable(true);
            emailString = "";
        }
    }
    public static void progressBarFill(int percenuale){
        int i = percenuale;
        while (i < 100){
            i += i;
            if (i > 100){
                i = 100;
            }
            try {
                SenderUI.senderUIInstance.setFillBar(i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}

