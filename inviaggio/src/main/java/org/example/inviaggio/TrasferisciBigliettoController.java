package org.example.inviaggio;

import dominio.Biglietto;
import dominio.InViaggio;
import dominio.Tratta;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class TrasferisciBigliettoController {

    @FXML
    private Button bottoneIndietro;
    @FXML
    private Button bottoneConferma;
    @FXML
    private TextField CF;
    @FXML
    private TextArea testo;
    @FXML
    private Label erroreCF;
    @FXML
    private ListView<String> elencoBiglietti;


    InViaggio inviaggio = InViaggio.getInstance();
    String codice;

    public void initialize() {
        testo.setVisible(false);
        testo.setText("");
        testo.setEditable(false);
        erroreCF.setVisible(false);
        ObservableList<String> bg = FXCollections.observableArrayList();
        for(Biglietto b : inviaggio.trasferisciBiglietto()){
            //String s = new String(" "+t.getCodTratta()+" "+t.getCittaPartenza()+" "+t.getCittaArrivo() + " "); //Creo la stringa dalle informazioni della singola tratta
           // bg.add(s); //Aggiungo tutto nella lista di stringhe Observable
        }
        elencoBiglietti.getItems().addAll(bg); //Riempio la ListView
        elencoBiglietti.getSelectionModel().selectedItemProperty().addListener(this::cambiaSceltaLista);

    }

    private void cambiaSceltaLista(ObservableValue<? extends String> Observable, String oldVal, String newVal){
        ObservableList<String> elencoSelezionato = elencoBiglietti.getSelectionModel().getSelectedItems();
        String getElementoSelezionato= (elencoSelezionato.isEmpty())?"Nessun elemento selezionato":elencoSelezionato.toString();
        String[] parte = getElementoSelezionato.split(" "); //Serve per spezzettare la stringa in un array di stringhe, crea una stringa ogni spazio
        codice = parte[1]; //Prendo il secondo elemento perchè il primo contiene [
    }

    public void onBottoneIndietro(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) bottoneIndietro.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("homeCliente.fxml"));
        newStage.setTitle("Benvenuto");
        newStage.setScene(new Scene(root));
        newStage.show();
    }
}
