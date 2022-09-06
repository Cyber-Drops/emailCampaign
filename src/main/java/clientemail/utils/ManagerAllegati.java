package clientemail.utils;

import clientemail.send.Sender;
import clientemail.view.SenderUI;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerAllegati {
    private static List<String> nomiAllegati = new ArrayList<>();// Contiene tutti gli allegati, usati nel
    // metodo rimuoviAllegato sia come opzioni sel JOption che per recuperare l'indice dell'allegato da rimuovere.
    /**
     *  Crea uno StringBuilder con i nomi degli allegati, per poterli visualizzare sull' UI
     *  attachText.
     *  Permette la selezione del file da allegare tramite PathSelector.getFileSrc(), se la lista publica della
     *  Classe Sender attachFile non contiene quel file, lo aggiunge.
     * @param attachText usato per aggiungere i nomi dei file allegati.
     */
    public static void aggiungiAllegato(JTextPane attachText){
        StringBuilder stringBuilder = new StringBuilder();// DA modificare, quanti oggetti inutili istanzio??
<<<<<<< HEAD
=======
        System.out.println("!!!!!!!!!"+stringBuilder);
>>>>>>> ced708b (Aggiorno commit locale per fare pull da remoto)
        System.out.println(Arrays.toString(Sender.attachFile.toArray()));
        try {
            File file = PathSelector.getFileSrc();
            if (!Sender.attachFile.contains(file)) {
                Sender.attachFile.add(file);
            }
            for (File file_name : Sender.attachFile) {
                stringBuilder.append(file_name.getName().concat("\n"));
                attachText.setText(stringBuilder.toString());
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Crea uno stringBuilder nel quale memorizza i nomi degli allegati, con il quale verrà popolato il campo visivo
     * contenente tutti i nomi degli allegati. Usato per ricaricare gli alleagati da una email salvata.
     * Crea un array di stringhe dal parametro listaAllegati splittato da ",". Tramite il quale ricrea gli oggetti File
     * e ricarica gli allegati.
     * @param listaAllegati tipo Lista, contiene le path assolute degli allegati, salvate in attachFile del Gson.
     */
    public static void caricaAllegati(String listaAllegati) {
        StringBuilder stringBuilder = new StringBuilder();
        listaAllegati = listaAllegati.replace("[","");
        listaAllegati = listaAllegati.replace("]","");
        String[] allegatiArray = listaAllegati.split(",");
<<<<<<< HEAD
=======
        System.out.println("-----------"+stringBuilder);
>>>>>>> ced708b (Aggiorno commit locale per fare pull da remoto)
        System.out.println(Arrays.toString(allegatiArray));
        System.out.println(Arrays.toString(Sender.attachFile.toArray()));
        try {
            for (String f : allegatiArray) {
                f = f.strip();
                File file = new File(f);
                if (!Sender.attachFile.contains(f)) {
                    Sender.attachFile.add(file);
                }
                System.out.println("<<<<"+Arrays.toString(Sender.attachFile.toArray()));
                for (File file_name : Sender.attachFile) {
                    if (!stringBuilder.toString().contains(file_name.getName())){
                        stringBuilder.append(file_name.getName().concat("\n"));
                    }
                }
            }
<<<<<<< HEAD
=======
            System.out.println("-----------"+stringBuilder);
>>>>>>> ced708b (Aggiorno commit locale per fare pull da remoto)
            SenderUI.senderUIInstance.getAttachText().setText(stringBuilder.toString());
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * Crea uno showOptionDialog tramite il quale l'utente potra selezionare quale allegato vuole rimuovere o annulla.
     * @param attachText tipo: JTextPane, usato per recuperare i nomi degli allegati e per il suo aggiornamento.
     */
    public static void rimuoviAllegato(JTextPane attachText){
        if (!nomiAllegati.contains("Annulla")) {// Assicura che l'opzione annulla sia una sola
            nomiAllegati.add("Annulla");
        }
        for (File nome : Sender.attachFile) {// ciclo attachFile ottenendo i file degli allegati
            if (!nomiAllegati.contains(nome.getName())) {// recupero il nome dell'allegato
                nomiAllegati.add(nome.getName());// Contine nomi allegati
            }
        }
        int posizioneAllegato = JOptionPane.showOptionDialog(null,"Seleziona Allegato da rimuovere", "Rimuovi Allegato",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, nomiAllegati.toArray(), nomiAllegati.toArray()[0]);
        if (posizioneAllegato != 0) {//posizione diversa da annulla
            String rmNomeAllegato = nomiAllegati.get(posizioneAllegato); //salvo il nome dell'allegato da rimuovere
            nomiAllegati.remove(nomiAllegati.get(nomiAllegati.indexOf(rmNomeAllegato)));// rimuovo l'allegato dalla lista
            Sender.attachFile.remove(posizioneAllegato-1);// rimuovo fisicamente l'allegato il File
            JOptionPane.showMessageDialog(null, rmNomeAllegato );
            StringBuilder text = new StringBuilder();
            for ( String nomeA : nomiAllegati) {
                if (!nomeA.equalsIgnoreCase("annulla")) {
                    text.append(nomeA.concat(System.lineSeparator()));//ricostruisco la lista degli allegati da visualizzare
                }
            }
            attachText.setText(text.toString());
        }
    }
}

