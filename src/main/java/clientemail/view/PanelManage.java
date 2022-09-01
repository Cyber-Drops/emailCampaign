package clientemail.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelManage {
    private static JFrame mainFrame = new JFrame("Client Email");
    private static SenderUI senderUI = new SenderUI(); //Istanzio la classe grafica per l'invio email;
    private static Config configUI;
    private static HelpUI helpUI;
    public static void startUI(){
        Config.setInstanceConfig();
        configUI = Config.getInstanceConfig();
        HelpUI.setHelpInstace();
        helpUI = HelpUI.getHelpInstace();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocation(300,100);
        mainFrame.setSize(1200,800);
        if (loadConfigPanel()){
            mainFrame.setVisible(true);
            senderUI.sendEmail(senderUI);//chiamo il metodo d'istanza sendEmail e gli passo l'istanza della classe SenderUI
        }

    }

    public static void loadSendPanel(ActionEvent event){
        Container container = ((JButton) event.getSource()).getParent();
        while (!(container instanceof JFrame)){
            container = container.getParent();
        }
        ((JFrame) container).setContentPane(senderUI.getPanelSender());
        container.revalidate();
    }

    public static boolean loadConfigPanel(){
        if (!configUI.isConfig()){
            mainFrame.setContentPane(configUI.getConfigPanel());
            configUI.setConfigFile();
        }else {
            mainFrame.setContentPane(senderUI.getPanelSender()); // //richiamo il pannello per invio email
        }
        return true;
    }

    public static void loadHelpPanel(ActionEvent event){
        Container container = ((JButton) event.getSource()).getParent();
        while (!(container instanceof JFrame)){
            container = container.getParent();
        }
        ((JFrame) container).setContentPane(helpUI.getHelpPanel());
        container.revalidate();
    }

}
