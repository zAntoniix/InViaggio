package dominio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

// Test sui metodi inseriti durante l'Iterazione 2

class ClienteTest2 {
    static InViaggio inviaggio;
    static Biglietto b, b2;
    static Cliente cl;
    static Corsa c, c2;
    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    static Date data, data2;
    static {
        try {
            data = formatter.parse("24/01/2025");
            data2 = formatter.parse("06/06/2025");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void setUp() throws Exception {
        cl = new Cliente("Antonio","Zarbo","ZAIEWJ2032","Fallito");
        c = new Corsa(1, data , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C1");
        b = new Biglietto("B1", 25, c);
        cl.confermaBiglietto(b);

        c2 = new Corsa(1, data2 , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),200,"C2");
        b2 = new Biglietto("B2", 200, c2);
        cl.confermaBiglietto(b2);
    }

    @Test
    void testRimuoviAccount() {
        assertTrue(cl.rimuoviAccount());
    }

    @Test
    void testAnnullaPrenotazione() {
        LinkedList<Biglietto> list = new LinkedList<>();
        list.add(b2); // ci aspettiamo ritorni solo questo biglietto
        assertTrue(list.equals(cl.annullaPrenotazione()));
    }

    @Test
    void testAnnullaBiglietto() {
        int size = cl.getElencoBiglietti().size();
        assertTrue(cl.annullaBiglietto("B2"));
        assertEquals(size-1, cl.getElencoBiglietti().size());

        LinkedHashMap<String, Biglietto> mappa = new LinkedHashMap<>();
        mappa.put("B1", b);
        assertTrue(mappa.equals(cl.getElencoBiglietti()));
    }
}