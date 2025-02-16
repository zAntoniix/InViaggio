package org.example.inviaggio;


import dominio.Corsa;
import dominio.InViaggio;
import dominio.Tratta;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
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
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Pattern;

public class SospendiTrattaController {

    String codice;
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
        labelErroreDataFine.setVisible(false);
        labelErroreDataInizio.setVisible(false);
        dataInizio.setVisible(false);
        dataFine.setVisible(false);
        bottoneConferma.setVisible(false);
        labelDataInizio.setVisible(false);
        labelDataFine.setVisible(false);

        ObservableList<String> tr = FXCollections.observableArrayList();
        Iterator<Map.Entry<String, Tratta>> iterator=inviaggio.visualizzaElencoTratte().entrySet().iterator();
        while(iterator.hasNext()){ //Scorro la mappa delle Tratte
            Map.Entry<String,Tratta> entry=iterator.next();
            Tratta t=entry.getValue();
            String s = new String(" "+t.getCodTratta()+" "+t.getCittaPartenza()+" "+t.getCittaArrivo() + " "); //Creo la stringa dalle informazioni della singola tratta
            tr.add(s); //Aggiungo tutto nella lista di stringhe Observable
        }
        elencoTratte.getItems().addAll(tr); //Riempio la ListView
        elencoTratte.getSelectionModel().selectedItemProperty().addListener(this::cambiaSceltaLista); //Abilito la funzione che deve eseguire ogni volta che cambia elemento
    }

    private void cambiaSceltaLista(Observable observable) {
        dataInizio.setVisible(true);
        dataFine.setVisible(true);
        bottoneConferma.setVisible(true);
        labelDataInizio.setVisible(true);
        labelDataFine.setVisible(true);
        ObservableList<String> elencoSelezionato = elencoTratte.getSelectionModel().getSelectedItems();
        String getElementoSelezionato= (elencoSelezionato.isEmpty())?"Nessun elemento selezionato":elencoSelezionato.toString();
        String[] parte = getElementoSelezionato.split(" "); //Serve per spezzettare la stringa in un array di stringhe, crea una stringa ogni spazio
        codice = parte[1]; //Prendo il secondo elemento perchè il primo contiene [
    }

    public void onBottoneConferma(ActionEvent actionEvent) throws IOException {
        LinkedList<Corsa> elencoCorse;
        if(dataInizio.getText().isEmpty() && !patternData.matcher(dataInizio.getText()).matches()){
            labelErroreDataInizio.setVisible(true);
        }else{
            labelErroreDataInizio.setVisible(false);

        }
        if(dataFine.getText().isEmpty() && !patternData.matcher(dataFine.getText()).matches()){
            labelErroreDataFine.setVisible(true);
        }else{
            labelErroreDataFine.setVisible(false);
        }

        if(!dataFine.getText().equals(" ") && !dataInizio.getText().equals(" ") && !codice.equals(" ")){
            try {
                ObservableList<String> cs = FXCollections.observableArrayList();
                Date dateInizio = formatter.parse(dataInizio.getText());
                Date dateFine = formatter.parse(dataFine.getText());
               if(inviaggio.sospendiTratta(codice)) {
                   elencoCorse = inviaggio.selezionaPeriodoSospensione(dateInizio, dateFine);
                   for (Corsa c : elencoCorse) {
                       String s = c.getCodCorsa() + " " + formatter.format(c.getData()) + " " + formatoOra.format(c.getOraPartenza()) + " " + c.getLuogoPartenza() + " " + c.getLuogoArrivo();
                       cs.add(s);
                   }
                   elencoCorseSospese.getItems().addAll(cs);
                   elencoCorseSospese.setVisible(true);
                   labelElencoCorse.setVisible(true);
               }
            }catch (Exception e){
                e.printStackTrace();
            }
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
