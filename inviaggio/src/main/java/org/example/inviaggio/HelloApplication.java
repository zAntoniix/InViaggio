package org.example.inviaggio;

import dominio.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        InViaggio inviaggio = InViaggio.getInstance();

        // Simulazione dati persistenti

        inviaggio.registrati("Filippo","Ippo","FLPP23","1234");
        inviaggio.logout();

        inviaggio.registrati("Antonio","Zarbo","ZRBNTN99","Ciao");
        inviaggio.logout();

        //Creo delle tratte per la creazione dell'interfaccia
        Tratta t= new Tratta(1,"Palermo","Palermo","T1");
        Tratta t1= new Tratta(2,"Milano","Messina","T2");
        Tratta t2= new Tratta(2,"Catania","Palermo","T3");
        //Aggiungo le tratte con il metodo addTratta per rispettare l'SD
        inviaggio.addTratta(t);
        inviaggio.addTratta(t1);
        inviaggio.addTratta(t2);

        //Aggiungo una Corsa alla tratta T2 per la creazione dell'interfaccia
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Time oraPartenza = Time.valueOf("08:00:00");
            Time oraArrivo = Time.valueOf("09:15:00");
            Date data = formatter.parse("24/04/2024");
            Date data1 = formatter.parse("24/05/2025");
            Date data2 = formatter.parse("06/04/2025");

            Tratta temp = inviaggio.selezionaTratta("T1");
            temp.inserisciCorsa(1,formatter.parse("10/03/2025"), "Stazione Centrale", "Politeama", oraPartenza, oraArrivo,5);
            temp.inserisciCorsa(1,formatter.parse("11/03/2025"), "Via Tommaso Fazello", "Barbera", oraPartenza, oraArrivo,10);
            oraPartenza = Time.valueOf("10:25:00");
            oraArrivo = Time.valueOf("11:00:00");
            temp.inserisciCorsa(1,formatter.parse("09/03/2025"), "Cairoli", "Viale Ercole", oraPartenza, oraArrivo,11);
            temp.inserisciCorsa(1,formatter.parse("14/03/2025"), "Piazza Don Sturzo", "Piazza Valdesi", oraPartenza, oraArrivo,8);

            temp = inviaggio.selezionaTratta("T2");
            oraPartenza = Time.valueOf("08:30:00");
            oraArrivo = Time.valueOf("19:10:00");
            temp.inserisciCorsa(1,data, "Staz. Garibaldi", "Villa S.Giovanni", oraPartenza, oraArrivo,25);
            oraPartenza = Time.valueOf("11:30:00");
            oraArrivo = Time.valueOf("23:10:00");
            temp.inserisciCorsa(2,data, "Lampugnano", "Villa S.Giovanni", oraPartenza, oraArrivo,39);

            temp = inviaggio.selezionaTratta("T3");
            temp.inserisciCorsa(1,data, "Via Archimede", "Politeama", oraPartenza, oraArrivo,6);
            temp.inserisciCorsa(1,data2, "Via D'Amico", "Piazza Cairoli", oraPartenza, oraArrivo,12);
            temp.inserisciCorsa(2,data1, "Stazione Centrale", "Notabartolo", oraPartenza, oraArrivo,30);

            inviaggio.accedi("ZRBNTN99", "Ciao");

            Biglietto b= new Biglietto("B1",34,inviaggio.selezionaTratta("T2").selezionaCorsa("C1T2"));
            Biglietto b1= new Biglietto("B2",34,inviaggio.selezionaTratta("T3").selezionaCorsa("C2T3"));
            Biglietto b2= new Biglietto("B3",34,inviaggio.selezionaTratta("T3").selezionaCorsa("C3T3"));

            inviaggio.setTrattaSelezionata(inviaggio.selezionaTratta("T2"));
            inviaggio.setBigliettoCorrente(b);
            inviaggio.confermaBiglietto();
            inviaggio.setTrattaSelezionata(inviaggio.selezionaTratta("T3"));
            inviaggio.setBigliettoCorrente(b1);
            inviaggio.confermaBiglietto();
            inviaggio.setBigliettoCorrente(b2);
            inviaggio.confermaBiglietto();

            inviaggio.logout();
        }catch (ParseException e) {
            e.printStackTrace();
        }
        launch();
    }

}