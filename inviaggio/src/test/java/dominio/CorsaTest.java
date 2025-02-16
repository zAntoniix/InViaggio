package dominio;

import org.junit.jupiter.api.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CorsaTest {
    static Corsa cp, cp2, cp3;
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
        cp = new Corsa(1, data , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C1T1");
        cp2 = new Corsa(1, data , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C2T1");
        cp3 = new Corsa(2, data , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2, "C3T1");
    }

    @Test
    void setPosti() {
        assertEquals(52, cp.getNumPosti());
        assertEquals(100, cp3.getNumPosti());
    }

    @Test
    void isDisponibileData() throws ParseException {
        assertTrue(cp.isDisponibileData(data));
        Date data2 = formatter.parse("24/05/2024");
        assertFalse(cp.isDisponibileData(data2));
        cp2.setNumPosti(0);
        assertFalse(cp2.isDisponibileData(data));
    }

    @Test
    void decrementaPosti() {
        assertTrue(cp.decrementaPosti()); // cp.numPosti = 52, quindi >= 1
        cp2.setNumPosti(0); // controllo nel caso in cui il numero di posti è 0
        assertFalse(cp2.decrementaPosti());
    }
}