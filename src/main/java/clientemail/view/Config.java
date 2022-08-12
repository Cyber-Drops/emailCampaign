package clientemail.view;

import clientemail.utils.PathSelector;
import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Config {
    private File configFile;
    private JPanel panel1;
    private JPanel configPanel;
    private JLabel titleLabel;
    private JPasswordField passwordEmail;
    private JTextField emailFrom;
    private JLabel configEmailLabel;
    private JLabel ConfigEmailPasswLabel;
    private JButton congiButton;
    private static Config instanceConfig;

    {
        initComponent();
    }
    public Config() {}

    public Boolean isConfig(){
        try {
            JOptionPane.showMessageDialog(null,"Carica o crea file di configurazione.");
            File pathWork = new File(PathSelector.getPathWork());
            List<File> listaFile = (Arrays.stream(pathWork.listFiles())).toList();
            for (File file : listaFile) {
                if (file.getName().endsWith(".conf")){
                    //setConfig();
                    setConfigFile();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void setInstanceConfig(){
        instanceConfig = new Config();
    }

    public static Config getInstanceConfig(){
        return instanceConfig;
    }

    public void setConfig() {
        char[] passwdEmail = passwordEmail.getPassword();
        String email = emailFrom.getText();
        StringBuilder passwordString  = new StringBuilder();
        for (char ch : passwdEmail) {
            passwordString.append(ch);
        }
        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir").concat(System.getProperty("file.separator").concat("config.conf")),true);
            fileWriter.write(email.concat(",").concat(passwordString.toString()));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return;
    }

    public File getConfig() {
        return configFile;
    }

    private  void initComponent(){
        congiButton.addActionListener(e -> {
            setConfig();
            JOptionPane.showMessageDialog(null, "File di configurazione creato con successo");
            PanelManage.loadSendPanel(e);
        });
    }

    public JPanel getConfigPanel() {
        return configPanel;
    }
    public void setConfigFile(){
        this.configFile = new File(System.getProperty("user.dir").concat(System.getProperty("file.separator").concat("config.conf")));
    }


}
