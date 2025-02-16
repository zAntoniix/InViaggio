package org.example.inviaggio;

import dominio.Corsa;
import dominio.InViaggio;
import dominio.Tratta;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class RimuoviCorsaController {

    String codice,codiceCorsa;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    String datePattern = "^\\d{2}/\\d{2}/\\d{4}$";
    Pattern patternData = Pattern.compile(datePattern);
    SimpleDateFormat formatoOra = new SimpleDateFormat("HH:mm");

    InViaggio inviaggio = InViaggio.getInstance();

    @FXML
    private ListView elencoTratte;
    @FXML
    private ListView elencoCorseSospese;
    @FXML
    private Label labelElencoCorse;
    @FXML
    private TextField dataInizio;
    @FXML
    private TextField dataFine;
    @FXML
    private Label labelErroreDataFine;
    @FXML
    private Label labelErroreDataInizio;
    @FXML
    private Button bottoneAnnulla;
    @FXML
    private Button bottoneConferma;
    @FXML
    private Label labelDataInizio;
    @FXML
    private Label labelDataFine;

    public void initialize() {
        elencoCorseSospese.setVisible(false);
        labelElencoCorse.setVisible(false);
        ObservableList<String> tr = FXCollections.observableArrayList();
        Iterator<Map.Entry<String, Tratta>> iterator=inviaggio.visualizzaElencoTratte().entrySet().iterator();
        while(iterator.hasNext()){ //Scorro la mappa delle Tratte
            Map.Entry<String,Tratta> entry=iterator.next();
            Tratta t=entry.getValue();
            String s = new String(" "+t.getCodTratta()+" "+t.getCittaPartenza()+" "+t.getCittaArrivo() + " "); //Creo la stringa dalle informazioni della singola tratta
            tr.add(s); //Aggiungo tutto nella lista di stringhe Observable
        }
        elencoTratte.getItems().addAll(tr); //Riempio la ListView
        elencoTratte.getSelectionModel().selectedItemProperty().addListener(this::cambiaSceltaListaTratta); //Abilito la funzione che deve eseguire ogni volta che cambia elemento
    }

    private void cambiaSceltaListaTratta(Observable observable) {
        ObservableList<String> elencoSelezionato = elencoTratte.getSelectionModel().getSelectedItems();
        String getElementoSelezionato= (elencoSelezionato.isEmpty())?"Nessun elemento selezionato":elencoSelezionato.toString();
        String[] parte = getElementoSelezionato.split(" "); //Serve per spezzettare la stringa in un array di stringhe, crea una stringa ogni spazio
        codice = parte[1]; //Prendo il secondo elemento perchè il primo contiene [
        inviaggio.selezionaTratta(codice);
        elencoCorseSospese.getItems().clear();
        elencoCorseSospese.setVisible(true);
        ObservableList<String> cs = FXCollections.observableArrayList();
        for(Corsa c : inviaggio.mostraCorse()){
            String s = c.getCodCorsa() + " "+ formatter.format(c.getData())+ " " + formatoOra.format(c.getOraPartenza()) + " " + c.getLuogoPartenza() + " " + c.getLuogoArrivo() ;
            cs.add(s);
        }
        elencoCorseSospese.getItems().addAll(cs);
        elencoCorseSospese.getSelectionModel().selectedItemProperty().addListener(this::cambiaSceltaListaCorse); //Abilito la funzione che deve eseguire ogni volta che cambia elemento
    }

    private void cambiaSceltaListaCorse(Observable observable) {
        ObservableList<String> elencoSelezionato = elencoCorseSospese.getSelectionModel().getSelectedItems();
        String getElementoSelezionato= (elencoSelezionato.isEmpty())?"Nessun elemento selezionato":elencoSelezionato.toString();
        String[] parte = getElementoSelezionato.split(" "); //Serve per spezzettare la stringa in un array di stringhe, crea una stringa ogni spazio
        codiceCorsa = parte[0].replace("[", "").trim(); //Prendo il secondo elemento perchè il primo contiene [
    }

    public void onConfermaClick(ActionEvent event) throws IOException {
        inviaggio.rimuoviCorsa(codiceCorsa);
        int selectedIndex = elencoCorseSospese.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            elencoCorseSospese.getItems().remove(selectedIndex);
        }
    }

    public void onAnnullaClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneAnnulla.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("paginaPrincipaleAmministratore.fxml"));
        newStage.setTitle("Benvenuto");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

}
