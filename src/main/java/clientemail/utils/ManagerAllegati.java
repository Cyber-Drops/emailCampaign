package clientemail.utils;

import clientemail.send.Sender;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ManagerAllegati {
    private static List<String> nomiAllegati = new ArrayList<>();
    public static void aggiungiAllegato(JTextPane attachText){
        StringBuilder stringBuilder = new StringBuilder();// DA modificare, quanti oggetti inutili istanzio??
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

    public static void rimuoviAllegato(JTextPane attachText){
        if (!nomiAllegati.contains("Annulla")) {
            nomiAllegati.add("Annulla");
        }
        for (File nome : Sender.attachFile) {
            if (!nomiAllegati.contains(nome.getName())) {
                nomiAllegati.add(nome.getName());// Contine oggetti File
            }
        }
        int posizioneAllegato = JOptionPane.showOptionDialog(null,"Seleziona Allegato da rimuovere", "Rimuovi Allegato",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, nomiAllegati.toArray(), nomiAllegati.toArray()[0]);
        if (posizioneAllegato != 0) {
            String rmNomeAllegato = nomiAllegati.get(posizioneAllegato); //salvo il nome dell'allegato da rimuovere
            nomiAllegati.remove(nomiAllegati.get(nomiAllegati.indexOf(rmNomeAllegato)));
            Sender.attachFile.remove(posizioneAllegato-1);
            JOptionPane.showMessageDialog(null, rmNomeAllegato );
            StringBuilder text = new StringBuilder();
            for ( String nomeA : nomiAllegati) {
                if (!nomeA.equalsIgnoreCase("annulla")) {
                    text.append(nomeA.concat(System.lineSeparator()));
                }
            }
            attachText.setText(text.toString());
        }
    }
}

