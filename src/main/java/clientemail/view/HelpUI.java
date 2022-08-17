package clientemail.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;


public class HelpUI {
    private JPanel helpPanel;
    private JTextArea textHelpArea;
    private JButton exitMenuButton;
    private JLabel linkHelpLabel;

    private static HelpUI helpInstace;

    public static HelpUI getHelpInstace() {
        return helpInstace;
    }

    public static void setHelpInstace() {
        helpInstace = new HelpUI();
    }

    public JPanel getHelpPanel() {
        initComponent();
        return helpPanel;
    }

    public void initComponent(){
        exitMenuButton.addActionListener(PanelManage::loadSendPanel);
        linkHelpLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkHelpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://cyber-drops.com/2022/08/16/campagna-email/"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                linkHelpLabel.setText("<html><a href=''>www.cyber-drops.com</a></html>www.cyber-drops.com");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                linkHelpLabel.setText("www.cyber-drops.com");
            }

        });
    }
    public void componiHelpmenu(){
        /**
         * Caricamento e parsing del file, per la composizione dell'help.
         * Utilizzato metodo getResourceAsStream per leggere il file.
         */
        InputStream textHelp;
        if (System.getProperty("os.name").contains("Windows")){
            textHelp = (getClass().getClassLoader().getResourceAsStream("help/helpMenu.txt"));
        }else {
            textHelp = (getClass().getClassLoader().getResourceAsStream("help".concat(System.getProperty("file.separator")).concat("helpMenu.txt")));
        }
        try {
            int ch;
            StringBuilder testo = new StringBuilder();
            while ((ch = textHelp.read()) != -1){
                char character  = (char) ch;
                testo.append(character);
            }
            textHelpArea.setText(testo.toString());
       } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
