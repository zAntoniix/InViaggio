package dominio;

import org.junit.jupiter.api.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class InViaggioTest {
    static InViaggio inviaggio;
    static Cliente cl;
    static Tratta tr,tr2,tr3;
    static Biglietto b;

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
        inviaggio = InViaggio.getInstance();
        cl = new Cliente("Antonio","Zarbo","ZAIEWJ2032","Fallito");
        inviaggio.setClienteLoggato(cl);
        tr = new Tratta ( 1,"Catania","Napoli", "T1");
        inviaggio.getElencoTratte().put("T1",tr);
        inviaggio.setTrattaCorrente(tr);
        inviaggio.setTrattaSelezionata(tr);
        inviaggio.inserisciCorsa(1,data,"stazione","aereoporto",Time.valueOf("12:20:00"),Time.valueOf("22:20:00"),23);
        inviaggio.setBigliettoCorrente(inviaggio.selezionaCorsa("C1"));
    }

    @Test
    void testGetInstance() {
        assertNotNull(inviaggio);
    }

    @Test
    void testInserisciNuovaTratta() {
        assertTrue(inviaggio.inserisciNuovaTratta( 1,"Catania","Milano"));
        tr2 = inviaggio.getTrattaCorrente();
        assertInstanceOf(Tratta.class, inviaggio.getTrattaCorrente());
        assertTrue(tr2.equals(inviaggio.getTrattaCorrente()));
    }

    @Test
    void testGeneraCodTratta() {
        assertEquals("T"+(inviaggio.getElencoTratte().size()+1),inviaggio.generaCodTratta());
    }

    @Test
    void testInserisciCorsa() {
        assertTrue(inviaggio.inserisciCorsa(1,data,"stazione Centrale","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("22:20:00"),25));
    }

    @Test
    void testConfermaInserimento() {
        tr3 = new Tratta ( 2,"Milano","Napoli", "T3");
        inviaggio.setTrattaCorrente(tr3);
        int size = inviaggio.getElencoTratte().size();
        assertTrue(inviaggio.confermaInserimento());
        assertEquals(size+1, inviaggio.getElencoTratte().size());
    }

    @Test
    void testPrenotaBiglietto() {
        LinkedHashMap<String,Tratta> l;
        l = inviaggio.visualizzaElencoTratte();
        assertEquals(l,inviaggio.visualizzaElencoTratte());
    }

    @Test
    void testSelezionaTratta() {
        assertInstanceOf(Tratta.class, inviaggio.selezionaTratta("T1"));
        assertEquals("T1",inviaggio.getTrattaSelezionata().getCodTratta());

    }

    @Test
    void testRichiediCorsePerData() {
        LinkedList<Corsa> l;
        inviaggio.setTrattaSelezionata(tr);
        l=inviaggio.richiediCorsePerData(data);
        assertEquals(l,inviaggio.richiediCorsePerData(data));
    }

    @Test
    void testGeneraCodBiglietto() {
        assertEquals("B"+(inviaggio.getClienteLoggato().getElencoBiglietti().size()+1),inviaggio.generaCodBiglietto());
    }

    @Test
    void testSelezionaCorsa() {
        inviaggio.setTrattaSelezionata(tr);
        assertInstanceOf(Biglietto.class, inviaggio.selezionaCorsa("C1"));
    }

    @Test
    void testConfermaBiglietto() {
        assertTrue(inviaggio.confermaBiglietto());
    }

}