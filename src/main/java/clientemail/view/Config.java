package clientemail.view;

import clientemail.exception.FileConfigException;
import clientemail.send.Sender;
import clientemail.utils.PathSelector;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Config {
    private File configFile;
    private String ultimaConfigSalvata;
    private List<String> nomiConfigFileList = new ArrayList<>();
    private List <File> configFileList = new ArrayList<>();
    private JPanel panel1;
    private JPanel configPanel;
    private JLabel titleLabel;
    private JPasswordField passwordEmail;
    private JTextField emailFrom;
    private JLabel configEmailLabel;
    private JLabel ConfigEmailPasswLabel;
    private JButton congiButton;
    private JTextField smtpServer;
    private JLabel portaServer;
    private JTextField smtpTLSport;
    private JButton indietroConfigUI;
    private static Config instanceConfig;
    private File pathWork;
    private String fromAddressConfigFile;
    private String passwConfigFile;
    private String serverSMTPconfigFile;
    private String servetTlsPortConfigFile;

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
            JOptionPane.showMessageDialog(null,"Seleziona la cartella con almeno un file di " +
                    "configurazione .config");
            pathWork = new File(PathSelector.getPathWork());
            List<File> listaFile = (Arrays.stream(pathWork.listFiles())).toList();
            nomiConfigFileList = new ArrayList<>(); //Creata nuova lista, per più file config
            for (File file : listaFile) {
                if (file.getName().endsWith(".conf")){
                    configFileList.add(file);
                    nomiConfigFileList.add(file.getName());
                }
            }
            if (configFileList.size() > 0){
                setConfigFile();
                return true;
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

    public void setPathWork(File path){
        this.pathWork = path;
    }
    public void setConfig() {
        /**
         * Metodo per la scrittura del file.conf
         * Viene creato un file config.conf nel quale si trascrive username,password
         */
        char[] passwdEmail = passwordEmail.getPassword();
        String email = emailFrom.getText();
        String serverSMTP = smtpServer.getText();
        String servetTlsPort = smtpTLSport.getText();
        StringBuilder passwordString  = new StringBuilder();
        for (char ch : passwdEmail) {
            passwordString.append(ch);
        }
        try {
            String nomeFileConfig = JOptionPane.showInputDialog("Inserisci il nome del file");
            FileWriter fileWriter = new FileWriter(pathWork.getAbsolutePath().concat(System.getProperty("file.separator").concat(nomeFileConfig).concat(".conf")));
            fileWriter.write(email.concat(",").concat(passwordString.toString()).concat(",").concat(serverSMTP).concat(",").concat(servetTlsPort));
            fileWriter.flush();
            fileWriter.close();
            if (nomiConfigFileList.isEmpty()){
                nomiConfigFileList.add(nomeFileConfig);
                this.configFile = new File(pathWork.getAbsolutePath().concat(System.getProperty("file.separator").concat(nomeFileConfig).concat(".conf")));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return;
    }
    public boolean parametriConfigIsEmpti(){
        if (emailFrom.getText().isEmpty() || passwordEmail.getPassword().length < 1 || smtpServer.getText().isEmpty() || smtpTLSport.getText().isEmpty()){
            return false;
        }
        return true;
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
            if (!parametriConfigIsEmpti()){
                JOptionPane.showMessageDialog(null, "Parametri Non Compilati Presenti");
                return;
            }
            setConfig();
            JOptionPane.showMessageDialog(null, "File di configurazione creato con successo");
            if (!CreaCaricaConfig.getCreaCaricaConfigInstance().isCaricaButtonPressed()){ // in caso sia stato
                resetCampiConfigUI();
                PanelManage.loadCreaCaricaConfigPanel(); //premuto button crea, ci riporta alla schermata iniziale
            }else {
                PanelManage.loadSendPanel(e);
            }
        });
        indietroConfigUI.addActionListener(e->{
            PanelManage.loadCreaCaricaConfigPanel();
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
        Object[] configurazioni = nomiConfigFileList.toArray();
        int indexConfigFile = JOptionPane.showOptionDialog(null, "Seleziona la configurazione da caricare!", "Carica Configurazione",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, configurazioni, configurazioni );
        this.configFile = new File(pathWork.getAbsolutePath().concat(System.getProperty("file.separator").concat(configurazioni[indexConfigFile].toString())));
    }
    public void settaParametriConfigurazione() {
        String parametri = "";
        try {
            Scanner scanner = new Scanner(configFile);
            while (scanner.hasNext()) {
                parametri = scanner.nextLine();
            }
            String[] parametriArray = parametri.split(",");
            fromAddressConfigFile = parametriArray[0];
            passwConfigFile = parametriArray[1];
            serverSMTPconfigFile = parametriArray[2];
            servetTlsPortConfigFile = parametriArray[3];
        }catch (FileNotFoundException FileE) {
        JOptionPane.showMessageDialog(null, "File .conf non presente");
        }
    }
    public void settaConfigurazione(){
            SenderUI.senderUIInstance.getFrom().setText(fromAddressConfigFile);// scrive nella SenderUI username, visualizzato dall'utente
            Sender.setUsername(fromAddressConfigFile);
            Sender.setPassword(passwConfigFile);// setta la password in Sender, per l'invio
            Sender.setHost(serverSMTPconfigFile);
            Sender.setPortTLS(servetTlsPortConfigFile);
            SenderUI.senderUIInstance.getLoadConfigButton().setEnabled(false);

    }
    public String getFromAddressConfigFile(){
        return fromAddressConfigFile;
    }
    public void resetCampiConfigUI(){
        emailFrom.setText("");
        passwordEmail.setText("");
        smtpServer.setText("");
        smtpTLSport.setText("");
    }
    public String getPasswConfigFile(){
        return passwConfigFile;
    }
}
