package clientemail.utils;

public class Contatto {

    private String email;
    private String nome;
    private String cognome;
    private String telefono;

    public Contatto(String email, String nome, String cognome){
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
    }
    public Contatto(String email, String nome, String cognome, String telefono){
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
    }


    @Override
    public String toString() {
        StringBuilder contattoString = new StringBuilder();
        contattoString.append(this.email).append(this.nome).append(this.cognome).append(this.telefono);
        return contattoString.toString();
    }
}
