package clientemail.view;

import clientemail.exception.FileConfigException;
import clientemail.send.Sender;
import clientemail.utils.ManagerAllegati;
import clientemail.utils.PathSelector;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    private JButton helpButton;
    private JLabel linkCDlabel;
    private JButton rmAllegatiButton;
    private JCheckBox ricConfermaCheckBox;
    private JButton salvaCaricaMsgButton;
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
        initComponent();
    }
    private  void initComponent(){
        Config configUI = Config.getInstanceConfig();
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
        sendButton.addActionListener(e -> {
            Sender.send(from.getText(), to.getText(), cc.getText(), objectEmail.getText(), corpoMessaggio.getText());
        });
        emailFileButton.addActionListener(e -> {
            try {
                File file = PathSelector.getFileSrc();
                Sender.setEmailList(file);
                //setToFocusable(false);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        allegaButton.addActionListener(e-> {
            ManagerAllegati.aggiungiAllegato(attachText);
/*
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

 */
        });
        rmAllegatiButton.addActionListener(e->{
            ManagerAllegati.rimuoviAllegato(attachText);
        });
        loadConfigButton.addActionListener(e->{
            settaConfigurazione(configUI);
        });

        helpButton.addActionListener(e->{
            PanelManage.loadHelpPanel(e);
            HelpUI.getHelpInstace().componiHelpmenu();
        });
        linkCDlabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.cyber-drops.com"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                linkCDlabel.setText("<html><a href=''>www.cyber-drops.com</a></html>www.cyber-drops.com");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                linkCDlabel.setText("www.cyber-drops.com");
            }
        });

    }

    private void settaConfigurazione(Config configUI){
        /**
         * Esegue il setting dell'applicazione tramite i dati letti nel file config.conf
         * @paranm Oggetto di tipo Config, istanza
         */
        String parametri = "";
        try {
            System.out.println("ok"+configUI.getConfig().isFile());
            Scanner scanner = new Scanner(configUI.getConfig());
            while (scanner.hasNext()) {
                parametri = scanner.nextLine();
            }
            String[] parametriArray = parametri.split(",");
            from.setText(parametriArray[0]);// scrive nella SenderUI username, visualizzato dall'utente
            Sender.setUsername(parametriArray[0]);
            Sender.setPassword(parametriArray[1]);// setta la password in Sender, per l'invio
            loadConfigButton.setEnabled(false);
        } catch (FileNotFoundException FileE) {
            JOptionPane.showMessageDialog(null, "File config.conf non presente");
        } catch (FileConfigException FileConfE){
            return;
        }
    }
    public boolean reset(){
        /**
         * Metodo che si occupa della pulizia dei campi UI dopo l'inoltro delle email
         */
        int answer = JOptionPane.showConfirmDialog(null,"Email Inviata!\nRipulire i campi ed il corpo della email?");
        if (answer == 0) {
            from.setText("");
            to.setText("");
            cc.setText("");
            objectEmail.setText("");
            corpoMessaggio.setText("");
            attachText.setText("");
            Sender.attachFile.clear();
            sendProgressBar.setValue(0);
            SenderUI.senderUIInstance.setToFocusable(true);
            SenderUI.senderUIInstance.loadConfigButton.setEnabled(true);
            setFillBar(0);
            return true;
        }
        return false;
    }
    public void setToFocusable(boolean bool){
        to.setEnabled(bool);
    }
    public static void progressBarFill(int percenuale, boolean inProgress) {
        if (inProgress) {
            int i = percenuale;
            while (i < 100) {
                i += 10;
                if (i > 100) {
                    i = 100;
                }
                try {
                    senderUIInstance.setFillBar(i);
                    Thread.sleep(1500);
                    if (i == 100){
                        senderUIInstance.setFillBar(0);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }else {
            senderUIInstance.setFillBar(percenuale);
        }
    }
    public void setFillBar(int fill){
        sendProgressBar.setValue(fill);
    }
    public void setToText(String emailToList){
        to.setText(emailToList);
    }

    public JTextField getFrom() {
        return from;
    }

    public JTextPane getAttachText() {
        return attachText;
    }
}
