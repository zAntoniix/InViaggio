package dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dominio.*;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;

import static dominio.InViaggioTest.formatter;
import static org.junit.jupiter.api.Assertions.*;


public class InViaggioTest3 {
    static InViaggio inviaggio;

    @BeforeEach
    void setUp() {
        inviaggio= inviaggio.getInstance();
    }

    @Test
    void testVerificaAmministatore() {
        assertTrue(inviaggio.verificaAmministatore(1234));
        assertFalse(inviaggio.verificaAmministatore(02345));
    }
    @Test
    void testAccediAmministratore() {
        assertTrue(inviaggio.accediAmministratore(1234));
        assertFalse(inviaggio.accediAmministratore(0234));
    }

}
