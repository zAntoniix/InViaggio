package dominio;
import java.util.Date;
import java.util.*;
import java.sql.*;


public class Tratta {
    //Attributi
    private int tipoTratta;
    private String cittaPartenza;
    private String cittaArrivo;
    private String codTratta;
    private HashMap<String,Corsa> elencoCorse;

    //Costruttore
    public Tratta(int tipoTratta, String cittaPartenza, String cittaArrivo, String codTratta) {
        this.tipoTratta = tipoTratta;
        this.cittaPartenza = cittaPartenza;
        this.cittaArrivo = cittaArrivo;
        this.codTratta = codTratta;
        this.elencoCorse = new HashMap<>();
    }

    //Metodi
    public String getCodTratta() {
        return codTratta;
    }

    public Boolean inserisciCorsa(int tipoMezzo, Date data, String luogoPartenza, String luogoArrivo, Time oraArrivo, Time oraPartenza, float costoBase) {

        Corsa c = new Corsa(tipoMezzo,data,luogoPartenza,luogoArrivo,oraPartenza,oraArrivo,costoBase);
        if(c!=null)
            return true;
        else
            return false;
    }

    public List<Corsa> getCorsePerData(Date data){
        List<Corsa> corseDisponibili = new ArrayList<>();
        Iterator<Map.Entry<String,Corsa>>iterator=elencoCorse.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Corsa> entry=iterator.next();
            //String codice=entry.getKey();
            Corsa c=entry.getValue();
            if(c.isDisponibileData(data)) {
                corseDisponibili.add(c);
            }
        }
        return corseDisponibili;
    }

    public Corsa selezionaCorsa(String codCorsa) {
        return elencoCorse.get(codCorsa);
    }

    /*public Boolean aggiornaInformazioni(String codCorsa){
        Corsa c = selezionaCorsa(codCorsa);
        return c.decrementaPosti();
    }*/

}