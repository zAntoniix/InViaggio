package org.example.inviaggio;

import dominio.Biglietto;
import dominio.Corsa;
import dominio.InViaggio;
import dominio.Tratta;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PaginaClienteController {
    InViaggio inviaggio = InViaggio.getInstance();
    ObservableList<String> tr = FXCollections.observableArrayList();
    Tratta t;
    String codCorsa;

    @FXML
    private Label nomeUtente;
    @FXML
    private ListView<String> menuTratte;
    @FXML
    private Label trattaScelta;
    @FXML
    private Label oggettoSelezionato;
    @FXML
    private Label nomeView;
    @FXML
    private Label erroreData;
    @FXML
    private Button bottoneScelta;
    @FXML
    private Button bottoneSceltaCorsa;
    @FXML
    private Button bottoneSceltaBiglietto;
    @FXML
    private TextField dataScelta;



    public void initialize(){
        nomeUtente.setText(inviaggio.getClienteLoggato().getNome());
        //Utilizzo il metodo prenotaBiglietto per rispettare l'SD. Utilizzo l'iteretor per andare a scorrere la mappa
        Iterator<Map.Entry<String,Tratta>>iterator=inviaggio.prenotaBiglietto().entrySet().iterator();
        while(iterator.hasNext()){ //Scorro la mappa delle Tratte
            Map.Entry<String,Tratta> entry=iterator.next();
            Tratta t=entry.getValue();
            String s = new String(" "+t.getCodTratta()+" "+t.getCittaPartenza()+" "+t.getCittaArrivo() + " "); //Creo la stringa dalle informazioni della singola tratta
            //Nota ho messo lo spazio perchè il primo carattere è [ mettendo lo spazio vado a toglierlo con il parse [vedi dopo]
            tr.add(s); //Aggiungo tutto nella lista di stringhe Observable
        }
        menuTratte.getItems().addAll(tr); //Riempio la ListView
        menuTratte.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); //Abilito la scelta multipla della lista
        menuTratte.getSelectionModel().selectedItemProperty().addListener(this::cambiaSceltaLista); //Abilito la funzione che deve eseguire ogni volta che cambia elemento

    }

    //Funzione per la ListView
    private void cambiaSceltaLista(ObservableValue<? extends String> Observable, String oldVal, String newVal){
        ObservableList<String> elencoSelezionato = menuTratte.getSelectionModel().getSelectedItems();
        String getElementoSelezionato= (elencoSelezionato.isEmpty())?"Nessun elemento selezionato":elencoSelezionato.toString();
        String[] parte = getElementoSelezionato.split(" "); //Serve per spezzettare la stringa in un array di stringhe, crea una stringa ogni spazio
        String codice = parte[1]; //Prendo il secondo elemento perchè il primo contiene [
        trattaScelta.setText(codice);
    }

    //Funzione del bottone Conferma
    public void onConfermaBottone(ActionEvent event) throws IOException {
        if(!dataScelta.getText().equals("")) { //Controllo se il campo della data non è vuoto se no da errore
            ObservableList<String> cr = FXCollections.observableArrayList();
            List<Corsa> listaCorse = new ArrayList<Corsa>();
            //Seleziono la tratta con il metodo creato appositamente rispettando l'SD
            t = inviaggio.selezionaTratta(trattaScelta.getText());
            menuTratte.getItems().clear(); //Pulisco la listView
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //Permette di dare il patter alla data
            try {
                //Prendo la data dal textbox e la converto in un tipo Date
                Date date = formatter.parse(dataScelta.getText());
                System.out.println(date); //Stampa di prova
                inviaggio.richiediCorsePerData(date);
                //Uso il metodo richiediCorsePerData per farmi ritornare la lista delle corse per quella data rispettando l'SD
                listaCorse = inviaggio.richiediCorsePerData(date);
                for (Corsa c : listaCorse) { //Scorro la lista di corse
                    String s = new String(" " + c.getCodCorsa() + " " + c.getLuogoPartenza() + " " + c.getLuogoArrivo() + " " + c.getOraPartenza().toString() + " " + c.getOraArrivo().toString() + " " + c.getCostoBase());
                    cr.add(s);
                }
                menuTratte.getItems().addAll(cr);
                menuTratte.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                menuTratte.getSelectionModel().selectedItemProperty().addListener(this::cambiaSceltaLista);
                nomeView.setText("Corse Disponibili");
                bottoneScelta.setVisible(false);//Nascondo il bottone che cerca la tratta per evitare errori
                bottoneSceltaCorsa.setVisible(true); //Visualizzo il bottone del conferma corse
                oggettoSelezionato.setText("Corsa Selezionata"); //Cambio il nome da Tratta selezionata a Corsa selezionata
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            erroreData.setText("Inserire Data");
        }
        //Fine funzione
    }

    public void onConfermaBottoneCorsa(ActionEvent event) throws IOException {
        ObservableList<String> cr = FXCollections.observableArrayList();
        Corsa c = t.selezionaCorsa(trattaScelta.getText()); //Prendo il codice della corsa, adesso trattaScelta non contiene più il codice della tratta ma quella della corsa
        String codice = inviaggio.generaCodBiglietto();
        float costoBase = c.getCostoBase();
        Biglietto b = new Biglietto(codice,costoBase,c);
        inviaggio.setBigliettoCorrente(b);
        System.out.println(inviaggio.getBigliettoCorrente());
        nomeView.setText("Riepilogo Biglietto");
        oggettoSelezionato.setText("Codice biglietto"); //Cambio il nome da Corsa selezionata a Codice biglietto
        // Inizio creazione Stringa
        String s = new String(" " + b.getCodice() + " " + c.getLuogoPartenza() + " " +
                c.getLuogoArrivo() + " " + c.getData() + " " + c.getOraPartenza().toString() + " " + c.getOraArrivo().toString() + " " +
                b.getCostoFinale() + " " + c.getCodCorsa());
        // Fine creazione Stringa
        cr.add(s);
        menuTratte.getItems().clear();
        menuTratte.getItems().addAll(cr);
        menuTratte.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        menuTratte.getSelectionModel().selectedItemProperty().addListener(this::cambiaSceltaLista);
        bottoneSceltaCorsa.setVisible(false);//Nascondo il bottone che cerca la corsa per evitare errori
        bottoneSceltaBiglietto.setVisible(true); //Visualizzo il bottone del conferma biglietto
    }

    public void onConfermaBottoneBiglietto(ActionEvent event) throws IOException {
        inviaggio.confermaBiglietto();
        inviaggio.getClienteLoggato().confermaBiglietto(inviaggio.getBigliettoCorrente());
        bottoneSceltaBiglietto.setVisible(false);//Nascondo il bottone che conferma il biglietto per evitare errori
        System.out.println(inviaggio.getClienteLoggato().getElencoBiglietti());
    }

}
