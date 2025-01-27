package dominio;

import org.junit.jupiter.api.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TrattaTest {
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
        t = new Tratta( 1,"Catania","Milano", "T1");
    }

    @Test
    @Order (1)
    void testInserisciCorsa() {
        int dimensioneIniziale= t.getElencoCorse().size();
        t.inserisciCorsa(1,data1,"stazione Centrale","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("22:20:00"),25); //avrà codice C1
        //ci aspettiamo che la dimensione della mappa elencoCorse sia aumentata di 1
        assertEquals(dimensioneIniziale+1,t.getElencoCorse().size());
    }

    @Test
    @Order (2)
    void testGeneraCodiceCorsa(){
        assertEquals("C2",t.generaCodiceCorsa());
    }

    @Test
    @Order (3)
    void testGetCorsePerData(){
        LinkedList<Corsa> c = new LinkedList<>();
        t.inserisciCorsa(1,data2,"stazione Centrale","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("22:20:00"),25);
        t.inserisciCorsa(1,data2,"Aereoporto","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("23:20:00"),25);
        Corsa c1 = new Corsa(1,data2,"stazione Centrale","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("22:20:00"),25, "C2");
        Corsa c2 = new Corsa(1,data2,"Aereoporto","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("23:20:00"),25, "C3");
        c.add(c1);
        c.add(c2);
        LinkedList<Corsa> l2 = t.getCorsePerData(data2);
        assertTrue(c.equals(l2));
    }

    @Test
    @Order (4)
    void testSelezionaCorsa() {
        assertInstanceOf(Corsa.class, t.selezionaCorsa("C1"));
    }
}