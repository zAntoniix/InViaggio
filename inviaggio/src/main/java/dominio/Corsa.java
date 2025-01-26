package dominio;
import java.sql.Time;
import java.util.*;


public class Corsa {

    //Attributi
    private int tipoMezzo;
    private Date data;
    private int numPosti;
    private String luogoPartenza;
    private String luogoArrivo;
    private String codCorsa;
    private float costoBase;
    private Time oraPartenza;
    private Time oraArrivo;

    //Costruttore

    public Corsa(int tipoMezzo, Date data, String luogoPartenza, String luogoArrivo,Time oraPartenza, Time oraArrivo, float costoBase, String codice) {
        this.tipoMezzo = tipoMezzo;
        this.data = data;
        this.luogoPartenza = luogoPartenza;
        this.luogoArrivo = luogoArrivo;
        this.oraPartenza = oraPartenza;
        this.oraArrivo = oraArrivo;
        this.numPosti = setPosti(tipoMezzo);
        this.costoBase = costoBase;
        this.codCorsa = codice;
    }

    //Metodi
    public int setPosti(int tipoMezzo){ //Controllo del tipoEffettuato precedentemente
        if(tipoMezzo == 1)//Autobus
            return 52;
        else // tipoMezzo == 2 (Treno)
            return 100;
    }


    public Boolean isDisponibileData(Date data_ricercata){
        if(this.data.equals(data_ricercata))
            return true;
        else
            return false;
    }

    public Boolean decrementaPosti(){
        if(this.numPosti>=1){
            this.numPosti--;
            return true;
        }
        else
            return false;
    }

    public float getCostoBase() { return costoBase; }

    public String getCodCorsa() { return codCorsa; }

    public String getLuogoPartenza() { return luogoPartenza; }

    public String getLuogoArrivo() { return luogoArrivo; }

    public Time getOraPartenza() { return oraPartenza; }

    public Time getOraArrivo() { return oraArrivo; }

    public Date getData() { return data; }

    public void setNumPosti(int numPosti) { this.numPosti = numPosti; }

    public int getNumPosti() {
        return numPosti;
    }

    public int getTipoMezzo() {return tipoMezzo;}
}
