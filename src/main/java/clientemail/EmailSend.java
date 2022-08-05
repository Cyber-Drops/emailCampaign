package clientemail;

import clientemail.view.Config;
import clientemail.view.SenderUI;

import javax.management.MBeanAttributeInfo;
import javax.swing.*;
import java.net.URL;


public class EmailSend {
    /**
     * @programma Email Campagin.jar
     * @author Simone Tempesta
     * @version 1.0.0
     *
     */
    public static void main(String[] args) {
        /**
         * Istanzio la classe grafica per l'invio email
         * chiamo il metodo d'istanza sendEmail e gli passo l'istanza della classe SenderUI
         */
        JFrame mainFrame = new JFrame("Client Email");
        SenderUI senderUI = new SenderUI(); //Istanzio la classe grafica per l'invio email
        Config configUI = new Config();
        configUI.setInstanceConfig(configUI);
        if (!configUI.isConfig()){
            mainFrame.setContentPane(configUI.getConfigPanel());
        }else {
            mainFrame.setContentPane(senderUI.getPanelSender()); // //richiamo il pannello per invio email
        }
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocation(300,100);
        mainFrame.setSize(1000,600);
        mainFrame.setVisible(true);
        senderUI.sendEmail(senderUI, configUI);//chiamo il metodo d'istanza sendEmail e gli passo l'istanza della classe SenderUI

    }
}
