package dominio;

public class Biglietto {
    //Attributi
    private String codice;
    private float costoFinale;
    private Corsa corsaPrenotata;

    //Costruttore
    public Biglietto(String codice, float costoFinale, Corsa corsaPrenotata) {
        this.codice = codice;
        this.costoFinale = costoFinale;
        this.corsaPrenotata = corsaPrenotata;
    }

    //Metodi
    public String getCodice(){
        return this.codice;
    }

    public Corsa getCorsaPrenotata(){
        return this.corsaPrenotata;
    }
}
