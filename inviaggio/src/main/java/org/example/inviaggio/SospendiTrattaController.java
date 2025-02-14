package org.example.inviaggio;


import dominio.InViaggio;
import dominio.Tratta;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.Iterator;
import java.util.Map;

public class SospendiTrattaController {

    InViaggio inviaggio = InViaggio.getInstance();
    @FXML
    private ListView elencoTratteSospensione;
    @FXML
    private ListView elencoCorseSospensione;
    @FXML
    private Label labelElencoTratte;
    @FXML
    private Label labelElencoCorse;
    @FXML
    private Button bottoneConfermaTratta;
    @FXML
    private Button bottoneConfermaPeriodo;
    @FXML
    private TextField dataInizio;
    @FXML
    private TextField dataFine;
    @FXML
    private Label labelDataInizio;
    @FXML
    private Label labelDataFine;
    @FXML
    private Label labelErroreDataFine;
    @FXML
    private Label labelErroreDataInizio;
    @FXML
    private Button bottoneConfermaCorsa;



    public void initialize() {
        elencoCorseSospensione.setVisible(false);
        labelElencoCorse.setVisible(false);
        bottoneConfermaPeriodo.setVisible(false);
        dataInizio.setVisible(false);
        dataFine.setVisible(false);
        labelDataInizio.setVisible(false);
        labelDataFine.setVisible(false);
        labelErroreDataFine.setVisible(false);
        labelErroreDataInizio.setVisible(false);
        bottoneConfermaCorsa.setVisible(false);

        ObservableList<String> tr = FXCollections.observableArrayList();
        Iterator<Map.Entry<String, Tratta>> iterator=inviaggio.visualizzaElencoTratte().entrySet().iterator();
        while(iterator.hasNext()){ //Scorro la mappa delle Tratte
            Map.Entry<String,Tratta> entry=iterator.next();
            Tratta t=entry.getValue();
            String s = new String(" "+t.getCodTratta()+" "+t.getCittaPartenza()+" "+t.getCittaArrivo() + " "); //Creo la stringa dalle informazioni della singola tratta
            tr.add(s); //Aggiungo tutto nella lista di stringhe Observable
        }
        elencoTratteSospensione.getItems().addAll(tr); //Riempio la ListView
        elencoTratteSospensione.getSelectionModel().selectedItemProperty().addListener(this::cambiaSceltaLista); //Abilito la funzione che deve eseguire ogni volta che cambia elemento
    }

    private void cambiaSceltaLista(Observable observable) {

    }


}
