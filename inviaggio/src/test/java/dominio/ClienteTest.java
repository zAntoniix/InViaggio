package dominio;

import org.junit.jupiter.api.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {
    static InViaggio inviaggio;
    static Biglietto b;
    static Cliente cl;
    static Corsa c;
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
     static void setUp() throws Exception {
         cl = new Cliente("Antonio","Zarbo","ZAIEWJ2032","Fallito");
         c = new Corsa(1, data , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C1");
         b = new Biglietto("B1", 25, c);
         cl.confermaBiglietto(b);

     }

    @Test
    void testConfermaBiglietto() {
        int dim = cl.getElencoBiglietti().size();
        b = new Biglietto("B2", 25, c);
        cl.confermaBiglietto(b);
        assertEquals(dim+1, cl.getElencoBiglietti().size());
    }

    @Test
    void testGetElencoBiglietti() {
        LinkedHashMap<String, Biglietto> mappa = new LinkedHashMap<>();
        mappa.put("B1", b);
        assertTrue(mappa.equals(cl.getElencoBiglietti()));
    }
}