package clientemail.view;

import clientemail.exception.FileConfigException;
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
        initComponent();//Inizializzo i componenti della UI tramite un inizializzatore di instanza
    }
    public Config() {} //Blocco il costruttore di default

    public Boolean isConfig(){
        /**
         * Metodo che verifica la presenza del file config.conf, se il file è
         * presente chiama il metodo setConfigFile
         * @retutn true se esiste il file config.conf, flase altrimenti
         */
            JOptionPane.showMessageDialog(null,"Carica o crea file di configurazione.");
            File pathWork = new File(PathSelector.getPathWork());
            List<File> listaFile = (Arrays.stream(pathWork.listFiles())).toList();
            for (File file : listaFile) {
                if (file.getName().endsWith(".conf")){
                    setConfigFile();
                    return true;
                }
            }
            return false;

    }
    public static void setInstanceConfig(){
        /**
         * Metodo che crea un instanza dell'oggetto Config e lo assegna all'attributo di
         * instanza instanceConfig, cosi da poter accedere anche ai metodi di instanza tramite
         * metodo statico
         */
        instanceConfig = new Config();
    }

    public static Config getInstanceConfig(){
        /**
         * Metodo di accesso all' instanza dell'oggetto Config cosi da poter accedere
         * anche ai metodi di instanza tramite metodo statico
         * @return instanceConfig Oggetto di tipo Config, Istanza di Config
         */
        return instanceConfig;
    }

    public void setConfig() {
        /**
         * Metodo per la scrittura del file.conf
         * Viene creato un file config.conf nel quale si trascrive username,password
         */
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

    public File getConfig() throws FileConfigException {
        /**
         * Metodo per accedere al file contenente i settaggi utili
         * @return configFile Oggetto di tipo File, il file di configurazione
         */
        return configFile;
    }

    private  void initComponent(){
        /**
         * Metodo per inizializzare i componenti della UI
         */
        congiButton.addActionListener(e -> {
            setConfig();
            JOptionPane.showMessageDialog(null, "File di configurazione creato con successo");
            PanelManage.loadSendPanel(e);
        });
    }

    public JPanel getConfigPanel() {
        /**
         * Metodo per accedere al JPanel della finestra di configurazione, utile per caricare la finestra
         * @return configPanel Oggetto di tipo JPanel, la finestra di configurazione
         */
        return configPanel;
    }
    public void setConfigFile(){
        /**
         * Metodo per il setting dell'attributo configFile, oggetto di tipo File (config.conf)
         */
        this.configFile = new File(System.getProperty("user.dir").concat(System.getProperty("file.separator").concat("config.conf")));
    }


}
