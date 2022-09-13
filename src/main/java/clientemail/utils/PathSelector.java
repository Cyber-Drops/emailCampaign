package clientemail.utils;

import javax.swing.*;
import java.io.File;

/**
 * Classe per la gestione di file e directory
 */
public class PathSelector {
    /**
     * Apre un JFileChooser per la selezione di un file, lancia un eccezione in caso di file null
     * @return oggetto di tipo File
     */
    public static File getFileSrc() throws Exception {
        JFileChooser jFchooser = new JFileChooser(System.getProperty("user.dir"));
        jFchooser.showOpenDialog(null);
        File file = jFchooser.getSelectedFile();
        if (file == null){
            throw new Exception();
        }
        return file;
    }

    public static String getPathDst() throws Exception{
        JFileChooser jFchooser = new JFileChooser();
        jFchooser.showOpenDialog(null);
        return jFchooser.getSelectedFile().getAbsolutePath();
    }

    /**
     * Metodo Statico.
     * Apre un fileChooser con radice di default con il quale è possibile selezionare solo una directory.
     * @return Oggetto di tipo String, la path assoluta
     * @throws Exception ...?
     */
    public static String getPath() throws Exception {
        JFileChooser jFchooser = new JFileChooser();
        jFchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFchooser.showOpenDialog(null);
        return jFchooser.getSelectedFile().getAbsolutePath();
    }

    /**
     * Metodo statico
     * Apre un fileChooser con radice nella directory dove risiede il tool con il quale è possibile
     * selezionare solo una directory.
     * @return Oggetto di tipo String, la path assoluta
     */
    public static String getPathWork() {
        JFileChooser jFchooser = new JFileChooser(System.getProperty("user.dir"));
        jFchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFchooser.showOpenDialog(null);
        File pathFile = jFchooser.getSelectedFile();
        if (pathFile != null){
            return pathFile.getAbsolutePath();
        }else {
            return "annulla";
        }
    }

    /**
     * Metodo statico
     * Apre un fileChooser con radice nella directory dove risiede il tool con il quale è possibile selezionare
     * la destinazione del file di salvataggio, aprendo un nuovo fileChooser si approva l'operazione.
     * @return Oggetto di tipo File
     */
    public static File saveFile(){
        JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.dir"));
        int answer = jFileChooser.showSaveDialog(null);
        if (answer == JFileChooser.APPROVE_OPTION){
            File fileSave = jFileChooser.getSelectedFile();
            return fileSave;
        }
        return null;
    }

}
