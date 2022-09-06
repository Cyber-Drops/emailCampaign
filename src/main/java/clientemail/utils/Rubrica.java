package clientemail.utils;

import java.util.ArrayList;
import java.util.List;

public class Rubrica {
    private List<Contatto> contattiRubrica = new ArrayList<>();
    private static Rubrica rubricaInstance;

    public Rubrica(){}

    public static void setRubricaInstance(){ //Instanziata nella classe PanelManage, metodo start()
        rubricaInstance = new Rubrica();
    }

    public static Rubrica getRubricaInstance() {
        return rubricaInstance;
    }

    public List<Contatto> getContattiRubrica() {
        return contattiRubrica;
    }

    public void aggiungiContatto(Contatto contatto) {
        this.contattiRubrica.add(contatto);
    }

    @Override
    public String toString() {
        for (Contatto contatto : contattiRubrica) {
            for (Object dato :  contatto.getDatiContatto()) {
                System.out.println(dato.toString());
            }
        }
        StringBuilder rubricaString = new StringBuilder();
        for (Contatto contatto : contattiRubrica) {
            rubricaString.append(contatto);
            rubricaString.append("\n");
        }
        return rubricaString.toString();
    }
}
