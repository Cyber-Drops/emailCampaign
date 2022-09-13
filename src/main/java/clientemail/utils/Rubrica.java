package clientemail.utils;

import clientemail.view.RubricaUI;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Rubrica {
    private List<Contatto> contattiRubrica = new ArrayList<>(); //Lista di oggetti Contatto
    private static Rubrica rubricaInstance; // instanza unica della Rubrica

    public Rubrica(){}

    public static void setRubricaInstance(){ //Instanziata nella classe PanelManage, metodo start()
        rubricaInstance = new Rubrica();
    }

    /**
     * Overloading del metodo setRubricaInstance, con il quale viene settata l'istanze dell'oggetto Rubrica,
     * passandogli l'istanza stessa caricata dal File Gson, così ad ogni caricamento sono sicuro di avere la
     * stessa rubrica che ho salvato.
     * @param rubrica Oggetto di tipo Rubrica caricato dal file Gson
     */
    public static void setRubricaInstance(Rubrica rubrica){ //Chiamato nel metodo caricaRubricaGson (questa classe)
        rubricaInstance = rubrica;
    }

    public static Rubrica getRubricaInstance() {
        return rubricaInstance;
    }

    public List<Contatto> getContattiRubrica() {
        return contattiRubrica;
    }

    /**
     * Salva un istanza di tipo Rubrica in formato Gson in un file chiamato rubrica, sollevando
     * un eccezione di tipo IOException in caso di errore.
     */

    public void salvaRubricaGson(){
        Gson rubricaGson = new Gson();
        String rubricaGosonSalvata = rubricaGson.toJson(getRubricaInstance());
        File saveFile = new File(System.getProperty("user.dir").concat(System.getProperty("file.separator").concat("rubrica")));
        try {
            FileWriter fileWriter = new FileWriter(saveFile);
            fileWriter.write(rubricaGosonSalvata);
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException ioException){
            JOptionPane.showMessageDialog(null,"Errore Salvataggio Rubrica");
        }
    }

    /**
     * Carica un oggetto di tipo Rubrica da un file in formato Gson chiamato rubrica e residente nella
     * directory di avvio del programma, con il quale setta l'istanza di Rubrica.java. Chiama il metodo
     * aggiornaRubricaUI.
     * Gestisce l'eccezione in caso di mancanza del file rubrica
     */
    public void caricaRubricaGson(){
        Gson rubricaGson = new Gson();
        File saveFile = new File(System.getProperty("user.dir").concat(System.getProperty("file.separator").concat("rubrica")));
        try {
            FileReader reader = new FileReader(saveFile);
            Rubrica rubrica = rubricaGson.fromJson(reader, Rubrica.class);//carico l'oggetto dal file Gson
            setRubricaInstance(rubrica);// setto l'istanza con l'oggetto rubrica caricato dal file Gson
            RubricaUI.getRubricaUIinstance().aggiornaRubricaUI();
            System.out.println("File Caricato");
        }catch (FileNotFoundException rubricaFileNotFoundEx){
            JOptionPane.showMessageDialog(null,"File Rubrica non esiste, sarà creato in seguito");
        }
    }

    @Override
    public String toString() {
        StringBuilder rubricaString = new StringBuilder();
        for (Contatto contatto : contattiRubrica) {
            rubricaString.append(contatto);
            rubricaString.append("\n");
        }
        return rubricaString.toString();
    }
}
