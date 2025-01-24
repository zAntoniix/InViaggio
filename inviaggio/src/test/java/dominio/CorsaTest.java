package dominio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CorsaTest {
    static Corsa cp;
    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    static Date data;
    static {
        try {
            data = formatter.parse("24/04/2024");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    public static void setUp() {
        cp = new Corsa(1, data , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2);

    }

    @Test
    void setPosti() {
        assertEquals(52, cp.setPosti(1));
        assertEquals(100, cp.setPosti(2));
    }

    @Test
    void generaCodCorsa() {
        String prova = cp.generaCodCorsa();
        assertInstanceOf(String.class, prova);
    }

    @Test
    void isDisponibileData() throws ParseException {
        assertTrue(cp.isDisponibileData(data));

        Date data2 = formatter.parse("24/05/2024");
        assertFalse(cp.isDisponibileData(data2));
    }

    @Test
    void decrementaPosti() {
        assertTrue(cp.decrementaPosti());

        Corsa cp2 = new Corsa(1, data , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2);
        cp2.setNumPosti(0);
        assertFalse(cp2.decrementaPosti()); // cp.numPosti = 52, quindi >= 1
    }
}