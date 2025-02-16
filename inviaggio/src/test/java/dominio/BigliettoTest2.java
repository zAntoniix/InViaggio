package dominio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BigliettoTest2 {
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
        c1 = new Corsa(1, data , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C1T1");
        b1 = new Biglietto("B1", 2, c1);
        c1.setNumPosti(50);

        c2 = new Corsa(1, data2 , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),200,"C2T1");
        b2 = new Biglietto("B2", 200, c2);
    }

    @Test
    void testGetBigliettiAnnullabili() {
        // mi aspetto false in quanto mancano meno di 12 ore alla partenza (oggi 6/2/25 alle 10:10)
        // assertFalse(b1.getBigliettiAnnullabili());
        //mi aspetto true in quanto il biglietto è riferito a una corsa di giugno (oggi 6/2/25 alle 10:10)
        assertTrue(b2.getBigliettiAnnullabili());
    }

}