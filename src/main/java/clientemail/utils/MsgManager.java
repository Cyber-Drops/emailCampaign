package clientemail.utils;

import clientemail.send.Sender;
import clientemail.view.SenderUI;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.*;
import java.util.List;


public class MsgManager {

    private Gson gsonSalvataggio = new Gson();
    public void salvaMsg(){
        Salvataggio salvataggio = new Salvataggio();
        String salvataggioGson = gsonSalvataggio.toJson(salvataggio);
        File saveFile = PathSelector.saveFile();
        if (saveFile != null) {
            try {
                FileWriter fileWriter = new FileWriter(saveFile);
                fileWriter.write(salvataggioGson);
                fileWriter.flush();
                fileWriter.close();
                JOptionPane.showMessageDialog(null, "Salvataggio avvenuto con successo!!");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }catch (IOException e){
                return;
            }
        }
    }
    public void caricaMsg(){
        try {
            File file = PathSelector.getFileSrc();
            FileReader reader = new FileReader(file);
            Salvataggio salvataggio = gsonSalvataggio.fromJson(reader, Salvataggio.class);
            //SenderUI.senderUIInstance.getFrom().setText(salvataggio.from);
            SenderUI.senderUIInstance.getTo().setText(salvataggio.to);
            SenderUI.senderUIInstance.getObjectEmail().setText(salvataggio.object);
            SenderUI.senderUIInstance.getCc().setText(salvataggio.cc);
            //SenderUI.senderUIInstance.getCorpoMessaggio().setText(salvataggio.corpoMessaggio);
            //SenderUI.senderUIInstance.setHtmlTextMail(salvataggio.htmlTextMail);
            SenderUI.senderUIInstance.setIconImgLine(salvataggio.iconImageLine); // Carico List contenente Stringhe path img
            SenderUI.senderUIInstance.setN(salvataggio.n);
            SenderUI.senderUIInstance.setTestoImgPlaceHolder(salvataggio.testoImgPlaceHolder);
            SenderUI.senderUIInstance.setListaCid(salvataggio.listaCid);
            SenderUI.senderUIInstance.setStringBuilder(salvataggio.htmlStringBuilder);//Carico lo StringBuilder
            SenderUI.senderUIInstance.setPosizioneCursore(salvataggio.posizioneCursore);
            if (!SenderUI.senderUIInstance.getAttachText().getText().isEmpty()) {
                ManagerAllegati.caricaAllegati(salvataggio.allegati);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public class Salvataggio {
        //private String from = SenderUI.senderUIInstance.getFrom().getText();
        private String to = SenderUI.senderUIInstance.getTo().getText();
        private String cc = SenderUI.senderUIInstance.getCc().getText();
        private String object = SenderUI.senderUIInstance.getObjectEmail().getText();
        private String allegati = Sender.attachFile.toString();
        private String corpoMessaggio = SenderUI.senderUIInstance.getCorpoMessaggio().getText();
        //private String htmlTextMail = SenderUI.senderUIInstance.getHtmlTextMail();
        private List<String> iconImageLine = SenderUI.senderUIInstance.getIconImgLine();
        private List<String> listaCid = SenderUI.senderUIInstance.getListaCid();
        private StringBuilder htmlStringBuilder = SenderUI.senderUIInstance.getStringBuilder();
        private int posizioneCursore = SenderUI.senderUIInstance.getPosizioneCursore();
        private String testoImgPlaceHolder = SenderUI.senderUIInstance.getTestoImgPlaceHolder();
        private int n = SenderUI.senderUIInstance.getN();

        //TODO attributo htmltext per img in line

    }
}
