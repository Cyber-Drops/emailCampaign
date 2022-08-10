package clientemail.utils;

import clientemail.exception.EmailFormatError;
import clientemail.send.Sender;
import clientemail.view.SenderUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmailConstructor {
    public static String setEmailList(File fileMail, String emailString){
        /**
         * Metodo che scansiona il file txt contenente le email separate da virgola e
         * carica tutto in emailString, cioè una variabile di tipo stringa, quando scanner arriva
         * alla fine, viene aggiunta una virgola. Questo perchè volendo si potrà caricare un altro
         * file delle email che sarà concatenato al precedente.
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
        return emailString;
    }
    public static boolean checkEmailFormat (String stringEmailList){
        /**
         * Metodo per il controllo dei caratteri che compongono le stringhe delle email.
         * @param Oggetto di tipo stringa, può essere un email o più di una separate da virgola.
         * @return Oggetto di tipo booleano, true se le email contengono i caratteri permessi e
         *         sono formattate nel modo canonico, altrimenti false.
         */
        String[] listaEmail;
        if (stringEmailList.contains(",")) {
            listaEmail = stringEmailList.split(",");
        }else {
            listaEmail = new String[1];
            listaEmail[0] = stringEmailList;
        }
        for (String email : listaEmail) {
            if (email.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")) {
                return true;
            }
        }
        return false;
    }

    public static void checkEmail(String from, String to, String cc, String emailString) throws EmailFormatError {
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
    }
}
