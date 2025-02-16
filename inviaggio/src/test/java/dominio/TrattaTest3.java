package dominio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class TrattaTest3 {
    static Tratta t;
    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    static Date data1, data2, data3, inizio, fine;
    static LinkedList<Corsa> listaExpected;

    static {
        try {
            data1 = formatter.parse("18/03/2025");
            data2 = formatter.parse("04/04/2025");
            data3 = formatter.parse("13/02/2025");

            inizio = formatter.parse("10/03/2025");
            fine = formatter.parse("20/04/2025");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    public static void setUp() throws Exception {
        t = new Tratta( 1,"Catania","Milano", "T1");
        t.inserisciCorsa(1,data1,"stazione Centrale","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("22:20:00"),25);
        t.inserisciCorsa(1,data2,"Aereoporto","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("23:20:00"),25);
        t.inserisciCorsa(1, data3, "Aereoporto","Librino", Time.valueOf("12:30:00"),Time.valueOf("13:00:00"),12);

        listaExpected = new LinkedList<>();
        listaExpected.add(new Corsa(1,data1,"stazione Centrale","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("22:20:00"),25, "C1T1"));
        listaExpected.add(new Corsa(1,data2,"Aereoporto","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("23:20:00"),25, "C2T1"));
    }

    @Test
    void testSospensioneCorse() {
        assertEquals(listaExpected, t.sospensioneCorse(inizio, fine));
    }

    @Test
    void testEliminaCorsePerSospensione() {
        assertTrue(t.eliminaCorsePerSospensione(listaExpected));
        assertEquals(1, t.getElencoCorse().size()); //ci aspettiamo che rimanga una sola corsa, con data = data3
    }
}