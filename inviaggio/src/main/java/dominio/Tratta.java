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
        if(verificaEsistenzaCorsa(data,oraPartenza,oraArrivo,luogoPartenza,luogoArrivo)) {
            Corsa c = new Corsa(tipoMezzo, data, luogoPartenza, luogoArrivo, oraPartenza, oraArrivo, costoBase, codiceCorsa);
            if (this.elencoCorse.put(c.getCodCorsa(), c) == null) {
                return true;
            } else {
                return false;
            }
        } else{
            return false;
        }
    }

    public boolean verificaEsistenzaCorsa(Date data,Time oraPartenza,Time oraArrivo,String luogoPartenza,String luogoArrivo) {
        Iterator<Map.Entry<String,Corsa>>iterator=elencoCorse.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Corsa> entry=iterator.next();
            Corsa c=entry.getValue();
            if(c.getData().equals(data) && c.getOraPartenza().equals(oraPartenza) && c.getOraArrivo().equals(oraArrivo) && c.getLuogoPartenza().equals(luogoPartenza) && c.getLuogoArrivo().equals(luogoArrivo) ){
                return false;
            }
        }
        return true;
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


    public LinkedList<Corsa> sospensioneCorse(Date dataInizio,Date dataFine){
        LinkedList<Corsa> elencoCorseDaSospendere = new LinkedList<>();
        Iterator<Map.Entry<String,Corsa>>iterator=elencoCorse.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Corsa> entry=iterator.next();
            Corsa c=entry.getValue();
            if(c.getCorsePerPeriodo(dataInizio,dataFine)) {
                elencoCorseDaSospendere.add(c);
            }
        }
        return elencoCorseDaSospendere;
    }

    public boolean eliminaCorsePerSospensione(List<Corsa> elencoCorseDaSospendere){
        for(Corsa c : elencoCorseDaSospendere){
            if(elencoCorse.remove(c.getCodCorsa()) == null){
                return false;
            }
        }
        return true;
    }

    public LinkedList<Corsa> getListaCorse(){
        LinkedList<Corsa> corseList = new LinkedList<>();
        Iterator<Map.Entry<String,Corsa>>iterator=elencoCorse.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Corsa> entry=iterator.next();
            Corsa c=entry.getValue();
            corseList.add(c);
        }
        return corseList;
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

    public LinkedHashMap<String, Corsa> getElencoCorse() {return elencoCorse;}

    public int getTipoTratta() { return tipoTratta; }

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