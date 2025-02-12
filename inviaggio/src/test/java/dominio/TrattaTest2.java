package dominio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class
TrattaTest2 {
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
        t.inserisciCorsa(1,data2,"stazione Centrale","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("22:20:00"),25);
        t.inserisciCorsa(1,data2,"Aereoporto","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("23:20:00"),25);

    }

    @Test
    void testInserisciCorsa() {
        int dimensioneIniziale= t.getElencoCorse().size();
        assertTrue(t.inserisciCorsa(1,data1,"stazione Centrale","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("22:20:00"),25));
        assertFalse(t.inserisciCorsa(1,data1,"stazione Centrale","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("22:20:00"),28));
        assertEquals(dimensioneIniziale+1,t.getElencoCorse().size());
    }

    @Test
    void testVerificaEsistenzaCorsa() {
        assertEquals(true, t.inserisciCorsa(1, data1, "Stazione XXV", "Piazza Gesu", Time.valueOf("10:30:00"),Time.valueOf("12:30:00"),5));
        // Mi aspetto false in quanto sto provando ad aggiungere una corsa con dati già presenti nel sistema
        assertEquals(false, t.inserisciCorsa(1, data1, "Stazione XXV", "Piazza Gesu", Time.valueOf("10:30:00"),Time.valueOf("12:30:00"),5));
    }
}