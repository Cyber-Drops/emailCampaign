package clientemail.view;

import clientemail.utils.Rubrica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelManage {
    private static JFrame mainFrame = new JFrame("Client Email");
    private static SenderUI senderUI = new SenderUI(); //Istanzio la classe grafica per l'invio email;
    private static CreaCaricaConfig creaCaricaConfigUI;
    private static Config configUI;
    private static HelpUI helpUI;
    public static void startUI(){
        CreaCaricaConfig.setCreaCaricaConfig(new CreaCaricaConfig());
        creaCaricaConfigUI = CreaCaricaConfig.getCreaCaricaConfigInstance();
        Config.setInstanceConfig();
        configUI = Config.getInstanceConfig();
        ContattoUI.setContattoUIInstance();
        Rubrica.setRubricaInstance();
        HelpUI.setHelpInstace();
        helpUI = HelpUI.getHelpInstace();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocation(300,100);
        mainFrame.setSize(1200,800);
        mainFrame.setVisible(true);
        loadCreaCaricaConfigPanel();
        senderUI.sendEmail(senderUI);//chiamo il metodo d'istanza sendEmail e gli passo l'istanza della classe SenderUI
    }

    public static void loadSendPanel(ActionEvent event){
        mainFrame.setLocation(350,100);
        mainFrame.setSize(1200,800);
        Container container = ((JButton) event.getSource()).getParent();
        while (!(container instanceof JFrame)){
            container = container.getParent();
        }
        ((JFrame) container).setContentPane(senderUI.getPanelSender());
        container.revalidate();
    }
    public static void loadCreaCaricaConfigPanel( ){
        mainFrame.setSize(800,500);
        mainFrame.setLocation(550,200);
        JPanel creaCaricaConfigPanel = creaCaricaConfigUI.getCreaCaricaConfigPanel();
        mainFrame.setContentPane(creaCaricaConfigPanel);
    }
    public static void loadRubricaPanel(ActionEvent event){
        Container container = ((JButton) event.getSource()).getParent();
        while (!(container instanceof JFrame)){
            container = container.getParent();
        }
        ((JFrame) container).setContentPane(RubricaUI.getRubricaUIinstance().getPanel1());
        //RubricaUI.getRubricaUIinstance().aggiornaRubricaUI();
        Rubrica.getRubricaInstance().salvaRubricaGson();
        Rubrica.getRubricaInstance().caricaRubricaGson();
        container.revalidate();
    }
    public static void loadConattoPanel(ActionEvent event){
        Container container = ((JButton) event.getSource()).getParent();
        while (!(container instanceof JFrame)){
            container = container.getParent();
        }
        ((JFrame) container).setContentPane(ContattoUI.getContattoUIInstance().getContattoUIPanel());
        container.revalidate();
    }

    public static void loadPanel(ActionEvent event){
        mainFrame.setSize(800,500);
        mainFrame.setLocation(550,200);
        Container container = ((JButton) event.getSource()).getParent();
        while (!(container instanceof JFrame)){
            container = container.getParent();
        }
        ((JFrame) container).setContentPane(configUI.getConfigPanel());
        container.revalidate();
    }

    public static boolean loadConfigPanel(ActionEvent e){
        if (!configUI.isConfig()){
            //loadConfigPanel(e);
            loadPanel(e);
            //controllo directory, listo .conf, se uno setto, altrimenti seleziono e setto.
            //configUI.setConfigFile();
        }else {
            loadSendPanel(e);
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

