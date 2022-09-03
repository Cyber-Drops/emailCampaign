package clientemail.utils;

import javax.swing.*;
import java.io.File;

public class PathSelector {
    /**
     *Classe per la gestione di file e directory

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

    public static String getPath() throws Exception {
        JFileChooser jFchooser = new JFileChooser();
        jFchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFchooser.showOpenDialog(null);
        return jFchooser.getSelectedFile().getAbsolutePath();
    }
    public static String getPathWork() {
        JFileChooser jFchooser = new JFileChooser(System.getProperty("user.dir"));
        jFchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFchooser.showOpenDialog(null);
        System.out.println(jFchooser.getSelectedFile().getAbsolutePath());
        return jFchooser.getSelectedFile().getAbsolutePath();
    }

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
