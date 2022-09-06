package clientemail.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Contatto extends ArrayList {

    private List<String> datiContatto;

    public Contatto(List<String> dati){
        this.datiContatto = dati;
    }

    public List<String> getDatiContatto() {
        return datiContatto;
    }

    @Override
    public String toString() {
        StringBuilder contattoString = new StringBuilder();
        int i = 0;
        for (String dato : datiContatto) {
            contattoString.append(dato);
            if (i < 3){
                contattoString.append(",");
            }
            i++;
        }
        return contattoString.toString();
    }

}
