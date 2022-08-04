package clientemail.send;


import clientemail.view.SenderUI;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Sender {
    private static final String password = "vtmherotsrjpohuw";//Password google account associata all'app
    private static String emailString = "";//Stringa delle email lette dal file, sono tutte separate da virgola
    public static List<File> attachFile = new ArrayList<>();//Lista degli allegati

    public static void setEmailList(File fileMail){

        try {
            Scanner scanner = new Scanner(fileMail);
            while (scanner.hasNext()){
                emailString = emailString.concat(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void send(String from, String to, String cc, String objectEamil, String corpoMessaggio){

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, password);

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object. Con i dettagli come from, to e oggetto.
            MimeMessage message = new MimeMessage(session);
            // Creo un oggetto MimeBodyPart con il corpo della mail
            MimeBodyPart messaggeBody = new MimeBodyPart();
            // Creo un oggetto MimeBodyPart per l'allegato

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            if (!emailString.isEmpty()){
                InternetAddress[] address = InternetAddress.parse(emailString);
                message.addRecipients(Message.RecipientType.TO, address );
                System.out.println(Arrays.toString(address));
            }else {
                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            }
            if (!cc.isEmpty()){
                InternetAddress[] ccAddress = InternetAddress.parse(cc);
                message.addRecipients(Message.RecipientType.CC, ccAddress);
                System.out.println(Arrays.toString(ccAddress));
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
                    //attachPart.add(messaggeAttachment);
                    //messaggeAttachment.attachFile(file);
                    //multipartMessagge.addBodyPart(messaggeAttachment);
                }
            }
            //Aggiungo il MimeMultipart al MimeMessage
            message.setContent(multipartMessagge);
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            int answer = JOptionPane.showConfirmDialog(null,"Voui ripulire i campi ed il corpo della email?");
            if (answer == 0){
                reset();
            }

        }catch (AuthenticationFailedException authEx){
            JOptionPane.showMessageDialog(null,"Password o Username Errati");
        }catch (AddressException addressEx){
            JOptionPane.showMessageDialog(null,"Problemi con gli indirizzi email, si prega di verificare");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        /*catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Errore Allegato");
        }
         */
    }
    private static void reset(){
        SenderUI.senderUIInstance.reset();
        emailString = "";
    }

}

