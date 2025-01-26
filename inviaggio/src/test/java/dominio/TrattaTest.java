package dominio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
class TrattaTest {
    static InViaggio inviaggio;
    static Tratta t;
    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    static Date data1;
    static Date data2;

    static {
        try {
            data1 = formatter.parse("24/04/2024");
            data2 = formatter.parse("04/04/2024");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    public static void setUp() throws Exception {
        inviaggio = InViaggio.getInstance();
        t = new Tratta( 1,"Catania","Milano", "T1");
    }

    @Test
    void inserisciCorsa() {
        int dimensioneIniziale= t.getElencoCorse().size();
        t.inserisciCorsa(1,data1,"stazione Centrale","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("22:20:00"),25); //avrà codice C1
        //ci aspettiamo che la dimensione della mappa elencoCorse sia aumentata di 1
        assertEquals(dimensioneIniziale+1,t.getElencoCorse().size());

    }

    @Test
    void generaCodiceCorsa(){
        assertEquals("C2",t.generaCodiceCorsa());
    }

    @Test
    void getCorsePerData(){
        List<Corsa> c=new LinkedList<>();
        t.inserisciCorsa(1,data2,"stazione Centrale","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("22:20:00"),25); //avrà codice C2
        t.inserisciCorsa(1,data2,"Aereoporto","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("23:20:00"),25); //avrà codice C3
        c=t.getCorsePerData(data2);
        assertEquals(c,t.getCorsePerData(data2));
    }

    @Test
    void selezionaCorsa() {
        assertInstanceOf(Corsa.class, t.selezionaCorsa("C1"));
    }
}