package clientemail.view;

import clientemail.send.Sender;
import clientemail.utils.ManagerAllegati;
import clientemail.utils.MsgManager;
import clientemail.utils.PathSelector;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Finestra UI per impostare le email, con mittente, destinatario, allegati, immagini ecc..
 */
public class SenderUI {
    private JPanel mainPanel; //Pannello principale
    private JTextField from;
    private JTextField to;
    private JTextField cc;
    private JTextField objectEmail;
    private JTextPane corpoMessaggio;
    private String htmlTextMail;
    private List<String> iconImgLine = new ArrayList<>();
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
    private JButton imgToTextButton;
    private JButton caricaDaRubricaButton;
    private JButton indietroSenderUI;
    private Path path;
    private StringBuilder stringBuilder = new StringBuilder();
    private int n = 0;
    private int posizioneCursore;
    private String cid;
    private List<String> listaCid = new ArrayList<>();
    private String testoImgPlaceHolder = "";
    public static SenderUI senderUIInstance;

    public JPanel getPanelSender() {
        return panelSender;// Permette il caricamento della finestra
    }
    //PlaceHolder per Immagine ◘
    //PlaceHolder per \n ◙
    public SenderUI() {} //Blocco il costruttore di default
    /**
     * Setto il parametro statico di istanza, così da poter richiamare i metodi d'istanza, senza crearne di
     * nuove.
     * Creo l'URL di riferimento per le immagini, così da poterle richiamare anche in esecuzione del file .jar,
     * le immagini si trovano in cartella di default del proggetto resources->image, il percorso è S.O. dipendente.
     * chiamo il metodo initComponent() per inizializzare i componenti dell'interfaccia grafica
     * @param senderUI istanza della Classe SenderUI, creata nella classe PanelManager.
     */
    public  void sendEmail(SenderUI senderUI){// Chiamato dal PanelManage startUI()
        senderUIInstance = senderUI;// Setto l'istanza SenderUI
        //Gestisco le immagini della finestra
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
    /**
     * @return testoImgPlaceHolder è una stringa con i riferimenti per posizionare le ImageIcon nel JTextPane
     */
    public String getTestoImgPlaceHolder() {
        return testoImgPlaceHolder;
    }
    /**
     * setta testoImgPlaceHolder è una stringa con i riferimenti per posizionare le ImageIcon nel JTextPane
     */
    public void setTestoImgPlaceHolder(String testoImgPlaceHolder) {
        this.testoImgPlaceHolder = testoImgPlaceHolder;
    }
    /**
     * Gestiti eventi pressione bottoni.
     * 1) sendButton, il quale chiama il metodo statico send della classe Sender, passandogli le
     * stringhe prelevate dai vari campi della UI ed il parsingInHtml.
     * 2) emailFileButton, il quale richiama il metodo getFileSource della classe PathSelector, con il quale
     * seleziono il file.txt contenente le email destinatari seperate dalla virgola. Chiama poi
     * il metodo statico della classe Sender setEmailList() passandogli il file.
     * 3) salvaCaricaMsgButton........
     * 4) allegaButton richiama il metodo della classe ManagerAllegati aggiungi allegato(), passandogli JTextPane.
     * 5) rmAllegatiButton richiama il metodo della classe ManagerAllegati rimuoviAllegati(), passando JTextPane.
     * 6) loadConfigButton Imposta i parametri di configurazione tramite i file.conf, chiamando i metodi setteParametri
     *      DiConfigurazione() e settaConfigurazione() della classe Config
     * 7) helpButton Mostra la sezione dell'help con riferimenti a guide online
     * 8) linkCDlabel Crea una label come Link, con la quale sarà possibile raggiungere le guide online
     * 9) corpoMessaggio Tramite un KeyListner legge i tasti premuti dalla tastiera gestendo il ritorno a capo con il
     *      placeHoder ◙, gestisce il backSpace per cancellare ed aggiornare la posizione del cursore
     * 10) imgToTextButton Gestisce l'inserimento di un immagine nel corpo della mail tramite un placeHolder ◘ ed il
     *      campo cid dell'html così da aggiungere l'immagine nella propria posizine nel testo.
     */
    private  void initComponent(){
        //Config configUI = Config.getInstanceConfig();
        corpoMessaggio.addFocusListener(new FocusAdapter() {//Verificare la sua assenza
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }
        });
        /*
        try {
            path  = Files.createTempFile("html", ".file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         */
        //attachText.insertIcon(new ImageIcon("/home/simone/Immagini/Schermate/sch.png") );
        sendButton.addActionListener(e -> {
            //parseHtmlFile();
            //htmlTextMail = parseHtml();
            Sender.send(from.getText(), to.getText(), cc.getText(), objectEmail.getText(), parseHtml());
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
        caricaDaRubricaButton.addActionListener(e->{
            RubricaUI.getRubricaUIinstance().setEnableCaricaButton();
            PanelManage.loadRubricaPanel(e);
        });
        salvaCaricaMsgButton.addActionListener(e->{
            String[] option = {"Annulla", "Salva", "Carica"};
            MsgManager msgManagerInstance = new MsgManager();
            int answer  = JOptionPane.showOptionDialog(null,"Salva o carica email completa","Carica/Salva Email",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,option, option[0]);
            switch (answer) { // enanched switch
                case 1 -> msgManagerInstance.salvaMsg();//Salva
                case 2 -> {//Carica
                    //Sender.attachFile.clear();
                    System.out.println(Arrays.toString(Sender.attachFile.toArray()));
                    attachText.setText("");
                    Sender.attachFile.clear();
                    corpoMessaggio.setText("");
                    SenderUI.senderUIInstance.to.setText("");
                    SenderUI.senderUIInstance.cc.setText("");
                    SenderUI.senderUIInstance.objectEmail.setText("");
                    msgManagerInstance.caricaMsg();
                    setStringBilderToText();
                    testoImgPlaceHolder = stringBuilder.toString();
                    caricaImgIcon();
                }
            }
        });
        allegaButton.addActionListener(e-> {
            ManagerAllegati.aggiungiAllegato(attachText);
        });
        rmAllegatiButton.addActionListener(e->{
            ManagerAllegati.rimuoviAllegato(attachText);
        });
        loadConfigButton.addActionListener(e->{
            Config.getInstanceConfig().settaParametriConfigurazione();
            Config.getInstanceConfig().settaConfigurazione();
            //settaConfigurazione(configUI);
        });
        helpButton.addActionListener(e->{
            PanelManage.loadHelpPanel(e);
            HelpUI.getHelpInstace().componiHelpmenu();
        });
        indietroSenderUI.addActionListener(e->{
            PanelManage.loadCreaCaricaConfigPanel();
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

        corpoMessaggio.addKeyListener(new KeyListener() {
            List<Integer> codCaratteriNulli = Arrays.asList(16,37,38,39,40,127,27);

            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
                String stringListner01 = corpoMessaggio.getFocusListeners()[0].toString();
                posizioneCursore = Integer.parseInt(stringListner01.substring(stringListner01.indexOf("=")+1,stringListner01.indexOf(",")));
                if (!codCaratteriNulli.contains(e.getKeyCode())) {
                    switch (e.getKeyCode()) {
                        case 8:
                            System.out.println("Cursor Del: " + posizioneCursore);
                            if (posizioneCursore > 0) {
                                System.out.println("->" + (posizioneCursore - 1));
                                stringBuilder.deleteCharAt(posizioneCursore - 1);
                                //stringBuilder.deleteCharAt(stringBuilder.length()-1);
                            }
                            break;
                        case 10:
                            stringBuilder.append("◙");
                            break;
                        default:
                            if (!codCaratteriNulli.contains(e.getKeyCode())){
                                System.out.println(e.getKeyChar());
                                System.out.println(e.getKeyCode());
                                if (posizioneCursore < stringBuilder.length()){
                                    System.out.println("Cursor+1: " + posizioneCursore);
                                    stringBuilder.insert(posizioneCursore,e.getKeyChar());
                                }else {
                                    System.out.println("Cursor: " + posizioneCursore);
                                    stringBuilder.insert(posizioneCursore, e.getKeyChar());
                                    //stringBuilder.append(e.getKeyChar());
                                }
                            }
                    }
                }
                /*
                if (e.getKeyCode() == 8) {//Da modificare
                    System.out.println("Cursor Del: "+posizioneCursore);
                    if (posizioneCursore > 0){
                        System.out.println("->"+(posizioneCursore-1));
                        stringBuilder.deleteCharAt(posizioneCursore-1);
                        //stringBuilder.deleteCharAt(stringBuilder.length()-1);
                        }
                    }
                if (e.getKeyCode() == 10) {
                    stringBuilder.append("<br>");
                }
                if (!codCaratteriNulli.contains(e.getKeyCode())){
                    if (posizioneCursore < stringBuilder.length()){
                        System.out.println("Cursor+1: " + posizioneCursore);
                        stringBuilder.insert(posizioneCursore,e.getKeyChar());
                    }else {
                        System.out.println("Cursor: " + posizioneCursore);
                        stringBuilder.insert(posizioneCursore, e.getKeyChar());
                        //stringBuilder.append(e.getKeyChar());
                    }
                }
 */
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        imgToTextButton.addActionListener(e->{
            //String stringListner = corpoMessaggio.getFocusListeners()[0].toString();
            //System.out.println("Listner "+ stringListner.substring(stringListner.indexOf("=")+1,stringListner.indexOf(",")));
            //System.out.println(stringBuilder);
            //System.out.println(Arrays.toString(corpoMessaggio.getFocusListeners()));
            try {
                //Sender.addTextEmail(corpoMessaggio.getText());
                //Sender.creaBodyPart();
                cid = "<img src="+"cid:image".concat(String.valueOf((n))).concat(">");
                listaCid.add(cid);
                String iconPath = PathSelector.getFileSrc().getAbsolutePath();
                iconImgLine.add(iconPath);
                corpoMessaggio.insertIcon(new ImageIcon(iconPath));
                testoImgPlaceHolder = testoImgPlaceHolder.concat(corpoMessaggio.getText()).concat("◘");
                System.out.println();
                writeHtmlFile("◘");
                n++;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
 /*
    private void settaConfigurazione(Config configUI){
        /**
         * Esegue il setting dell'applicazione tramite i dati letti nel file config.conf
         * @paranm Oggetto di tipo Config, istanza

        String parametri = "";
        try {
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

  */
    /**
     * Metodo che si occupa della pulizia dei campi UI dopo l'inoltro delle email e ristabilisce il focus
     */
    public boolean reset(){
        Sender.cleanMultipartMessage();
        try {
            setStringBilderToText();
        }catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException){
            System.out.println("--stringIndexOutOfBoundsException--"+"calcolato");
        }
        corpoMessaggio.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }
        });
        int answer = JOptionPane.showConfirmDialog(null,"Email Inviata!\nRipulire i campi ed il corpo della email?");
        if (answer == 0) {
            stringBuilder = new StringBuilder(""); //Fondamentale la pulizia dello stringBuilder cuore del messaggio
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
    public JButton getLoadConfigButton() {
        return loadConfigButton;
    }
    public void setToFocusable(boolean bool){
        to.setEnabled(bool);
    }

    /**
     * Gestisce il caricamento della barra in base ai Thread dell'invio della mail.
     * @param percenuale Integer, caricamento
     * @param inProgress Boolean, true se non ci sono problemi ed il Thread procede; False se si blocca un Thread
     *                   durante l'invio del messaggio.
     */
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

    /**
     * Si occupa di impostare il riempimento della barra con l'intero passatogli.
     * @param fill Integer, quanto carico la barra
     */
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

    public JTextField getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to.setText(to);
    }

    public JTextField getCc() {
        return cc;
    }
    public JTextField getObjectEmail() {
        return objectEmail;
    }

    public JTextPane getCorpoMessaggio() {
        return corpoMessaggio;
    }
    public boolean isChecked(){
        return ricConfermaCheckBox.isSelected();
    }

    /**
     * @return Integer, numero usato per l'inserimento delle immagini tramite cid html
     */
    public int getN() {//Chiamato dalla sottoclasse salvataggio, nella classe MsgManager
        return n;
    }

    /**
     * Imposta il numero usato per l'inserimento delle immagini tramite cid html
     * @param n Integer numero per il cid html image
     */
    public void setN(int n) {
        this.n = n;
    }

    /**
     * @return Integer ritorna la posizione del cursore come intero
     */
    public int getPosizioneCursore() {
        return posizioneCursore;
    }

    /**
     * Setta la posizione del cursore
     * @param posizioneCursore Integer, posizione del cursore
     */
    public void setPosizioneCursore(int posizioneCursore) {
        this.posizioneCursore = posizioneCursore;
    }

    /**
     * Aggiunge ad uno stringBuilder usato per memorizzare l'html la stringa passatagli, in questo caso il placeHolder
     * per le immagini ◘
     * @param htmlLine String, testo html, ◘
     */
    public void writeHtmlFile(String htmlLine) {
        stringBuilder.append(htmlLine);
        System.out.println("StringBuilder"+stringBuilder);
        System.out.println("-->"+stringBuilder.toString().lines().toList());
        System.out.println("htmlline"+htmlLine);
        /*
        try {
            System.out.println(htmlLine);
            FileWriter fileWriter = new FileWriter(path.toString(), true);
            fileWriter.write(htmlLine);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
             */
    }
    public StringBuilder getStringBuilder() {
        return stringBuilder;
    }
    public void setStringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    /**
     * Restituisce la lista dei cid html così da poterli richiamare per aggiungere le immagini nel corpo della email
     * @return lista di String
     */
    public List<String> getListaCid() {
        return listaCid;
    }
    public void setListaCid(List<String> listaCid) {
        this.listaCid = listaCid;
    }

    /**
     *Carica le immagini per il corpo della mail dal file, tramite testoImgPlaceHolder con il quale inserisco
     * ogni volta che trovo ◙ lo \n, ogni volta che trovo  ◘ al corpo del messaggio l'imageIcon estratta dalla lista
     * delle iconImageLine ed infine per tutti gli altri caratteri fa l'append.
     */
    public void caricaImgIcon(){
        char[] charsPlaceholder = testoImgPlaceHolder.toCharArray();
        Document doc = corpoMessaggio.getDocument();
        int i = 0;
        for (char ch : charsPlaceholder) {
            int docLenght = doc.getLength();
            try {
                switch (ch){
                    case '◙':
                        System.out.println(doc.getLength());
                        doc.insertString(docLenght,"\n", null);
                        break;
                    case '◘':
                        corpoMessaggio.insertIcon(new ImageIcon(iconImgLine.get(i)));
                        i++;
                        break;
                    default:
                        doc.insertString(docLenght, Character.toString(ch), null);
                }
            } catch (BadLocationException locationException){
                System.out.println(locationException.getMessage());
            }
        }
        corpoMessaggio.addFocusListener(new FocusAdapter() {//Aggiungo un nuovo focus listner al corpo del messaggio
                                                            // nella UI
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
            }
        });
    }

    /**
     * Traduce un testo html in testo normale eliminando i tag ed inserendo al loro posto i placeHolder (<br> con ◙);
     * (<img.. che identifica l'inizio del cid con ◘)
     */
    public void setStringBilderToText(){
        for (String st : stringBuilder.toString().lines().toList()) {
            if (st.contains("<br>")){
                System.out.println("replace");
                stringBuilder.replace(0,stringBuilder.length(),stringBuilder.toString().replaceAll("<br>","◙"));
                //stringBuilder.replace(stringBuilder.indexOf("/"),stringBuilder.indexOf("/")+1,"<br>");
            }
            if (st.contains("<img")){
                System.out.println("blaaaaaaaaaaaaaaaaaaaaaa");
                System.out.println(Arrays.toString(listaCid.toArray()));
                for (int i = 0; i < listaCid.size(); i++ ){
                    System.out.println(i);
                    //stringBuilder.replace(0, stringBuilder.length(), stringBuilder.toString().replace(listaCid.get(i), "1" ));
                    System.out.println("caret"+corpoMessaggio.getCaretPosition());
                    System.out.println("stringB"+stringBuilder.length());
                    //stringBuilder.replace(stringBuilder.indexOf("<i"),stringBuilder.indexOf(">")+20,"◘");
                    stringBuilder.replace(stringBuilder.indexOf(listaCid.get(i)),stringBuilder.indexOf(listaCid.get(i))+20,"◘");
                }
                stringBuilder.append(" ");
                System.out.println(stringBuilder.length());
                System.out.println("convertito "+stringBuilder.toString());
                //stringBuilder.replace(0,stringBuilder.length(),stringBuilder.toString().replace("1",listaCid.get(i)));
            }
        }
        System.out.println("convertito "+stringBuilder.toString());
    }

    /**
     * Trasforma il testo (stringBuilder) in html rimpiazzando i placeHolder con i tag ed il cid html (◙ con i cid dela listaCid) e
     * (◙ con il tag html <br>)
     * @return Oggetto di tipo StringBuilder
     */
    public String parseHtml(){
        long conta= stringBuilder.toString().chars().filter(ch -> ch == '◙').count(); //Inverse White Circle
        long conta1 = stringBuilder.toString().chars().filter(ch -> ch == '◘').count(); //Inverse Bullet
        System.out.println("conta1"+conta1);
        //for (String st : stringBuilder.toString().lines().toList()) {
            //System.out.println(st);
            /*
            if (st.contains("<img")) {
                System.out.println("crea"+st);
                Sender.creaBodyPart(listaCid.get(conta), n);
                conta++;
            }
             */
            //if (st.contains("/")){
        for (int o = 0; o < conta1; o++){
            stringBuilder.replace(stringBuilder.indexOf("◘"),stringBuilder.indexOf("◘")+1,listaCid.get(o));
            //stringBuilder.replace(0,stringBuilder.length(),stringBuilder.toString().replace("1",listaCid.get(o)));
            //stringBuilder.replace(stringBuilder.indexOf("1"),stringBuilder.indexOf("1")+1,listaCid.get(o));
            //stringBuilder.toString().replaceFirst("1",listaCid.get(o));
            //stringBuilder.insert(stringBuilder.indexOf("◘"), listaCid.get(o));
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXX"+stringBuilder);
            System.out.println("ooooooooooo "+o);
            Sender.creaBodyPart(listaCid.get(o), o);
        }
        for (int o = 0; o < conta; o++){
            System.out.println("replace");
            stringBuilder.replace(0,stringBuilder.length(),stringBuilder.toString().replaceAll("◙","<br>"));
            //stringBuilder.replace(stringBuilder.indexOf("/"),stringBuilder.indexOf("/")+1,"<br>");
        }
            //} else if (st.contains("1")){
                System.out.println("blaaaaaaaaaaaaaaaaaaaaaa"+listaCid.size());
                System.out.println(Arrays.toString(listaCid.toArray()));
                //for (; i < listaCid.size(); i++ ){

                //}
                //stringBuilder.replace(0,stringBuilder.length(),stringBuilder.toString().replace("1",listaCid.get(i)));
            //}
            System.out.println("->"+stringBuilder+"<-");
        //}
        /*
        try {
            Scanner scanner = new Scanner(path);
            while (scanner.hasNext()){
                sc = scanner.nextLine();
                if ( sc.startsWith("<img")){
                    System.out.println("img"+cid);
                    Sender.creaBodyPart(cid, n);
                }
                //msg.append(sc);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         */
        return stringBuilder.toString();
    }

    public List<String> getIconImgLine() {
        return iconImgLine;
    }
    public void setIconImgLine(List<String> iconImgLine) {
        this.iconImgLine = iconImgLine;
    }
}
