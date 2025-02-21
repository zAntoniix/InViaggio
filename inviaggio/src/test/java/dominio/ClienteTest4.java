package dominio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest4 {

        static InViaggio inviaggio;
        static Biglietto b, b2,b3;
        static Cliente cl;
        static Corsa c1, c2,c3;
        static LinkedList<Corsa> listaCorse;
        static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        static Date data, data2, data3;
        static {
        try {
            data = formatter.parse("24/01/2025");
            data2 = formatter.parse("06/06/2025");
            data3 = formatter.parse("18/03/2025");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @BeforeAll
    static void setUp() throws Exception {
        cl = new Cliente("Antonio","Zarbo","ZAIEWJ2032","Fallito");
        c1 = new Corsa(1, data , "Via Giovanni XXIII", "Stazione Centrale", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C1T1");
        b = new Biglietto("B1", 25, c1);
        cl.confermaBiglietto(b);
        c2 = new Corsa(1, data2 , "Via Giovanni XXIII", "Stazione Centrale", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),200,"C2T1");
        b2 = new Biglietto("B2", 200, c2);
        c3 = new Corsa(1, data3 , "Via Giovanni XXIII", "Stazione Centrale", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),200,"C3T1");
        b3 = new Biglietto("B3", 200, c3);
        cl.confermaBiglietto(b2);
        cl.confermaBiglietto(b3);
        listaCorse = new LinkedList<>();
        listaCorse.add(c1);
        listaCorse.add(c2);
        listaCorse.add(c3);
    }
    @Test
    void getBigliettiModificabili() {
            LinkedList<Biglietto> listaAttesa = new LinkedList();
            listaAttesa.add(b2);
            listaAttesa.add(b3);
            assertEquals(listaAttesa, cl.getBigliettiModificabili());
    }


    @Test
    void checkStatoBiglietti() {
            cl.checkStatoBiglietti();
            assertEquals("Scaduto",b.getStato());
            assertEquals("Valido",b2.getStato());
            assertEquals("Valido",b3.getStato());
    }

    @Test
    void controllaBigliettiScaduti() {
            b.setStato("Scaduto");
            assertTrue(cl.controllaBigliettiScaduti());
            b.setStato("Valido");
            assertFalse(cl.controllaBigliettiScaduti());
    }

    @Test
    void aggiornaStatoBigliettiScaduti() {
            b.setStato("Scaduto");
            cl.aggiornaStatoBigliettiScaduti();
            assertEquals("Multato",b.getStato());
            assertEquals("Valido",b2.getStato());
            assertEquals("Valido",b3.getStato());
    }

    @Test
    void listaBigliettiValidi() {
            b.setStato("Scaduto");
            LinkedList<Biglietto> listaAttesa = new LinkedList<>();
            listaAttesa.add(b2);
            listaAttesa.add(b3);
            assertEquals(listaAttesa,cl.listaBigliettiValidi());
    }
}