package dominio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BigliettoTest4 {
    static Corsa c1, c2;
    static Biglietto b1, b2;
    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    static Date data, data2;
    static {
        try {
            data = formatter.parse("06/02/2025");
            data2 = formatter.parse("06/06/2025");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void setUp() throws Exception {
        c1 = new Corsa(1, data , "Garibaldi", "Stazione Centrale", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C1T1");
        b1 = new Biglietto("B1", 2, c1);
        c1.setNumPosti(50);

        c2 = new Corsa(1, data2 , "Garibaldi", "Stazione Centrale", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),200,"C2T1");
        b2 = new Biglietto("B2", 200, c2);
    }

    @Test
    void testIsModificabile() {
        assertFalse(b1.isModificabile());
        assertTrue(b2.isModificabile());
    }

    @Test
    void testIsScaduto() {
        b2.setStato("Scaduto");
        assertTrue(b2.isScaduto());
        b2.setStato("Valido");
        assertFalse(b2.isScaduto());
    }

    @Test
    void testIsValido() {
        b2.setStato("Valido");
        assertTrue(b2.isValido());
        b2.setStato("Scaduto");
        assertFalse(b2.isValido());
    }
}