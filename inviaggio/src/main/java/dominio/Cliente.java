package dominio;
import java.util.*;

public class Cliente {

    //Attributi
    private String nome;
    private String cognome;
    private String CF;
    private String codPersonale;
    private LinkedHashMap<String,Biglietto> elencoBiglietti;

    //Costruttore
    public Cliente(String nome, String cognome, String CF, String codPersonale) {
        this.nome = nome;
        this.cognome = cognome;
        this.CF = CF;
        this.codPersonale = codPersonale;
        this.elencoBiglietti = new LinkedHashMap<String,Biglietto>();
    }

    //Metodi
    public boolean confermaBiglietto(Biglietto b){
        if(elencoBiglietti.put(b.getCodice(),b)==null)
            return true;
        else{
            b.incrementaPosto();
            return false;
        }
    }

    public boolean rimuoviAccount(){
        LinkedList<Biglietto> bigliettoPrenotato = new LinkedList<>();
        Iterator<Map.Entry<String,Biglietto>>iterator=elencoBiglietti.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Biglietto> entry=iterator.next();
            Biglietto b = entry.getValue();
            if(b.incrementaPosto()){
                elencoBiglietti.remove(b.getCodice());
                b=null;// Elimina il riferimento all'istanza del biglietto
            }else{
                return false;
            }
        }
        if(elencoBiglietti.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public LinkedList<Biglietto> annullaPrenotazione(){
        LinkedList<Biglietto> bigliettiPrenotatiAnnullabili = new LinkedList<>();
        Iterator<Map.Entry<String,Biglietto>>iterator=elencoBiglietti.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Biglietto> entry=iterator.next();
            Biglietto b = entry.getValue();
            if(b.getBigliettiAnnullabili()){
                bigliettiPrenotatiAnnullabili.add(b);
            }
        }
        return bigliettiPrenotatiAnnullabili;
    }

    public boolean annullaBiglietto(String codice){
        Biglietto b = elencoBiglietti.get(codice);
        if(b!=null){
            if(b.incrementaPosto()){
                elencoBiglietti.remove(codice);
                b=null;// Elimina il riferimento all'istanza del biglietto
                return true;
            }else{
                return false;
            }
        }else {
            return true;
        }
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCF() {
        return CF;
    }

    public String getCodPersonale() {
        return codPersonale;
    }

    public LinkedHashMap<String,Biglietto> getElencoBiglietti() {
        return elencoBiglietti;
    }

}

