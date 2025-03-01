package org.example.inviaggio;

import dominio.Biglietto;
import dominio.InViaggio;
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
import javafx.stage.Stage;

import java.io.IOException;

public class AnnullaBigliettoController {

    InViaggio inviaggio = InViaggio.getInstance();
    String codice;

    @FXML
    private Button annullaBottone;
    @FXML
    private ListView<String> listaBiglietti;
    @FXML
    private Label errore;
    @FXML
    private Label verifica;

    public void initialize() {
        errore.setVisible(false);
        verifica.setVisible(false);
        ObservableList<String> bt = FXCollections.observableArrayList();
        for(Biglietto b : inviaggio.annullaPrenotazione()){
            if(b.getCorsaPrenotata().getTipoMezzo() == 1) {
                String s =new String(" " + b.getCodice() + " " + "Autobus" + " " + b.getCorsaPrenotata().getLuogoPartenza() + " " + b.getCorsaPrenotata().getLuogoArrivo() + " " + b.getCorsaPrenotata().getOraPartenza() + " "+ b.getCorsaPrenotata().getData());
                bt.add(s);
            } else {
                String s =new String(" " + b.getCodice() + " " + "Treno" + " " + b.getCorsaPrenotata().getLuogoPartenza() + " " + b.getCorsaPrenotata().getLuogoArrivo() + " " + b.getCorsaPrenotata().getOraPartenza() + " "+ b.getCorsaPrenotata().getData());
                bt.add(s);
            }
        }
        listaBiglietti.getItems().addAll(bt); //Riempio la ListView
        listaBiglietti.getSelectionModel().selectedItemProperty().addListener(this::cambiaSceltaLista); //Abilito la funzione che deve eseguire ogni volta che cambia elemento
    }

    private void cambiaSceltaLista(ObservableValue<? extends String> Observable, String oldVal, String newVal){
        ObservableList<String> elencoSelezionato = listaBiglietti.getSelectionModel().getSelectedItems();
        String getElementoSelezionato= (elencoSelezionato.isEmpty())?"Nessun elemento selezionato":elencoSelezionato.toString();
        String[] parte = getElementoSelezionato.split(" "); //Serve per spezzettare la stringa in un array di stringhe, crea una stringa ogni spazio
        codice = parte[1]; //Prendo il secondo elemento perchè il primo contiene [
    }

    public void onAnnullaBottone(ActionEvent event) throws IOException {
        Stage stage = (Stage) annullaBottone.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("homeCliente.fxml"));
        newStage.setTitle("Home Page");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void onConfermaBottone(ActionEvent event) throws IOException {
        if(listaBiglietti.getSelectionModel().isEmpty()){
            errore.setVisible(true);
        }else{
            inviaggio.selezionaBigliettoDaAnnullare(codice);
            int selectedIndex = listaBiglietti.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                listaBiglietti.getItems().remove(selectedIndex);
            }
            errore.setVisible(false);
            verifica.setVisible(true);
        }

    }

}
