package dominio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest3 {
    static InViaggio inviaggio;
    static Biglietto b, b2;
    static Cliente cl;
    static Corsa c1, c2;
    static LinkedList<Corsa> listaCorse;
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
        c1 = new Corsa(1, data , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C1");
        b = new Biglietto("B1", 25, c1);
        cl.confermaBiglietto(b);

        c2 = new Corsa(1, data2 , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),200,"C2");
        b2 = new Biglietto("B2", 200, c2);
        cl.confermaBiglietto(b2);
        listaCorse = new LinkedList<>();
        listaCorse.add(c1);
        listaCorse.add(c2);
    }

    @Test
    void annullaBigliettoPerSospensione() {
        assertTrue(cl.annullaBigliettoPerSospensione(listaCorse));
        assertTrue(cl.getElencoBiglietti().isEmpty());
    }
}