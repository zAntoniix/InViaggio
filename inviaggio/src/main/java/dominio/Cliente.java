package dominio;
import java.util.*;

public class Cliente implements Observer{

    //Attributi
    private String nome;
    private String cognome;
    private String CF;
    private String codPersonale;
    private LinkedHashMap<String,Biglietto> elencoBiglietti;
    private LinkedList<Tratta> elencoTratte;

    //Costruttore
    public Cliente(String nome, String cognome, String CF, String codPersonale) {
        this.nome = nome;
        this.cognome = cognome;
        this.CF = CF;
        this.codPersonale = codPersonale;
        this.elencoBiglietti = new LinkedHashMap<String,Biglietto>();
        this.elencoTratte = new LinkedList<>();
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
        Iterator<Map.Entry<String,Biglietto>>iterator=elencoBiglietti.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Biglietto> entry=iterator.next();
            Biglietto b = entry.getValue();
            if(b.incrementaPosto()){
                iterator.remove();
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

    public boolean annullaBigliettoPerSospensione(List<Corsa> listaCorsa){
        boolean checkBiglietto;
        for(Corsa c : listaCorsa) {
            Iterator<Map.Entry<String, Biglietto>> iterator = elencoBiglietti.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Biglietto> entry = iterator.next();
                Biglietto b = entry.getValue();
                checkBiglietto = b.verificaBigliettoPerCorsa(c);
                if (checkBiglietto) {
                    iterator.remove();
                    b = null;
                }
            }
        }
        return true;
    }

    public void iscrizioneNotifiche(Tratta trattaDaOsservare){
        trattaDaOsservare.addObserver(this);
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

    public LinkedList<Biglietto> getListaBiglietti(){
        LinkedList<Biglietto> listaBiglietto = new LinkedList<>();
        Iterator<Map.Entry<String,Biglietto>>iterator=elencoBiglietti.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Biglietto> entry=iterator.next();
            Biglietto b=entry.getValue();
            listaBiglietto.add(b);
        }
        return listaBiglietto;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nome, cliente.nome) && Objects.equals(cognome, cliente.cognome) && Objects.equals(CF, cliente.CF) && Objects.equals(codPersonale, cliente.codPersonale) && Objects.equals(elencoBiglietti, cliente.elencoBiglietti);
    }


    @Override
    public void update(Observable o) {
        Tratta trattaDaOsservare = (Tratta) o;
        LinkedList<Corsa> listaCorse = trattaDaOsservare.getListaCorse();
        LinkedList<Biglietto> listaBiglietti= this.getListaBiglietti();
        int contatore = 0;
        for(Corsa c : listaCorse){
            for(Biglietto b : listaBiglietti){
                if(c.getCodCorsa().equals(b.getCorsaPrenotata().getCodCorsa())){
                       contatore++;
                }
            }
        }
        if(contatore==0){
            trattaDaOsservare.deleteObserver(this);
        }

    }


}

