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
    private String codiceCorsa;
    private float costoBase;
    private Time oraPartenza;
    private Time oraArrivo;

    //Costruttore

    public Corsa(int tipoMezzo, Date data, String luogoPartenza, String luogoArrivo,Time oraPartenza, Time oraArrivo, float costoBase) {
        this.tipoMezzo = tipoMezzo;
        this.data = data;
        this.luogoPartenza = luogoPartenza;
        this.luogoArrivo = luogoArrivo;
        this.oraPartenza = oraPartenza;
        this.oraArrivo = oraArrivo;
        this.numPosti = setPosti(tipoMezzo);
        this.costoBase = costoBase;
        this.codiceCorsa = generaCodCorsa();
    }

    //Metodi
    public int setPosti(int tipoMezzo){ //Controllo del tipoEffettuato precedentemente
        if(tipoMezzo == 1)//Autobus
            return 52;
        else
            return 100;

    }

    public String generaCodCorsa() {
        String caratteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int Lunghezza_Stringa = 5;
        Random rand = new Random();
        StringBuilder stringaCasuale =  new StringBuilder(Lunghezza_Stringa);

        for (int i = 0; i < Lunghezza_Stringa; i++) {
            int index = rand.nextInt(caratteri.length());
            stringaCasuale.append(caratteri.charAt(index));
        }
        return stringaCasuale.toString();
    }

    public float getCostoBase() {
        return costoBase;
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

}
