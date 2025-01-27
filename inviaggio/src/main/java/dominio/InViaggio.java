package dominio;

import java.sql.Time;
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

    public List<Corsa> richiediCorsePerData(Date data) {
        return trattaSelezionata.getCorsePerData(data);
    }

    public String generaCodBiglietto() {
        String codBiglietto = "B" + (clienteLoggato.getElencoBiglietti().size()+1);
        return codBiglietto;
    }

    public Biglietto selezionaCorsa(String codCorsa) {
        Corsa c;
        float cb;
        String cod;
        c = trattaSelezionata.selezionaCorsa(codCorsa);
        cb = c.getCostoBase();
        cod = generaCodBiglietto();
        bigliettoCorrente = new Biglietto(cod, cb, c);
        return bigliettoCorrente;
    }

    public boolean confermaBiglietto() {
        if (bigliettoCorrente.getCorsaPrenotata().decrementaPosti()) {
            return clienteLoggato.confermaBiglietto(bigliettoCorrente);
        } else return false;
    }

    public void addTratta(Tratta t){

        this.elencoTratte.put(t.getCodTratta(),t);
    }

    public Cliente getClienteLoggato(){
        return clienteLoggato;
    }

    public void setBigliettoCorrente(Biglietto b) {
        this.bigliettoCorrente = b;
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
}
