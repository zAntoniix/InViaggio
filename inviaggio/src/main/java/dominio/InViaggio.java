package dominio;

import java.sql.Time;
import java.util.*;

public class InViaggio {

    //Attributi
    private static InViaggio inviaggio;
    private HashMap<String,Tratta> elencoTratte;
    private List<Cliente> elencoClienti;

    private Biglietto bigliettoCorrente;
    private Tratta trattaCorrente;
    private Tratta trattaSelezionata;
    private Cliente clienteLoggato;




    //Costruttore
    public InViaggio() {
        elencoTratte = new HashMap<>();
        elencoClienti = new ArrayList<>();
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
        String cod = "T"+elencoTratte.size()+1;
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
        if(trattaCorrente.inserisciCorsa(tipoMezzo, data, luogoPartenza, luogoArrivo, oraPartenza, oraArrivo, costoBase)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean confermaInserimento() {
        String codTratta = trattaCorrente.getCodTratta();
        if(elencoTratte.put(codTratta, trattaCorrente) != null) {
            trattaCorrente = null;
            return true;
        } else {
            return false;
        }
    }

    public HashMap<String, Tratta> prenotaBiglietto() {
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
        String codBiglietto = "B" + clienteLoggato.getElencoBiglietti().size() +1;
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

    public Cliente getClienteLoggato(){
        return clienteLoggato;
    }

    public void addTratta(Tratta t){
        this.elencoTratte.put(t.getCodTratta(),t);
    }

    public void setBigliettoCorrente(Biglietto b) {
        this.bigliettoCorrente = b;
    }

    public Biglietto getBigliettoCorrente() {
        return bigliettoCorrente;
    }
}
