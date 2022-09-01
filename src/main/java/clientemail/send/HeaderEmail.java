package clientemail.send;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class HeaderEmail {
    private static MimeMessage message;
    private static String object;
    private static String corpoMessaggio;


    public static void setMessage(MimeMessage mimeMessage) {
        message = mimeMessage;
    }

    public static void setObject(String objectString) {
        object = objectString;
    }

    public static void setCorpoMessaggio(String corpoMessaggioString) {
        corpoMessaggio = corpoMessaggioString;
    }

    public static void setHeaderEmail(){
        try {
            message.setSubject(object);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
