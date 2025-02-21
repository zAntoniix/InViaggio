package dominio;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

class InViaggioTest3 {
    static InViaggio inviaggio;
    static Cliente cl,cl2;
    static Tratta t;
    static Biglietto b,b2,b3,b4;
    static Corsa c1,c2,c3,c4;
    static Date data, data2, data3, data4, inizio, fine;
    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @BeforeAll
    static void setUp() {
        inviaggio= inviaggio.getInstance();
        try {
            data = formatter.parse("06/05/2025");
            data2 = formatter.parse("10/05/2025");
            data3 = formatter.parse("02/06/2025");
            data4 = formatter.parse("09/07/2025");

            inizio = formatter.parse("05/05/2025");
            fine = formatter.parse("05/06/2025");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        cl=new Cliente("Antonio","Zarbo","ZAIEWJ2032","Fallito");
        inviaggio.getElencoClienti().add(cl);
        inviaggio.setClienteLoggato(cl);
        c1 = new Corsa(1, data , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C1T1");
        c2 = new Corsa(1, data2 , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C2T1");
        c3 = new Corsa(1, data3 , "Milano", "Messina", Time.valueOf("15:27:00"), Time.valueOf("23:10:00"),2,"C3T1");
        c4 = new Corsa(1, data4 , "Milano", "Messina", Time.valueOf("00:12:00"), Time.valueOf("23:10:00"),2,"C4T1");
        b=new Biglietto("B1", 2, c1);
        b2=new Biglietto("B2", 2, c2);
        b3=new Biglietto("B3", 2, c3);
        b4=new Biglietto("B4", 2, c4);
        cl.getElencoBiglietti().put("B1",b);
        t = new Tratta( 1,"Catania","Napoli", "T1");
        inviaggio.getElencoTratte().put("T1",t);
        t.getElencoCorse().put("C1T1",c1);
        t.getElencoCorse().put("C2T1",c2);
        t.getElencoCorse().put("C3T1",c3);
        t.getElencoCorse().put("C4T1",c4);
        inviaggio.setTrattaSelezionata(t);
    }

    @Test
    void testVerificaAmministratore() {
        assertTrue(inviaggio.verificaAmministratore(1234));
        assertFalse(inviaggio.verificaAmministratore(02345));
    }

    @Test
    void testAccediAmministratore() {
        assertTrue(inviaggio.accediAmministratore(1234));
        assertFalse(inviaggio.accediAmministratore(0234));
    }

    @Test
    void testSospendiTratta() {
        assertFalse(inviaggio.sospendiTratta("T2"));
        assertTrue(inviaggio.sospendiTratta("T1"));
    }

    @Test
    void testSelezionaPeriodoSospensione() {
        LinkedList<Corsa> listaAnnullate = new LinkedList<>();
        listaAnnullate.add(c1);
        listaAnnullate.add(c2);
        listaAnnullate.add(c3);
        assertEquals(listaAnnullate, inviaggio.selezionaPeriodoSospensione(inizio, fine));
    }

   @Test
    void testRimuoviCorsa() {
        assertTrue(inviaggio.rimuoviCorsa("C4T1"));
    }
}