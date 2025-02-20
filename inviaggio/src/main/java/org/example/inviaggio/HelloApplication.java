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
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        InViaggio inviaggio = InViaggio.getInstance();

        inviaggio.registrati("Antonio","Zarbo","ZRBNTN99","Fallito");


        //Creo delle tratte per la creazione dell'interfaccia
        Tratta t= new Tratta(0,"Palermo","Catania","T1");
        Tratta t1= new Tratta(0,"Milano","Messina","T2");
        Tratta t2= new Tratta(1,"Catania","Palermo","T3");
        //Aggiungo le tratte con il metodo addTratta per rispettare l'SD
        inviaggio.addTratta(t);
        inviaggio.addTratta(t1);
        inviaggio.addTratta(t2);

        //Aggiungo una Corsa alla tratta T2 per la creazione dell'interfaccia
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            Time oraPartenza = Time.valueOf("08:30:00");
            Time oraArrivo = Time.valueOf("15:10:00");
            Date data = formatter.parse("24/04/2024");
            Date data1 = formatter.parse("24/05/2025");
            Date data2 = formatter.parse("06/04/2025");
            Tratta temp = inviaggio.selezionaTratta("T2");
            temp.inserisciCorsa(1,data, "Milano", "Messina", oraPartenza, oraArrivo,25);
            oraPartenza = Time.valueOf("11:30:00");
            oraArrivo = Time.valueOf("23:10:00");
            temp.inserisciCorsa(1,data, "Milano", "Messina", oraPartenza, oraArrivo,39);
            temp = inviaggio.selezionaTratta("T3");
            temp.inserisciCorsa(1,data1, "Catania", "Palermo", oraPartenza, oraArrivo,12);
            temp.inserisciCorsa(1,data2, "Bari", "Messina", oraPartenza, oraArrivo,12);

            Biglietto b= new Biglietto("B1",34,inviaggio.selezionaTratta("T2").selezionaCorsa("C1T2"));
            Biglietto b1= new Biglietto("B2",34,inviaggio.selezionaTratta("T3").selezionaCorsa("C1T3"));
            Biglietto b2= new Biglietto("B3",34,inviaggio.selezionaTratta("T3").selezionaCorsa("C2T3"));

            inviaggio.setTrattaSelezionata(inviaggio.selezionaTratta("T2"));
            inviaggio.setBigliettoCorrente(b);
            inviaggio.confermaBiglietto();
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