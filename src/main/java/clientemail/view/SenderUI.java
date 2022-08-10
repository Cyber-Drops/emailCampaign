package clientemail.view;

import clientemail.send.Sender;
import clientemail.utils.PathSelector;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class SenderUI {
    private JPanel mainPanel; //Pannello principale
    private JTextField from;
    private JTextField to;
    private JTextField cc;
    private JTextField objectEmail;
    private JTextArea corpoMessaggio;
    private  JButton sendButton;
    private JPanel panelSender;
    private JButton emailFileButton;
    private JLabel labelIcon;
    private JLabel labelIconTesta;
    private JButton allegaButton;
    private JLabel labelAllegati;
    private JTextPane attachText;
    private JButton loadConfigButton;
    private JProgressBar sendProgressBar;
    private JLabel barLabel;
    public static SenderUI senderUIInstance;

    public JPanel getPanelSender() {
        return panelSender;
    }

    public SenderUI() {} //Blocco il costruttore di default
    public  void sendEmail(SenderUI senderUI, Config configUI){
        /**
         * Setto il parametro statico di istanza, così da poter richiamare i metodi d'istanza, senza creare nuove
         * istanze.
         * Creo l'URL di riferimento per le immagini, così da poterle richiamare anche in esecuzione del file .jar,
         * le immagini si trovano in cartella di default del proggetto resources->image, il percorso è S.O. dipendente.
         * chiamo il metodo initComponent() per inizializzare i componenti dell'interfaccia grafica
         */
        senderUIInstance = senderUI;
        if (System.getProperty("os.name").contains("Windows")){
           URL logoPng = (getClass().getClassLoader().getResource("image/logo100_100.png"));
           URL testaJpg = getClass().getClassLoader().getResource("image/avatarCyberDrops_Testa_150.jpg");
           labelIcon.setIcon(new ImageIcon(logoPng));
           labelIconTesta.setIcon(new ImageIcon(testaJpg));
        }else {
             URL logoPng = (getClass().getClassLoader().getResource("image".concat(System.getProperty("file.separator")).concat("logo100_100.png")));
             URL testaJpg = getClass().getClassLoader().getResource("image".concat(System.getProperty("file.separator")).concat("avatarCyberDrops_Testa_150.jpg"));
            labelIcon.setIcon(new ImageIcon(logoPng));
            labelIconTesta.setIcon(new ImageIcon(testaJpg));
        }

        initComponent(configUI);
    }
    private  void initComponent(Config configUI){
        /**
         * Gestito evento pressione sul:
         * 1) sendButton, il quale chiama il metodo statico send della classe Sender, passandogli le Stringhe prelevate
         * dai vari campi della UI
         * 2) emailFileButton, il quale richiama il metodo getFileSource della classe PathSelector, con il quale
         * seleziono il file.txt contenente le email destinatari seperate dalla virgola. chiama poi il metodo statico
         * della classe Sender setEmailList() passandogli il file, infine disabilita il campo UI to.
         * 3)allegaButton, crea uno StringBuilder dei nomi degli allegati, per poterli visualizzare sull' UI attachText.
         * Permette la selezione del file da allegare tramite PathSelector.getFileSrc(), se la lista publicca della
         * classe Sender attachFile non contiene quel file, lo aggiunge
         */
        // TODO Creare una barra di caricamento, che visualizzi il tempo per l'invio delle email.
        sendButton.addActionListener(e -> {
            Sender.send(from.getText(), to.getText(), cc.getText(), objectEmail.getText(), corpoMessaggio.getText());
            Sender.attachFile.clear();
        });
        emailFileButton.addActionListener(e -> {
            try {
                File file = PathSelector.getFileSrc();
                Sender.setEmailList(file);
                setToFocusable(false);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        allegaButton.addActionListener(e-> {
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
        });
        loadConfigButton.addActionListener(e->{
            settaConfigurazione(configUI);
        });
    }

    private void settaConfigurazione(Config configUI){
        configUI.setConfig();
        String parametri = "";
        try {
            System.out.println(configUI.getConfig());
            Scanner scanner = new Scanner(configUI.getConfig());
            while (scanner.hasNext()) {
                parametri = scanner.nextLine();
            }
            String[] parametriArray = parametri.split(",");
            from.setText(parametriArray[0]);
            Sender.setPassword(parametriArray[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean reset(){
        /**
         * Metodo che si occupa della pulizia dei campi UI dopo l'inoltro delle email
         */
        int answer = JOptionPane.showConfirmDialog(null,"Voui ripulire i campi ed il corpo della email?");
        if (answer == 0) {
            from.setText("");
            to.setText("");
            cc.setText("");
            objectEmail.setText("");
            corpoMessaggio.setText("");
            attachText.setText("");
            sendProgressBar.setValue(0);
            SenderUI.senderUIInstance.setToFocusable(true);
            return true;
        }
        return false;
    }
    public void setToFocusable(boolean bool){
        to.setEnabled(bool);
    }
    public void setSendProgressBar(boolean bool){
        sendProgressBar.setVisible(bool);
    }
    public static void progressBarFill(int percenuale){
        int i = percenuale;
        while (i < 100){
            i += i;
            if (i > 100){
                i = 100;
            }
            try {
                senderUIInstance.setFillBar(i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void setFillBar(int fill){
        sendProgressBar.setValue(fill);
    }
    public void setToText(String emailToList){
        to.setText(emailToList);
    }

}
