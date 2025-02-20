package org.example.inviaggio;

import dominio.InViaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ConvalidaBigliettoController {
    @FXML
    private Button bottoneIndietro;
    @FXML
    private Button bottoneConvalida;
    @FXML
    private Button bottoneCerca;
    @FXML
    private TextArea testo;
    @FXML
    private Label erroreCF;
    @FXML
    private Label erroreCodice;
    @FXML
    private TextField CF;
    @FXML
    private TextField Codice;

    InViaggio inviaggio = InViaggio.getInstance();

    public void initialize() {
        bottoneConvalida.setVisible(false);
        testo.setVisible(false);
        testo.setEditable(false);
        erroreCF.setVisible(false);
        erroreCodice.setVisible(false);
    }


    public void onBottoneCerca(ActionEvent event) {
        if(CF.getText().isEmpty()){
            erroreCF.setVisible(true);
        }else{
            erroreCF.setVisible(false);
        }
        if(Codice.getText().isEmpty()){
            erroreCodice.setVisible(true);
        }else{
            erroreCodice.setVisible(false);
        }

        if(!CF.getText().isEmpty() && !Codice.getText().isEmpty()){
            if(inviaggio.convalidaBiglietto(CF.getText(), Codice.getText())){
                bottoneConvalida.setVisible(true);
                String s = "Biglietto: "+ inviaggio.getBigliettoCorrente().getCodice() + " "+ "Stato: " + inviaggio.getBigliettoCorrente().getStato() + " " + "Corsa: " +inviaggio.getBigliettoCorrente().getCorsaPrenotata().getCodCorsa();
                testo.setText(s);
                testo.setVisible(true);
            }else{
                testo.setText("Biglietto non trovato, riprovare la procedura di convalida");
                testo.setVisible(true);
            }
        }
    }

    public void onBottoneConvalida(ActionEvent actionEvent) throws IOException {
        float cf = inviaggio.confermaConvalida();
        bottoneConvalida.setVisible(false);
        testo.setText("Biglietto convalidato, totale da pagare: " + inviaggio.getBigliettoCorrente().getCostoFinale());
    }

    public void onBottoneIndietro(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) bottoneIndietro.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("paginaPrincipaleAmministratore.fxml"));
        newStage.setTitle("Benvenuto");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

}
