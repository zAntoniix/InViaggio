package dominio;
import java.util.*;

public class Cliente {

    //Attributi
    private String nome;
    private String cognome;
    private String CF;
    private String codPersonale;
    private HashMap<String,Biglietto> elencoBiglietti;

    //Costruttore
    public Cliente(String nome, String cognome, String CF, String codPersonale, HashMap<String, Biglietto> elencoBiglietti) {
        this.nome = nome;
        this.cognome = cognome;
        this.CF = CF;
        this.codPersonale = codPersonale;
        this.elencoBiglietti = elencoBiglietti;
    }

    //Metodi
    public Boolean confermaBiglietto(Biglietto b){
        if(elencoBiglietti.put(b.getCodice(),b)!=null)
            return true;
        else
            return false;
    }
}
