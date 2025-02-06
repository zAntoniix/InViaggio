package dominio;

import dominio.scontistica.LastMinute;
import dominio.scontistica.PrezzoDomenica;
import dominio.scontistica.PrezzoFinale;

import java.sql.Time;
import java.time.*;
import java.util.*;

public class InViaggio {

    //Attributi
    private static InViaggio inviaggio;
    private LinkedHashMap<String,Tratta> elencoTratte;
    private LinkedList<Cliente> elencoClienti;

    private Biglietto bigliettoCorrente;
    private Tratta trattaCorrente;
    private Tratta trattaSelezionata;
    private Cliente clienteLoggato;
    private PrezzoFinale prezzoFinale;

    //Costruttore
    public InViaggio() {
        elencoTratte = new LinkedHashMap<>();
        elencoClienti = new LinkedList<>();
    }

    //Metodi
    public static InViaggio getInstance() {
        if(inviaggio == null)
            inviaggio = new InViaggio();
        return inviaggio;
    }

    public void setClienteLoggato(Cliente cliente) {
        clienteLoggato = cliente;
    }

    public String generaCodTratta() {
        String cod = "T" + (elencoTratte.size()+1);
        return cod;
    }

    public boolean inserisciNuovaTratta(int tipoTratta, String cittaPartenza, String cittaArrivo) {
        String codTratta = generaCodTratta();
        Iterator<Map.Entry<String,Tratta>>iterator=elencoTratte.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Tratta> entry=iterator.next();
            Tratta t = entry.getValue();
            if(t.getCittaPartenza().equals(cittaPartenza) && t.getCittaArrivo().equals(cittaArrivo)){
                return false;
            }
        }
        if(tipoTratta == 2 && cittaPartenza.equals(cittaArrivo)){
            return false;
        }
        if(tipoTratta == 1 && !cittaPartenza.equals(cittaArrivo)) {
            return false;
        }
        trattaCorrente = new Tratta(tipoTratta, cittaPartenza, cittaArrivo, codTratta);
        if(trattaCorrente != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean inserisciCorsa (int tipoMezzo, Date data, String luogoPartenza, String luogoArrivo, Time oraPartenza, Time oraArrivo, float costoBase) {
        return trattaCorrente.inserisciCorsa(tipoMezzo, data, luogoPartenza, luogoArrivo, oraPartenza, oraArrivo, costoBase);
    }


    public boolean confermaInserimento() {
        String codTratta = trattaCorrente.getCodTratta();
        if(elencoTratte.put(codTratta, trattaCorrente) == null) {
            trattaCorrente = null;
            return true;
        } else {
            return false;
        }
    }

    public LinkedHashMap<String, Tratta> prenotaBiglietto() {
        trattaSelezionata = null;
        return elencoTratte;
    }

    public Tratta selezionaTratta(String codTratta) {
        trattaSelezionata = elencoTratte.get(codTratta);
        return trattaSelezionata;
    }

    public LinkedList<Corsa> richiediCorsePerData(Date data) {
        return trattaSelezionata.getCorsePerData(data);
    }

    public String generaCodBiglietto() {
        String codBiglietto = "B" + (clienteLoggato.getElencoBiglietti().size()+1);
        return codBiglietto;
    }

    public Biglietto selezionaCorsa(String codCorsa) {
        Corsa c;
        float cb,cf;
        String cod;
        c = trattaSelezionata.selezionaCorsa(codCorsa);
        cod = generaCodBiglietto();
        LocalDateTime dataPrenotazione = c.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalTime oraCorrente = LocalTime.now();
        LocalTime oraPartenza = c.getOraPartenza().toLocalTime();
        Duration differenzaTempo = Duration.between(oraCorrente, oraPartenza);
        cb=c.getCostoBase();
        cf=cb;

        if(differenzaTempo.toHours()==12){
            prezzoFinale=new LastMinute();
            cf= cf-prezzoFinale.calcolaPrezzo(c);
        }
        if(dataPrenotazione.getDayOfWeek().getValue() == 7) {
            prezzoFinale = new PrezzoDomenica();
            cf= cf+prezzoFinale.calcolaPrezzo(c);
        }
        bigliettoCorrente = new Biglietto(cod, cf, c);
        return bigliettoCorrente;
    }

    public boolean confermaBiglietto() {
        if (bigliettoCorrente.getCorsaPrenotata().decrementaPosti()) {
            return clienteLoggato.confermaBiglietto(bigliettoCorrente);
        } else
            return false;
    }

    public boolean inserisciNuovaCorsa(String codTratta){
        trattaCorrente=null;
        trattaCorrente=elencoTratte.get(codTratta);

        if(trattaCorrente!=null){
            return true;
        }
        else{
            return false;
        }

    }

    public boolean registrati(String nome, String cognome, String CF, String codicePersonale) {
        for(Cliente c : elencoClienti){
            if(c.getCF().equals(CF)){
                return false;
            }
        }
        Cliente cl = new Cliente(nome,cognome,CF,codicePersonale);
        if(elencoClienti.add(cl)){
            setClienteLoggato(cl);
            return true;
        }else{
            return false;
        }
    }

    public Cliente verificaCliente(String CF, String codPersonale){
        Cliente cl = null;
        for(Cliente c : elencoClienti){
            if(c.getCodPersonale().equals(codPersonale) && c.getCF().equals(CF)){
                cl = c;
            }
        }
        return cl;
    }

    public boolean accedi(String CF, String codPersonale) {
        Cliente cl;
        cl = verificaCliente(CF, codPersonale);
        if(cl!=null){
            setClienteLoggato(cl);
            return true;
        }else{
            return false;
        }
    }

    public boolean rimuovi(String CF, String codPersonale) {
        Cliente cl;
        cl = verificaCliente(CF, codPersonale);
        if(cl!=null){
            if(cl.rimuoviAccount()){
                elencoClienti.remove(cl);
                setClienteLoggato(null);
                cl=null;
                return true;
            } else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public void logout(){
        setClienteLoggato(null);
    }

    public List<Biglietto> annullaPrenotazione(){
        return clienteLoggato.annullaPrenotazione();
    }

    public boolean selezionaBigliettoDaAnnullare(String codice){
        if(clienteLoggato.annullaBiglietto(codice)){
            return true;
        }
        else{
            return false;
        }
    }

    public void addTratta(Tratta t){
        this.elencoTratte.put(t.getCodTratta(),t);
    }

    public Cliente getClienteLoggato(){
        return clienteLoggato;
    }

    public Biglietto getBigliettoCorrente() { return bigliettoCorrente; }

    public LinkedHashMap<String, Tratta> getElencoTratte() { return elencoTratte; }

    public LinkedList<Cliente> getElencoClienti() {
        return elencoClienti;
    }

    public Tratta getTrattaCorrente() {
        return trattaCorrente;
    }

    public Tratta getTrattaSelezionata() {
        return trattaSelezionata;
    }

    public void setBigliettoCorrente(Biglietto b) {
        this.bigliettoCorrente = b;
    }

    public void setTrattaCorrente(Tratta trattaCorrente) {this.trattaCorrente = trattaCorrente; }

    public void setTrattaSelezionata(Tratta trattaSelezionata) {this.trattaSelezionata = trattaSelezionata; }

}
