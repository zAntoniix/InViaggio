package org.example.inviaggio;

import dominio.Cliente;
import dominio.Corsa;
import dominio.InViaggio;
import dominio.Tratta;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        Cliente c= new Cliente("Antonio","Zarbo","ZAIEWJ2032","Fallito");
        inviaggio.setClienteLoggato(c);
        //Creo delle tratte per la creazione dell'interfaccia
        Tratta t= new Tratta(0,"Palermo","Catania","30212");
        Tratta t1= new Tratta(0,"Milano","Messina","20010");
        Tratta t2= new Tratta(1,"Catania","Palermo","78562");
        //Aggiungo le tratte con il metodo addTratta per rispettare l'SD
        inviaggio.addTratta(t);
        inviaggio.addTratta(t1);
        inviaggio.addTratta(t2);
        //Aggiungo una Corsa alla tratta 20010 per la creazione dell'interfaccia
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            Time oraPartenza = Time.valueOf("12:30:00");
            Time oraArrivo = Time.valueOf("23:10:00");
            Date data = formatter.parse("24/04/2024");
            Tratta temp = inviaggio.selezionaTratta("20010");
            temp.inserisciCorsa(1,data, "Milano", "Messina", oraPartenza, oraArrivo,2);
            //Dall'interfaccia e dal print che ho tolto funziona anche la generazione casuale del codice
        }catch (ParseException e) {
        e.printStackTrace();
    }

        launch();
    }


}