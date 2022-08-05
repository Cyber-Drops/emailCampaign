package clientemail.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelManage {

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

}
