package dominio;

import java.util.*;

public class InViaggio {

    //Attributi
    private static InViaggio inviaggio;
    private HashMap<String,Tratta> elencoTratte;
    private List<Cliente> elencoClienti;




    //Costruttore
    public InViaggio() {
        this.elencoTratte = new HashMap<>();
        this.elencoClienti = new ArrayList<>();
    }

    //Metodi
    public static InViaggio getInstance() {
        if(inviaggio == null)
            inviaggio = new InViaggio();
        return inviaggio;
    }
}
