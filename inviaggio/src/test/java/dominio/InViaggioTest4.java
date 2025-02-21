package dominio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import static dominio.InViaggioTest.formatter;
import static org.junit.jupiter.api.Assertions.*;

class InViaggioTest4 {
    static InViaggio inviaggio;
    static Cliente cl,cl2;
    static Tratta t;
    static Biglietto b,b2,b3,b4;
    static Corsa c1,c2,c3,c4;
    static Date data, data2, data3, data4, inizio, fine;

    @BeforeEach
    void setUp() {
        inviaggio= inviaggio.getInstance();
        try {
            data = formatter.parse("06/05/2025");
            data2 = formatter.parse("11/05/2025"); //viene di domenica
            data3 = formatter.parse("02/06/2025");
            data4 = formatter.parse("09/02/2025");

            inizio = formatter.parse("05/05/2025");
            fine = formatter.parse("05/06/2025");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        cl=new Cliente("Antonio","Zarbo","ZAIEWJ2032","Fallito");
        cl2=new Cliente("Gioele","Messina","MSSGNI","Fallito2");

        inviaggio.getElencoClienti().add(cl);
        inviaggio.getElencoClienti().add(cl2);
        inviaggio.setClienteLoggato(cl);

        c1 = new Corsa(1, data , "Via Giovanni XXIII", "Stazione Centrale", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C1T1");
        c2 = new Corsa(1, data2 , "Via Giovanni XXIII", "Stazione Centrale", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C2T1");
        c3 = new Corsa(1, data3 , "Via Giovanni XXIII", "Stazione Centrale", Time.valueOf("15:27:00"), Time.valueOf("23:10:00"),2,"C3T1");
        c4 = new Corsa(1, data4 , "Via Giovanni XXIII", "Stazione Centrale", Time.valueOf("00:12:00"), Time.valueOf("23:10:00"),2,"C4T1");
        b=new Biglietto("B1", 2, c1);
        b2=new Biglietto("B2", 2, c2);
        b3=new Biglietto("B3", 2, c3);
        b4=new Biglietto("B4", 2, c4);
        cl.getElencoBiglietti().put("B1",b);
        cl.getElencoBiglietti().put("B2",b2);
        cl.getElencoBiglietti().put("B3",b3);
        cl.getElencoBiglietti().put("B4",b4);

        t = new Tratta( 1,"Catania","Napoli", "T1");
        inviaggio.getElencoTratte().put("T1",t);
        t.getElencoCorse().put("C1T1",c1);
        t.getElencoCorse().put("C2T1",c2);
        t.getElencoCorse().put("C3T1",c3);
        t.getElencoCorse().put("C4T1",c4);
        inviaggio.setTrattaSelezionata(t);
    }

    @Test
    void testMostraBigliettiModificabili() {
        LinkedList<Biglietto> listaAttesa = new LinkedList<>();
        listaAttesa.add(b);
        listaAttesa.add(b2);
        listaAttesa.add(b3);
        System.out.println(listaAttesa);
        assertEquals(listaAttesa,inviaggio.mostraBigliettiModificabili());
    }

    @Test
    void selezionaBigliettoDaModificare() {
        inviaggio.selezionaBigliettoDaModificare("B3");
        assertEquals("B3",inviaggio.getBigliettoCorrente().getCodice());
    }

    @Test
    void confermaCorsaSostitutiva() {
        inviaggio.setBigliettoCorrente(b);
        assertTrue(inviaggio.confermaCorsaSostitutiva("C2T1"));
        assertEquals(2.66,b.getCostoFinale(),0.01);
    }

    @Test
    void visualizzaStorico() {
        LinkedHashMap<String,Biglietto> mappaAttesa= new LinkedHashMap<>();
        mappaAttesa.put("B1",b);
        mappaAttesa.put("B2",b2);
        mappaAttesa.put("B3",b3);
        mappaAttesa.put("B4",b4);
        assertTrue(mappaAttesa.equals(inviaggio.visualizzaStorico()));
    }

    @Test
    void convalidaBiglietto() {
        assertTrue(inviaggio.convalidaBiglietto("ZAIEWJ2032","B4"));
        assertFalse(inviaggio.convalidaBiglietto("Z032","B4"));
        assertFalse(inviaggio.convalidaBiglietto("ZAIEWJ2032","B14"));

    }

    @Test
    void confermaConvalida() {
        inviaggio.setClienteSelezionato(cl);
        inviaggio.setBigliettoCorrente(b);
        inviaggio.confermaConvalida();
        assertEquals("Convalidato",b.getStato());
        assertEquals(2,b.getCostoFinale());
        b4.setStato("Scaduto");
        inviaggio.confermaConvalida();
        assertEquals("Convalidato",b.getStato());
        assertEquals(3,b.getCostoFinale());
    }

    @Test
    void trasferisciBiglietto() {
        LinkedList<Biglietto> listaAttesa = new LinkedList<>();
        listaAttesa.add(b);
        listaAttesa.add(b2);
        listaAttesa.add(b3);
        assertTrue(listaAttesa.equals(inviaggio.trasferisciBiglietto()));
    }

    @Test
    void selezionaBigliettoDaTrasferire() {
        inviaggio.selezionaBigliettoDaTrasferire("B2");
        assertEquals("B2",inviaggio.getBigliettoCorrente().getCodice());
    }

    @Test
    void trasferimentoBiglietto() {
        inviaggio.setBigliettoCorrente(b2);
        assertTrue(inviaggio.trasferimentoBiglietto("MSSGNI"));
        assertEquals("MSSGNI",inviaggio.getClienteSelezionato().getCF());
        assertFalse(inviaggio.trasferimentoBiglietto("MG"));
    }
}