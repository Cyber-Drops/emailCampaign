package clientemail.utils;

import clientemail.exception.EmailFormatError;
import clientemail.send.Sender;
import clientemail.view.SenderUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmailConstructor {
    /**
     * Metodo che scansiona il file txt contenente le email separate da virgola e
     * carica tutto in emailString, cioè una variabile di tipo stringa, quando scanner arriva
     * alla fine, viene aggiunta una virgola. Questo perchè volendo si potrà caricare un altro
     * file delle email che sarà concatenato al precedente.
     * @param fileMail tipo File file.txt contenente gli indirizzi email destinatari
     * @param emailString tipo String vuoto o popolato da indirizzi email dei destinatari
     */
    public static String setEmailList(File fileMail, String emailString){
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
    /**
     * Metodo per il controllo dei caratteri che compongono le stringhe delle email.
     * @param stringEmailList di tipo stringa, può essere un email o più di una separate da virgola.
     * @return Oggetto di tipo booleano, true se le email contengono i caratteri permessi e
     *         sono formattate nel modo canonico, altrimenti false.
     */
    public static boolean checkEmailFormat (String stringEmailList) {
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

    /**
     * Verifica la correttezza dei campi from, to, cc, e gli indirizzi presenti nella stringa emailString.
     * @param from tipo String indirizzo email mittente
     * @param to tipo String indirizzi email destinatari
     * @param cc tipo String indirizzi email copia conoscenza
     * @param emailString tipo String popolato con gli indirizzi dei destinatari
     * @throws EmailFormatError tipo Exception lanciata in caso il formato dei campi non sia corretto, o i capmpi from o
     * to  siano vuoti.
     */
    public static void checkEmail(String from, String to, String cc, String emailString) throws EmailFormatError {
        //TODO Migliorare!!!!!!!!!!!!!!!
        System.out.println(from);
        if (from.isEmpty() || to.isEmpty()){
            throw new EmailFormatError();
        }
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
