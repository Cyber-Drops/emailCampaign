package clientemail.view;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.net.URL;

public class Emoticon {
    private JPanel emoticonPanel;
    private JButton smile_00;
    private JButton smile_01;
    private JButton smile_09;
    private JButton smile_03;
    private JButton smile_02;
    private JButton smile_04;
    private JButton smile_05;
    private JButton smile_06;
    private JButton smile_07;
    private JButton smile_08;
    private JButton smile_10;

    private static Emoticon emoticonInstance = new Emoticon();
    {
        if (System.getProperty("os.name").contains("Windows")){
            URL smile_00Img = (getClass().getClassLoader().getResource("emoji/smile_00.png"));
            smile_00.setIcon(new ImageIcon(smile_00Img));
            URL smile_01Img = (getClass().getClassLoader().getResource("emoji/smile_01.png"));
            smile_01.setIcon(new ImageIcon(smile_01Img));
            URL smile_02Img = (getClass().getClassLoader().getResource("emoji/smile_02.png"));
            smile_02.setIcon(new ImageIcon(smile_02Img));
            URL smile_03Img = (getClass().getClassLoader().getResource("emoji/smile_03.png"));
            smile_03.setIcon(new ImageIcon(smile_03Img));
            URL smile_04Img = (getClass().getClassLoader().getResource("emoji/smile_04.png"));
            smile_04.setIcon(new ImageIcon(smile_04Img));
            URL smile_05Img = (getClass().getClassLoader().getResource("emoji/smile_05.png"));
            smile_05.setIcon(new ImageIcon(smile_05Img));
            URL smile_06Img = (getClass().getClassLoader().getResource("emoji/smile_06.png"));
            smile_06.setIcon(new ImageIcon(smile_06Img));
            URL smile_09Img = (getClass().getClassLoader().getResource("emoji/smile_09.png"));
            smile_09.setIcon(new ImageIcon(smile_09Img));
            URL smile_10Img = (getClass().getClassLoader().getResource("emoji/smile_10.png"));
            smile_10.setIcon(new ImageIcon(smile_10Img));
        }else {
            URL smile_00Img = (getClass().getClassLoader().getResource("emoji".concat(System.getProperty("file.separator")).concat("smile_00.png")));
            smile_00.setIcon(new ImageIcon(smile_00Img));
            URL smile_01Img = (getClass().getClassLoader().getResource("emoji".concat(System.getProperty("file.separator")).concat("smile_01.png")));
            smile_01.setIcon(new ImageIcon(smile_01Img));
            URL smile_02Img = (getClass().getClassLoader().getResource("emoji".concat(System.getProperty("file.separator")).concat("smile_02.png")));
            smile_02.setIcon(new ImageIcon(smile_02Img));
            URL smile_03Img = (getClass().getClassLoader().getResource("emoji".concat(System.getProperty("file.separator")).concat("smile_03.png")));
            smile_03.setIcon(new ImageIcon(smile_03Img));
            URL smile_04Img = (getClass().getClassLoader().getResource("emoji".concat(System.getProperty("file.separator")).concat("smile_04.png")));
            smile_04.setIcon(new ImageIcon(smile_04Img));
            URL smile_05Img = (getClass().getClassLoader().getResource("emoji".concat(System.getProperty("file.separator")).concat("smile_05.png")));
            smile_05.setIcon(new ImageIcon(smile_05Img));
            URL smile_06Img = (getClass().getClassLoader().getResource("emoji".concat(System.getProperty("file.separator")).concat("smile_06.png")));
            smile_06.setIcon(new ImageIcon(smile_06Img));
            URL smile_07Img = (getClass().getClassLoader().getResource("emoji".concat(System.getProperty("file.separator")).concat("smile_07.png")));
            smile_07.setIcon(new ImageIcon(smile_07Img));
            URL smile_08Img = (getClass().getClassLoader().getResource("emoji".concat(System.getProperty("file.separator")).concat("smile_08.png")));
            smile_08.setIcon(new ImageIcon(smile_08Img));
            URL smile_09Img = (getClass().getClassLoader().getResource("emoji".concat(System.getProperty("file.separator")).concat("smile_09.png")));
            smile_09.setIcon(new ImageIcon(smile_09Img));
            URL smile_10Img = (getClass().getClassLoader().getResource("emoji".concat(System.getProperty("file.separator")).concat("smile_10.png")));
            smile_10.setIcon(new ImageIcon(smile_10Img));
        }
        initComponent();
    }

