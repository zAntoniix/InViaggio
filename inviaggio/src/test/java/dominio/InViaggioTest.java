package dominio;

import org.junit.jupiter.api.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InViaggioTest {
    static InViaggio inviaggio;
    static Cliente cl;
    static Tratta tr;

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
    }

    @Test
    @Order(1)
    void testGetInstance() {
        assertNotNull(inviaggio);
    }

    @Test
    @Order(2)
    void testInserisciNuovaTratta() {
        assertTrue(inviaggio.inserisciNuovaTratta( 1,"Catania","Milano"));
        tr = new Tratta ( 1,"Catania","Milano", "T1");
        assertInstanceOf(Tratta.class, inviaggio.getTrattaCorrente());
        assertTrue(tr.equals(inviaggio.getTrattaCorrente()));
    }

    @Test
    @Order(5)
    void testGeneraCodTratta() {
        assertEquals("T2",inviaggio.generaCodTratta());
    }

    @Test
    @Order(3)
    void testInserisciCorsa() {
        assertTrue(inviaggio.inserisciCorsa(1,data,"stazione Centrale","Stazione Gar", Time.valueOf("12:30:00"),Time.valueOf("22:20:00"),25));
    }

    @Test
    @Order(4)
    void testConfermaInserimento() {
        int size = inviaggio.getElencoTratte().size();
        assertTrue(inviaggio.confermaInserimento());
        assertEquals(size+1, inviaggio.getElencoTratte().size());
    }

    @Test
    void testPrenotaBiglietto() {
    }

    @Test
    void testSelezionaTratta() {
    }

    @Test
    void testRichiediCorsePerData() {
    }

    @Test
    void testGeneraCodBiglietto() {
    }

    @Test
    void testSelezionaCorsa() {
    }

    @Test
    void testConfermaBiglietto() {
    }

    @Test
    void testAddTratta() {
    }
}