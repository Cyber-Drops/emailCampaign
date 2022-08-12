package clientemail.view;

import javax.swing.*;


public class HelpUI {
    private JPanel helpPanel;
    private JTextArea textArea1;
    private JButton exitMenuButton;

    public JPanel getHelpPanel() {
        initComponent();
        return helpPanel;
    }

    public void initComponent(){

        exitMenuButton.addActionListener(e->{
            PanelManage.loadSendPanel(e);
        });

    }
}
