package clientemail.utils;

import clientemail.view.RubricaUI;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Rubrica {
    private List<Contatto> contattiRubrica = new ArrayList<>();
    private static Rubrica rubricaInstance;

    public Rubrica(){}

    public static void setRubricaInstance(){ //Instanziata nella classe PanelManage, metodo start()
        rubricaInstance = new Rubrica();
    }
    public static void setRubricaInstance(Rubrica rubrica){
        rubricaInstance = rubrica;
    }

    public static Rubrica getRubricaInstance() {
        return rubricaInstance;
    }

    public List<Contatto> getContattiRubrica() {
        return contattiRubrica;
    }

    public void aggiungiContatto(Contatto contatto) {
        this.contattiRubrica.add(contatto);
    }

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
    public void caricaRubricaGson(){
        Gson rubricaGson = new Gson();
        File saveFile = new File(System.getProperty("user.dir").concat(System.getProperty("file.separator").concat("rubrica")));
        try {
            FileReader reader = new FileReader(saveFile);
            Rubrica rubrica = rubricaGson.fromJson(reader, Rubrica.class);
            setRubricaInstance(rubrica);
            RubricaUI.getRubricaUIinstance().aggiornaRubricaUI();
        }catch (FileNotFoundException rubricaFileNotFoundEx){
            JOptionPane.showMessageDialog(null,"File Rubrica non esiste");
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
