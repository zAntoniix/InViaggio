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
    private LinkedHashMap<String,Corsa> elencoCorse;

    //Costruttore
    public Tratta(int tipoTratta, String cittaPartenza, String cittaArrivo, String codTratta) {
        this.tipoTratta = tipoTratta;
        this.cittaPartenza = cittaPartenza;
        this.cittaArrivo = cittaArrivo;
        this.codTratta = codTratta;
        this.elencoCorse = new LinkedHashMap<>();
    }

    //Metodi
    public String getCodTratta() {
        return codTratta;
    }

    public String generaCodiceCorsa() {
        String codCorsa = "C" + (elencoCorse.size()+1);
        return codCorsa;
    }

    public Boolean inserisciCorsa(int tipoMezzo, Date data, String luogoPartenza, String luogoArrivo, Time oraPartenza, Time oraArrivo, float costoBase) {
        String codiceCorsa = generaCodiceCorsa();
        Corsa c = new Corsa(tipoMezzo,data,luogoPartenza,luogoArrivo,oraPartenza,oraArrivo,costoBase,codiceCorsa);
        if (this.elencoCorse.put(c.getCodCorsa(), c) == null) {
            return true;
        } else {
            return false;
        }
    }

    public LinkedList<Corsa> getCorsePerData(Date data){
        LinkedList<Corsa> corseDisponibili = new LinkedList<>();
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

    public String getCittaPartenza(){
        return cittaPartenza;
    }

    public String getCittaArrivo(){
        return cittaArrivo;
    }

    public HashMap<String, Corsa> getElencoCorse() {return elencoCorse;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tratta tratta = (Tratta) o;
        return tipoTratta == tratta.tipoTratta &&
                Objects.equals(cittaPartenza, tratta.cittaPartenza) &&
                Objects.equals(cittaArrivo, tratta.cittaArrivo) &&
                Objects.equals(codTratta, tratta.codTratta) &&
                Objects.equals(elencoCorse, tratta.elencoCorse);
    }
}