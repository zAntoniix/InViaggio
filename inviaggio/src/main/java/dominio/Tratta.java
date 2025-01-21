package dominio;
import java.util.Random;

public class Tratta {
    //Attributi
    private int tipoTratta;
    private String cittaPartenza;
    private String cittaArrivo;
    private String codTratta;

    //Costruttore
    public Tratta(int tipoTratta, String cittaPartenza, String cittaArrivo, String codTratta) {
        this.tipoTratta = tipoTratta;
        this.cittaPartenza = cittaPartenza;
        this.cittaArrivo = cittaArrivo;
        this.codTratta = codTratta;
    }

    //Metodi
    public String getCodTratta() {
        return codTratta;
    }

}