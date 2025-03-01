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
    @FXML
    private Label testoCF;

    InViaggio inviaggio = InViaggio.getInstance();
    String codice;

    public void initialize() {
        testo.setVisible(false);
        testo.setText("");
        testo.setEditable(false);
        erroreCF.setVisible(false);
        testoCF.setVisible(false);
        CF.setVisible(false);
        ObservableList<String> bg = FXCollections.observableArrayList();
        for(Biglietto b : inviaggio.trasferisciBiglietto()){
            if(b.getCorsaPrenotata().getTipoMezzo() == 1) {
                String s = new String(" "+b.getCodice()+" "+"Autobus"+" "+b.getCorsaPrenotata().getLuogoPartenza()+" "+b.getCorsaPrenotata().getLuogoArrivo()+ " "+b.getCorsaPrenotata().getOraPartenza());
                bg.add(s);
            } else {
                String s = new String(" "+b.getCodice()+" "+"Treno"+" "+b.getCorsaPrenotata().getLuogoPartenza()+" "+b.getCorsaPrenotata().getLuogoArrivo()+ " "+b.getCorsaPrenotata().getOraPartenza());
                bg.add(s);
            }
        }
        elencoBiglietti.getItems().addAll(bg); //Riempio la ListView
        elencoBiglietti.getSelectionModel().selectedItemProperty().addListener(this::cambiaSceltaLista);
    }

    private void cambiaSceltaLista(ObservableValue<? extends String> Observable, String oldVal, String newVal){
        ObservableList<String> elencoSelezionato = elencoBiglietti.getSelectionModel().getSelectedItems();
        String getElementoSelezionato= (elencoSelezionato.isEmpty())?"Nessun elemento selezionato":elencoSelezionato.toString();
        String[] parte = getElementoSelezionato.split(" "); //Serve per spezzettare la stringa in un array di stringhe, crea una stringa ogni spazio
        codice = parte[1]; //Prendo il secondo elemento perchè il primo contiene [
        CF.setVisible(true);
        testoCF.setVisible(true);
    }

    public void onBottoneConferma(ActionEvent event) throws IOException {
        if(CF.getText().isEmpty()){
            erroreCF.setVisible(true);
        }else{
            erroreCF.setVisible(false);
            inviaggio.selezionaBigliettoDaTrasferire(codice);
            if(inviaggio.trasferimentoBiglietto(CF.getText())){
                testo.setText("Trasferimento biglietto al cliente " + CF.getText() + " avvenuto con successo");
                testo.setVisible(true);
            }else{
                testo.setText("Errore trasferimento biglietto, ripetere la procedura");
                testo.setVisible(true);
            }
        }
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
