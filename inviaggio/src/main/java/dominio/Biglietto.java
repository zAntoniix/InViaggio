package dominio;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
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
        //Controllo Mese
        if (dataAttuale.getMonth() == dataCorsa.getMonth()) {
            //Controllo giorno
            if (dataCorsa.getDayOfMonth() - dataAttuale.getDayOfMonth() == 1) {
                //Conrtrollo Ora nel caso giorno prima
                if (dataAttuale.toLocalTime().compareTo(oraCorsa) < 0) {
                    //Controllo minuti
                    if(dataAttuale.toLocalTime().getMinute() <= oraCorsa.getMinute()){
                        return true;
                    }else{
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
        if(dataAttuale.getMonth().getValue() < dataCorsa.getMonth().getValue()){
            return true;
        }
        else{
            return false;
        }

    }

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