    public static Emoticon getEmoticonInstance() {
        return emoticonInstance;
    }

    public JPanel getEmoticonPanel() {
        return emoticonPanel;
    }
    public void initComponent(){
        smile_00.addActionListener(e->{
            SenderUI.senderUIInstance.writeHtmlFile("\uD83D\uDE0A");
            insertEmojiCorpoEmail("\uD83D\uDE0A");
            PanelManage.loadSendPanel(e);
        });
        smile_01.addActionListener(e->{
            SenderUI.senderUIInstance.writeHtmlFile("\uD83D\uDE06");
            insertEmojiCorpoEmail("\uD83D\uDE06");
            PanelManage.loadSendPanel(e);
        });
        smile_02.addActionListener(e->{
            SenderUI.senderUIInstance.writeHtmlFile("\uD83D\uDE06");
            insertEmojiCorpoEmail("\uD83D\uDE06");
            PanelManage.loadSendPanel(e);
        });
        smile_03.addActionListener(e->{
            SenderUI.senderUIInstance.writeHtmlFile("\uD83D\uDE09");
            insertEmojiCorpoEmail("\uD83D\uDE09");
            PanelManage.loadSendPanel(e);
        });
        smile_04.addActionListener(e->{
            SenderUI.senderUIInstance.writeHtmlFile("\uD83D\uDE07");
            insertEmojiCorpoEmail("\uD83D\uDE07");
            PanelManage.loadSendPanel(e);
        });
        smile_05.addActionListener(e->{
            SenderUI.senderUIInstance.writeHtmlFile("\uD83D\uDE0D");
            insertEmojiCorpoEmail("\uD83D\uDE0D");
            PanelManage.loadSendPanel(e);
        });
        smile_06.addActionListener(e->{
            SenderUI.senderUIInstance.writeHtmlFile("\uD83D\uDE00");
            insertEmojiCorpoEmail("\uD83D\uDE00");
            PanelManage.loadSendPanel(e);
        });
        smile_07.addActionListener(e->{
            SenderUI.senderUIInstance.writeHtmlFile("\uD83D\uDE1C");
            insertEmojiCorpoEmail("\uD83D\uDE1C");
            PanelManage.loadSendPanel(e);
        });
        smile_08.addActionListener(e->{
            SenderUI.senderUIInstance.writeHtmlFile("\uD83D\uDE1D");
            insertEmojiCorpoEmail("\uD83D\uDE1D");
            PanelManage.loadSendPanel(e);
        });
        smile_09.addActionListener(e->{
            SenderUI.senderUIInstance.writeHtmlFile("\uD83D\uDE02");
            insertEmojiCorpoEmail("\uD83D\uDE02");
            PanelManage.loadSendPanel(e);
        });
        smile_10.addActionListener(e->{
            SenderUI.senderUIInstance.writeHtmlFile("\uD83D\uDE0F");
            insertEmojiCorpoEmail("\uD83D\uDE0F");
            PanelManage.loadSendPanel(e);
        });

    }
    public void insertEmojiCorpoEmail(String emoji){
        Document document = SenderUI.senderUIInstance.getCorpoMessaggio().getDocument();
        int documentSize = document.getLength();
        try {
            document.insertString(documentSize,emoji, null);
        } catch (BadLocationException ex) {
            throw new RuntimeException(ex);
        }
    }
}
