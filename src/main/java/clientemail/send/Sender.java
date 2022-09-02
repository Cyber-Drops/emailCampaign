package clientemail.send;

import clientemail.exception.EmailFormatError;
import clientemail.utils.EmailConstructor;
import clientemail.utils.Log;
import clientemail.view.Config;
import clientemail.view.SenderUI;
import com.sun.mail.smtp.SMTPSenderFailedException;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sender {
    /**
     *  TODO Parsing di un file json per comporre: object email, corpo email ed allegati(allegato:percorso)
     *  TODO Nel file.conf (di configurazione) aggiungere le proprietà del email server (es. smtp.gmail.com.....)
     */
    private static  String passwordEmail;// Password google account associata all'app
    private static String usernameEmail;
    private static String emailString = "";// Stringa delle email lette dal file, sono tutte separate da virgola
    public static List<File> attachFile = new ArrayList<>();// Lista degli allegati
    public static String mailStatus;
    private static String FilePathBody;
    private static Multipart multipartMessagge = new MimeMultipart(); // istanzio il multipartMessagge

    public static String getEmailString() {
        return emailString;
    }

    public static String getPassword() {
        return passwordEmail;
    }

    /**
     * Assegna ad emailString gli indirizzi email contenuti nel fileMail attraverso l'invocazione del metodo della
     * classe EmailConstructor setEmailList().
     * @param fileMail tipo File, il file contenente gli indirizzi email.txt
     */
    public static void setEmailList(File fileMail){
        emailString = EmailConstructor.setEmailList(fileMail, emailString);
    }

    /**
     *Creo la sessione sulla base del server email usato e delle sue proprietà: indirizzo, porta, protocolli,
     * autenticazione.
     * @param from tipo String indirizzo email mittente
     * @return tipo Session la sessione aperta
     */

    public static Session createSession(String from){
        // Imposto i parametri del server email, poi in file.conf
        String host = "smtp.gmail.com";

        // Recupero le proprietà del sistema e creo l'oggetto Proprierties
        Properties properties = System.getProperties();

        // Aggiungo i parametri per il server email
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("email.smtp.tls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Istanziazione di un oggetto di tipo sessione passando username, password, le proprietà
        // del server mail e authenticator.
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            //override del metodo getPasswordAuthentication
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, passwordEmail);
            }
        });
        // setto il debug su true così da poter vedere eventuali errori
        session.setDebug(true);
        return session;
    }

    /**
     *Crea il messaggio nelle sue varie parti, il MimeMessage, il MimeBodyPart, imposta l'email header con i vari
     * campi.
     * @param session tipo Session istanza di Session
     * @param from indirizzo email mittente
     * @param to indirizzi email destinatari
     * @param cc indirizzi email copia conoscenza
     * @param objectEamil oggetto email
     * @param corpoMessaggio il messaggio dell'email
     * @return tipo MimeMessage
     */
    public static MimeMessage componiMessaggio(Session session, String from, String to, String cc, String objectEamil, String corpoMessaggio) {
        // Create a default MimeMessage object. Con i dettagli come from, to e oggetto.
        MimeMessage message = new MimeMessage(session);
        try {
            MimeBodyPart messaggeBody = new MimeBodyPart(); //Creo un oggetto MimeBodyPart con il corpo della mail
            message.setFrom(new InternetAddress(from)); //Setto il campo header con l'email From
            if (SenderUI.senderUIInstance.isChecked()) {    //Conferma ricezione Email
                message.setHeader("Disposition-Notification-TO:", "email per ricevuta");
            }
            if (!emailString.isEmpty()) {
                InternetAddress[] address = InternetAddress.parse(emailString);
                message.addRecipients(Message.RecipientType.TO, address);
            } else {
                if (to.contains(",")) { // Imposto il campo To dell'header.
                    InternetAddress[] address = InternetAddress.parse(to);
                    message.addRecipients(Message.RecipientType.TO, address);
                } else {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                }
            }
            if (!cc.isEmpty()) {    // Imposto il campo CC dell'header.
                InternetAddress[] ccAddress = InternetAddress.parse(cc);
                message.addRecipients(Message.RecipientType.CC, ccAddress);
            }
            message.setSubject(objectEamil);// Imposto il campo oggetto dell' header
            messaggeBody.setContent(corpoMessaggio,"text/html");// Imposto il corpo del messaggio ed il tipo
            //Includo tutti i Mimepart
            multipartMessagge.addBodyPart(messaggeBody);// aggiungo i vari Mimepart al parametro di classe multipartmessagge,
            // messageBody, allegati
            if (!attachFile.isEmpty()) {
                for (File file : attachFile) {
                    MimeBodyPart messaggeAttachment = new MimeBodyPart();// creo un Mimepart per ogni allegato
                    messaggeAttachment.setDataHandler(new DataHandler(new FileDataSource(file)));// imposto il gestore
                    //dei dati per ogni file da allegare
                    messaggeAttachment.setFileName(file.getName());
                    multipartMessagge.addBodyPart(messaggeAttachment);//aggiungo il Mimepart dell'allegato con i vari
                    // settaggi al MimeMultipart
                }
            }
            message.setContent(multipartMessagge);  //Aggiungo il MimeMultipart al MimeMessage
        } catch (AuthenticationFailedException authEx) {    // eccezione di accesso account email
            JOptionPane.showMessageDialog(null, "Password o Username Errati");
        } catch (AddressException addressEx) {  // indirizzi email in un formato non previsto
            JOptionPane.showMessageDialog(null, "Problemi con gli indirizzi email, si prega di verificare");
        } catch (MessagingException mex) {  // errori imprevisti
            mex.printStackTrace();
        }
        return message;
    }

    /**
     * Generalizza l'invio di una email richiamando i vari metodi di classe per la creazione della stessa. Gestisce
     * il processo di invio e di scorrimento della barra di caricamento.
     * @param from Indirizzo email mittente
     * @param to Indirizzo email destinatari
     * @param cc Indirizzi email copia conoscenza
     * @param objectEamil oggetto dell'email
     * @param corpoMessaggio messaggio email
     */
    public static void send(String from, String to, String cc, String objectEamil, String corpoMessaggio){
        try {
            EmailConstructor.checkEmail(from,to,cc,emailString);    //Verifico la forma degli indirizzi email
        } catch (EmailFormatError e) {
            return;
        }
        Session session = createSession(from);
        MimeMessage message = componiMessaggio(session, from, to, cc, objectEamil, corpoMessaggio);
        //MultiThreading, un processo gestisce la progressBar ed un processo l'invio della mail
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            try {
                //() è new Runnable in lambda
                executorService.execute(() -> SenderUI.progressBarFill(20, true));
                Transport.send(message);
                mailStatus = "sent";
                reset(); // Pulisco i campi
                executorService.shutdown();
            } catch (AuthenticationFailedException authFail){
                executorService.execute(() -> SenderUI.progressBarFill(20, false));// In caso di errore
                //la barra si imposta e blocca al 20%
                executorService.shutdown();
                JOptionPane.showMessageDialog(null, "Autenticazione Fallita, controlla email from o password");
            }catch (SMTPSenderFailedException senderFailedException){
                JOptionPane.showMessageDialog(null, senderFailedException.getMessage());
            }catch (MessagingException e) {
                executorService.shutdown();
                switch (e.getMessage()){
                    case "Your message exceeded Google's message size limits":
                        JOptionPane.showMessageDialog(null,e.getMessage().substring(10,60).concat(System.lineSeparator().concat("Usa Google Drive ed Invia il Link")));
                        break;
                    case "No recipient addresses":
                        SenderUI.progressBarFill(0,false);
                        break;
                    default:
                        if (e.getMessage().contains("552-5.7.0 This message was blocked because its content presents a potential\n" +
                                "552-5.7.0 security issue.")){
                            JOptionPane.showMessageDialog(null,"This message was blocked because its content presents a potential security issue.");
                        }
                        int answer = JOptionPane.showConfirmDialog(null, "Errore sconosciuto, invio Errore all'amministratore?\n" +
                                "SI acconsenti all' invio con la tua email un messaggio dell'errore all'amministratore con conseguente invio del tuo " +
                                "indirizzo email, NO non verrà inviato nulla.");
                        if (0 == answer ){
                            attachFile.clear();
                            cleanMultipartMessage();
                            Log.sendLogEmail(e.getStackTrace(), e.getMessage(), answer);
                        }
                        System.exit(1);
                }

            }
        });
    }
    /*
    public static void addTextEmail(String text){
        try {
            messaggeBody.setText(text);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
     */

    /**
     * Crea un MimeBodypart per ogni immagine che aggiungo al corpo dell'email, posizionandola in line, in base al
     * riferimento cid Html
     * @param cid tipo Stringa riferimento html, con la quale una certa immagine sarà posizionata nel testo.
     * @param o tipo Intero con il quale recupero in sequenza la path delle immagini da inserire
     */
    public static void creaBodyPart(String cid, int o){
        MimeBodyPart ImageBodyPart = new MimeBodyPart();
        //DataSource source = null;
        try {
            //System.out.println(SenderUI.senderUIInstance.getIconImgLine().get(o));
            setFilePathBody(SenderUI.senderUIInstance.getIconImgLine().get(o));
            DataSource source = new FileDataSource(getFilePathBody());  //Sorgente del file da gestire
            ImageBodyPart.setDataHandler(new DataHandler(source));  // Aggiungo un gestore del file
            //"<img src="+"cid:image".concat(String.valueOf((n+1))).concat(">");
            //"<image".concat(String.valueOf(n)).concat(">")
            //Imposto il campo Content-ID dell'header con la parte del cid che identifica l'immagine.
            ImageBodyPart.setHeader("Content-ID", "<".concat(cid.substring(cid.indexOf(":")+1)));
            //System.out.println("cidCrea"+cid.substring(cid.indexOf(":")+1));
            ImageBodyPart.setDisposition(MimeBodyPart.INLINE);  //Imposto la disposizione del Mimepart
            multipartMessagge.addBodyPart(ImageBodyPart);
            //messageBody.setContent(htmlText, "text/html");
            //multipartMessagge.addBodyPart(messageBody);
            //setFilePathBody(PathSelector.getFileSrc().getAbsolutePath());
            /*
            for (int i = 0; i < SenderUI.senderUIInstance.getIconImgLine().size(); i++){
                setFilePathBody(SenderUI.senderUIInstance.getIconImgLine().get(n-1));
                source = new FileDataSource(getFilePathBody());
                ImageBodyPart.setDataHandler(new DataHandler(source));
                //"<img src="+"cid:image".concat(String.valueOf((n+1))).concat(">");
                //"<image".concat(String.valueOf(n)).concat(">")
                ImageBodyPart.setHeader("Content-ID", "<".concat(cid.substring(cid.indexOf(":")+1)));
                System.out.println("cidCrea"+cid.substring(cid.indexOf(":")+1));
                ImageBodyPart.setDisposition(MimeBodyPart.INLINE);
                multipartMessagge.addBodyPart(ImageBodyPart);
            }
             */
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ripulisce il MimeMultipart creandone uno nuovo e riassegnandolo dopo l'invio della email
     */
    public static void cleanMultipartMessage(){
        multipartMessagge = new MimeMultipart();
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

    /**
     * @return tipo String la path dell'immagine da inserire nel corpo della email in line
     */
    public static String getFilePathBody() {
        return FilePathBody;
    }

    /**
     * Setto la path dell'immagine da inserire nel corpo della email in line
     * @param filePathBody tipo String path dell'immagine da inserire in line
     */
    public static void setFilePathBody(String filePathBody) {
        FilePathBody = filePathBody;
    }

    /**
     * Pulisco tutti campi se il metodo reset() della classe SenderUI.senderUIInstance ritorna true, cioè
     * l'utente ha questa volontà.
     */
    private static void reset(){
            if (SenderUI.senderUIInstance.reset()){
                emailString = "";
                //Pulisco il multipartMessage
                //cleanMultipartMessage();
            }
    }

    private static void setParseHtml(){

    }

}

