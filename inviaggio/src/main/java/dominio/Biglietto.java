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
        LocalDate giornoSuccessivo = dataAttuale.toLocalDate().plusDays(1);
    if(dataAttuale.getYear() == dataCorsa.getYear() || giornoSuccessivo.getYear() == dataCorsa.getYear()){
        //Controllo Mese
        if (dataAttuale.getMonth() == dataCorsa.getMonth() || giornoSuccessivo.getMonth() == dataCorsa.getMonth()) {
            //Controllo giorno
            if (dataCorsa.getDayOfMonth() - dataAttuale.getDayOfMonth() >= 1 || dataCorsa.getDayOfMonth() - dataAttuale.getDayOfMonth() < -26) {
                //Controllo Ora nel caso giorno prima
                if (dataAttuale.toLocalTime().compareTo(oraCorsa) < 0) {
                    //Controllo minuti
                    if (dataAttuale.toLocalTime().getMinute() <= oraCorsa.getMinute()) {
                        return true;
                    } else {
                        return false;
                    }
                    //Fine controllo minuti
                } else {
                    return false;
                }
                //Fine controllo ore
            }
            if (dataAttuale.getDayOfMonth() - dataCorsa.getDayOfMonth() > 1) {
                return true;
            } else {
                return false;
            }
        }
        if (dataAttuale.getMonth().getValue() < dataCorsa.getMonth().getValue()) {
            return true;
        } else {
            return false;
        }

        } if(dataAttuale.getYear() < dataCorsa.getYear()){
        return true;
        }
        else{
            return false;
        }
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
        LocalTime oraModifica = LocalTime.now();
        LocalTime oraPartenza = corsaPrenotata.getOraPartenza().toLocalTime();
        Duration duration = Duration.between(oraModifica, oraPartenza);
        if(duration.abs().toHours() > 12) {
            return true;
        } else
            return false;
    }

    public void aggiornaBiglietto(float cf, Corsa c) {
        this.costoFinale = cf;
        this.corsaPrenotata = c;
    }

    public boolean checkBigliettoScaduto() {
        if(stato.equals("Scaduto")){
            return true;
        } else return false;
    }

    public String getCodice(){
        return this.codice;
    }

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Biglietto biglietto = (Biglietto) o;
        return Float.compare(costoFinale, biglietto.costoFinale) == 0 &&
                Objects.equals(codice, biglietto.codice) &&
                Objects.equals(corsaPrenotata, biglietto.corsaPrenotata);
    }

}
