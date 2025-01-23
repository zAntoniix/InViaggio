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
        String caratteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int Lunghezza_Stringa = 6;
        Random rand = new Random();
        StringBuilder stringaCasuale =  new StringBuilder(Lunghezza_Stringa);

        for (int i = 0; i < Lunghezza_Stringa; i++) {
            int index = rand.nextInt(caratteri.length());
            stringaCasuale.append(caratteri.charAt(index));
        }
        return stringaCasuale.toString();
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
        String caratteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int Lunghezza_Stringa = 4;
        Random rand = new Random();
        StringBuilder stringaCasuale =  new StringBuilder(Lunghezza_Stringa);

        for (int i = 0; i < Lunghezza_Stringa; i++) {
            int index = rand.nextInt(caratteri.length());
            stringaCasuale.append(caratteri.charAt(index));
        }
        return stringaCasuale.toString();
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
        bigliettoCorrente.getCorsaPrenotata().decrementaPosti();
        return clienteLoggato.confermaBiglietto(bigliettoCorrente);
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
