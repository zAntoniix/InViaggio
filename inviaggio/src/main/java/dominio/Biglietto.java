package dominio;

import java.util.Objects;

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

    public float getCostoFinale(){
        return this.costoFinale;
    }

    public Corsa getCorsaPrenotata(){
        return this.corsaPrenotata;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Biglietto biglietto = (Biglietto) o;
        return Float.compare(costoFinale, biglietto.costoFinale) == 0 &&
                Objects.equals(codice, biglietto.codice) &&
                Objects.equals(corsaPrenotata, biglietto.corsaPrenotata);
    }
}
