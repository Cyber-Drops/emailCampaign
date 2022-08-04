package clientemail.utils;

import javax.swing.*;
import java.io.File;

public class PathSelector {
    public static File getFileSrc() throws Exception {
        JFileChooser jFchooser = new JFileChooser();
        jFchooser.showOpenDialog(null);
        File file = jFchooser.getSelectedFile();
        if (file == null){
            throw new Exception();
        }
        return file;
    }
    /*
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
     */
}
