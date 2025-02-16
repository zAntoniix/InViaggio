package dominio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CorsaTest3 {
    static Corsa cp, cp2, cp3;
    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    static Date data, data2, data3, periodoInizio, periodoFine;
    static {
        try {
            data = formatter.parse("24/04/2024");
            data2 = formatter.parse("27/04/2024");
            data3 = formatter.parse("31/04/2024");

            periodoInizio = formatter.parse("24/04/2024");
            periodoFine = formatter.parse("30/04/2024");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    public static void setUp() {
        cp = new Corsa(1, data , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C1T1");
        cp2 = new Corsa(1, data2 , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2,"C2T1");
        cp3 = new Corsa(2, data3 , "Milano", "Messina", Time.valueOf("12:30:00"), Time.valueOf("23:10:00"),2, "C3T1");
    }

    @Test
    void testGetCorsePerPeriodo() {
        assertTrue(cp.getCorsePerPeriodo(periodoInizio, periodoFine));
        assertTrue(cp2.getCorsePerPeriodo(periodoInizio, periodoFine));
        assertFalse(cp3.getCorsePerPeriodo(periodoInizio, periodoFine));
    }
}