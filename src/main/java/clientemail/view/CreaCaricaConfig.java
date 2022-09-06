package clientemail.view;

import clientemail.utils.PathSelector;

import javax.swing.*;
import java.io.File;
import java.net.URL;

public class CreaCaricaConfig {
    private JButton CreaConfigButton;
    private JButton CaricaConfigButton;
    private JPanel creaCaricaConfigPanel;
    private JPanel panel1;
    private JLabel imageConfigLabel;

    private JButton rubricaButton;

    private boolean caricaButtonPressed = false;
    private static CreaCaricaConfig creaCaricaConfigInstance;

    {
        if (System.getProperty("os.name").contains("Windows")){
            URL mainImg = (getClass().getClassLoader().getResource("image/mainImg.png"));
            imageConfigLabel.setIcon(new ImageIcon(mainImg));
        }else {
            URL mainImg = (getClass().getClassLoader().getResource("image".concat(System.getProperty("file.separator")).concat("mainImg.png")));
            imageConfigLabel.setIcon(new ImageIcon(mainImg));
        }
        initComponent();//Inizializzo i componenti della UI tramite un inizializzatore di instanza

    }
    public CreaCaricaConfig(){}

    public static void setCreaCaricaConfig(CreaCaricaConfig creaCaricaConfig) {
        creaCaricaConfigInstance = creaCaricaConfig;
    }
    public static CreaCaricaConfig getCreaCaricaConfigInstance(){
        return creaCaricaConfigInstance;
    }

    public JPanel getCreaCaricaConfigPanel() {
        return creaCaricaConfigPanel;
    }
    private void initComponent(){
        CreaConfigButton.addActionListener(e->{

            String pathWork = PathSelector.getPathWork();
            if (!pathWork.equals("annulla")){
                PanelManage.loadPanel(e);
            }else {
                PanelManage.loadCreaCaricaConfigPanel();
            }
            Config.getInstanceConfig().setPathWork(new File(pathWork));
        });
        CaricaConfigButton.addActionListener(e->{
            caricaButtonPressed = true;
            PanelManage.loadConfigPanel(e);
            //PanelManage.loadSendPanel(e);
        });

        rubricaButton.addActionListener(e->{
            PanelManage.loadRubricaPanel(e);
        });
    }
    public boolean isCaricaButtonPressed(){
        return caricaButtonPressed;
    }

}
