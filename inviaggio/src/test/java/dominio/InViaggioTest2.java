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

class InViaggioTest2 {
    static InViaggio inviaggio;
    static Cliente cl,cl2;
    static Tratta t;
    static Biglietto b,b2,b3,b4;
    static Corsa c,c2,c3,c4;
    static Date data,data2,data3,data4;

    @BeforeEach
    void setUp() {
        inviaggio= inviaggio.getInstance();
            try {
                data = formatter.parse("06/06/2025");
                data2 = formatter.parse("20/07/2025");
                data3 = formatter.parse("08/02/2025");
                data4 = formatter.parse("09/02/2025");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        cl=new Cliente("Antonio","Zarbo","ZAIEWJ2032","Fallito");
        inviaggio.getElencoClienti().add(cl);
        inviaggio.setClienteLoggato(cl);
        c = new Corsa(1, data , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C1");
        c2 = new Corsa(1, data2 , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C2");
        c3 = new Corsa(1, data3 , "Milano", "Messina", Time.valueOf("15:27:00"), Time.valueOf("23:10:00"),2,"C3");
        c4 = new Corsa(1, data4 , "Milano", "Messina", Time.valueOf("00:12:00"), Time.valueOf("23:10:00"),2,"C4");
        b=new Biglietto("B1", 2, c);
        b2=new Biglietto("B2", 2, c2);
        b3=new Biglietto("B3", 2, c3);
        b4=new Biglietto("B4", 2, c4);
        cl.getElencoBiglietti().put("B1",b);
        t = new Tratta( 1,"Catania","Napoli", "T1");
        inviaggio.getElencoTratte().put("T1",t);
        t.getElencoCorse().put("C1",c);
        t.getElencoCorse().put("C2",c2);
        t.getElencoCorse().put("C3",c3);
        t.getElencoCorse().put("C4",c4);

    }
    @Test
    void selezionaCorsa() {
       /* inviaggio.setTrattaSelezionata(t);
        assertEquals(2,inviaggio.selezionaCorsa("C1").getCostoFinale()); //nessuna regola di dominio
        assertEquals(2.66,inviaggio.selezionaCorsa("C2").getCostoFinale(),0.01); //regola della domenica
        assertEquals(1.8,inviaggio.selezionaCorsa("C3").getCostoFinale(),0.01); //regola delle 12h
        assertEquals(2.46,inviaggio.selezionaCorsa("C4").getCostoFinale(),0.01);*/
    }

    @Test
    void inserisciNuovaCorsa() {
        assertTrue(inviaggio.inserisciNuovaCorsa("T1"));
        assertFalse(inviaggio.inserisciNuovaCorsa("T5"));
    }

    @Test
    void registrati() {
        int dim = inviaggio.getElencoClienti().size();
        assertFalse(inviaggio.registrati("Simone","Squillaci","ZAIEWJ2032","djna")); //Il cliente si registra con un CF già usato da un altro cliente registrato
        assertTrue(inviaggio.registrati("Gioele","Messina","MSSGLi","axaad"));  //Il cliente si registra con un CF mai usato
        cl2=new Cliente("Gioele","Messina","MSSGLi","axaad");
        assertEquals(dim+1,inviaggio.getElencoClienti().size()); // Controllo che il cliente è stato aggiunto nella lista del sistema
        assertTrue(cl2.equals(inviaggio.getClienteLoggato()));

    }

    @Test
    void verificaCliente() {
        assertTrue(inviaggio.verificaCliente("ZAIEWJ2032","Fallito").equals(cl));
        assertNull(inviaggio.verificaCliente("ZAI","Fallito"));
    }

    @Test
    void accedi() {
        assertTrue(inviaggio.accedi("ZAIEWJ2032","Fallito"));
        assertTrue(inviaggio.getClienteLoggato().equals(cl));
    }

    @Test
    void rimuovi() {
        int dim = inviaggio.getElencoClienti().size();
        assertTrue(inviaggio.rimuovi("ZAIEWJ2032","Fallito"));
        assertEquals(dim-1,inviaggio.getElencoClienti().size());
        assertNull(inviaggio.getClienteLoggato());
        assertFalse(inviaggio.rimuovi("ZAIEWJ2032","Fdada")); //Il cliente inserisce il codice persona sbagliato
        assertFalse(inviaggio.rimuovi("ZAIEWJ","Fallito")); //Il cliente inserisce il CF persona sbagliato

    }

    @Test
    void logout() {
        inviaggio.logout();
        assertNull(inviaggio.getClienteLoggato());
    }

    @Test
    void annullaPrenotazione() {
        LinkedList<Biglietto> bigliettiAnnullabili = new LinkedList<>();
        bigliettiAnnullabili.add(b);
        assertTrue(bigliettiAnnullabili.equals(inviaggio.annullaPrenotazione()));
    }

    @Test
    void selezionaBigliettoDaAnnullare() {
        inviaggio.setClienteLoggato(cl);
        assertTrue(inviaggio.selezionaBigliettoDaAnnullare("B1"));
    }

}