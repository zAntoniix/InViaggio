package dominio;

import java.time.*;
import java.util.Objects;

public class Biglietto {
    //Attributi
    private String codice;
    private float costoFinale;
    private Corsa corsaPrenotata;
    private String stato;

    // Stati:
    // Valido - biglietto non utilizzato per corsa non avvenuta
    // Annullato - biglietto annullato in seguito a sospensione/rimozione corse
    // Convalidato - biglietto usufruito
    // Scaduto - biglietto non utilizzato per corsa già avvenuta
    // Multato - biglietto scaduto dopo aver pagato la multa (regola di dominio R2)

    //Costruttore
    public Biglietto(String codice, float costoFinale, Corsa corsaPrenotata) {
        this.codice = codice;
        this.costoFinale = costoFinale;
        this.corsaPrenotata = corsaPrenotata;
        this.stato = "Valido";
    }

    //Metodi

    public boolean incrementaPosto(){
        int postiPrecedenti = corsaPrenotata.getNumPosti();
        corsaPrenotata.setNumPosti(corsaPrenotata.getNumPosti() + 1);
        if(corsaPrenotata.getNumPosti()>postiPrecedenti){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean getBigliettiAnnullabili() {
        LocalDateTime dataAttuale = LocalDateTime.now();
        LocalDateTime dataCorsa = corsaPrenotata.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalTime oraCorsa = corsaPrenotata.getOraPartenza().toLocalTime();
        LocalDateTime dataCompleta = dataCorsa.withHour(oraCorsa.getHour()).withMinute(oraCorsa.getMinute()).withSecond(oraCorsa.getSecond());
        Duration differenza = Duration.between(dataAttuale, dataCompleta);
        if(differenza.toHours() >= 24){
            return true;
        } else return false;
    }

    public boolean verificaBigliettoPerCorsa(Corsa c){
        if(getCorsaPrenotata().getCodCorsa().equals(c.getCodCorsa())){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isModificabile() {
        LocalDateTime dataModifica = LocalDateTime.now();
        LocalDateTime dataCorsa = corsaPrenotata.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalTime orario = corsaPrenotata.getOraPartenza().toLocalTime();
        LocalDateTime dataCorsaCompleta = dataCorsa.withHour(orario.getHour()).withMinute(orario.getMinute()).withSecond(orario.getSecond());
        Duration differenza = Duration.between(dataModifica, dataCorsaCompleta);
        if(differenza.toHours() > 12 && stato.equals("Valido")){
            return true;
        } else return false;
    }

    public void aggiornaBiglietto(float cf, Corsa c) {
        this.costoFinale = cf;
        this.corsaPrenotata = c;
    }

    public boolean isScaduto() {
        if(stato.equals("Scaduto")){
            return true;
        } else return false;
    }

    public boolean isValido() {
        if(stato.equals("Valido")){
            return true;
        } else return false;
    }

    public String getCodice(){
        return this.codice;
    }

    public void setCodice(String codice) { this.codice = codice; }

    public float getCostoFinale(){
        return this.costoFinale;
    }

    public void setCostoFinale(float costoFinale) {
        this.costoFinale = costoFinale;
    }

    public void setCorsaPrenotata(Corsa corsaPrenotata) {
        this.corsaPrenotata = corsaPrenotata;
    }

    public Corsa getCorsaPrenotata(){
        return this.corsaPrenotata;
    }

    public void setStato(String stato) { this.stato = stato; }

    public String getStato(){
        return this.stato;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Biglietto biglietto = (Biglietto) o;
        return Float.compare(costoFinale, biglietto.costoFinale) == 0 && Objects.equals(codice, biglietto.codice) && Objects.equals(corsaPrenotata, biglietto.corsaPrenotata) && Objects.equals(stato, biglietto.stato);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice, costoFinale, corsaPrenotata, stato);
    }
}
