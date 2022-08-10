package clientemail.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelManage {
    private static JFrame mainFrame = new JFrame("Client Email");
    private static SenderUI senderUI = new SenderUI(); //Istanzio la classe grafica per l'invio email;
    private static Config configUI = new Config();

    public static void startUI(){
        configUI.setInstanceConfig(configUI);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocation(300,100);
        mainFrame.setSize(1000,600);
        if (loadConfigPanel()){
            mainFrame.setVisible(true);
            senderUI.sendEmail(senderUI, configUI);//chiamo il metodo d'istanza sendEmail e gli passo l'istanza della classe SenderUI
        }
    }

    public static void loadSendPanel(ActionEvent event, Config configUI){
        SenderUI senderUI = new SenderUI(); //Istanzio la classe grafica per l'invio email
        senderUI.sendEmail(senderUI, configUI);//chiamo il metodo d'istanza sendEmail e gli passo l'istanza della classe SenderUI
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
        }else {
            mainFrame.setContentPane(senderUI.getPanelSender()); // //richiamo il pannello per invio email
        }
        return true;
    }

}
