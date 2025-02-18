package dominio;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Cliente implements Observer{

    //Attributi
    private String nome;
    private String cognome;
    private String CF;
    private String codPersonale;
    private LinkedHashMap<String,Biglietto> elencoBiglietti;
    private LinkedList<Tratta> elencoTratte;
    private boolean notifica;
    private String messaggio;

    //Costruttore
    public Cliente(String nome, String cognome, String CF, String codPersonale) {
        this.nome = nome;
        this.cognome = cognome;
        this.CF = CF;
        this.codPersonale = codPersonale;
        this.elencoBiglietti = new LinkedHashMap<String,Biglietto>();
        this.elencoTratte = new LinkedList<>();
        this.notifica = false;
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

    public boolean annullaBigliettoPerSospensione(List<Corsa> listaCorse){
        boolean checkBiglietto;
        for(Corsa c : listaCorse) {
            Iterator<Map.Entry<String, Biglietto>> iterator = elencoBiglietti.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Biglietto> entry = iterator.next();
                Biglietto b = entry.getValue();
                checkBiglietto = b.verificaBigliettoPerCorsa(c);
                if (checkBiglietto) {
                    b.setStato("Annullato");
                }
            }
        }
        return true;
    }

    public void iscrizioneNotifiche(Tratta trattaDaOsservare){
        trattaDaOsservare.addObserver(this);
    }

    public void resetNotifica() {
        messaggio = " ";
        notifica = false;
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

    public LinkedList<Biglietto> getBigliettiModificabili() {
        LinkedList<Biglietto> bigliettiModificabili = new LinkedList<>();
        Iterator<Map.Entry<String,Biglietto>>iterator=elencoBiglietti.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Biglietto> entry=iterator.next();
            //String codice=entry.getKey();
            Biglietto b = entry.getValue();
            if(b.isModificabile()) {
                bigliettiModificabili.add(b);
            }
        }
        return bigliettiModificabili;
    }

    public Biglietto selezionaBiglietto(String codBiglietto) {
        return elencoBiglietti.get(codBiglietto);
    }

    public void checkStatoBiglietti() {
        Iterator<Map.Entry<String,Biglietto>>iterator=elencoBiglietti.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, Biglietto> entry = iterator.next();
            //String codice=entry.getKey();
            Biglietto b = entry.getValue();
            LocalDateTime dataCorsa = b.getCorsaPrenotata().getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime dataOperazione = LocalDateTime.now();
            if (dataCorsa.isBefore(dataOperazione)) {
                b.setStato("Scaduto");
            }
        }
    }

    public boolean controllaBigliettiScaduti() {
        boolean esito = false;
        Iterator<Map.Entry<String,Biglietto>>iterator=elencoBiglietti.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, Biglietto> entry = iterator.next();
            //String codice=entry.getKey();
            Biglietto b = entry.getValue();
            if(b.checkBigliettoScaduto()) {
                esito = true;
            }
        }
        return esito;
    }

    public void aggiornaStatoBigliettiScaduti() {
        Iterator<Map.Entry<String,Biglietto>>iterator=elencoBiglietti.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, Biglietto> entry = iterator.next();
            //String codice=entry.getKey();
            Biglietto b = entry.getValue();
            if(b.checkBigliettoScaduto()) {
                b.setStato("Multato");
            }
        }
    }

    public String getNome() { return nome; }

    public String getCF() { return CF; }

    public String getCodPersonale() { return codPersonale; }

    public LinkedHashMap<String,Biglietto> getElencoBiglietti() { return elencoBiglietti; }

    public boolean getNotifica() { return notifica; }

    public void setNotifica(boolean val) { notifica = val; }

    public String getMessaggio() { return messaggio; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nome, cliente.nome) &&
                Objects.equals(cognome, cliente.cognome) &&
                Objects.equals(CF, cliente.CF) &&
                Objects.equals(codPersonale, cliente.codPersonale) &&
                Objects.equals(elencoBiglietti, cliente.elencoBiglietti);
    }

    @Override
    public void update(Observable o) {
        Tratta trattaDaOsservare = (Tratta) o;
        LinkedList<Corsa> listaCorse = trattaDaOsservare.getListaCorse();
        LinkedList<Corsa> listaCorseEliminate = trattaDaOsservare.getElencoCorseDaSospendere();
        LinkedList<Biglietto> listaBiglietti= this.getListaBiglietti();
        int contatore = 0;
        for(Corsa c : listaCorse){
            for(Biglietto b : listaBiglietti){
                if(c.getCodCorsa().equals(b.getCorsaPrenotata().getCodCorsa())){
                       contatore++;
                }
            }
        }
        messaggio=("Per la tratta "+ trattaDaOsservare.getCittaPartenza() + " " + trattaDaOsservare.getCittaArrivo() + " sono state eliminate le seguenti corse: " );
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoOra = new SimpleDateFormat("HH:mm");
        for(Corsa c : listaCorseEliminate){
            messaggio += "\n";
            messaggio += formatoData.format(c.getData());
            messaggio +=" ";
            messaggio +=" ora partenza ";
            messaggio += formatoOra.format(c.getOraPartenza());
            messaggio +=" ora arrivo ";
            messaggio += formatoOra.format(c.getOraArrivo());
        }
        setNotifica(true);
    }
}

