package org.example.inviaggio;

import dominio.InViaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AggiungiTrattaAmministratoreController {

    @FXML
    private TextField tipoTratta;
    @FXML
    private TextField cittaPartenza;
    @FXML
    private TextField cittaArrivo;
    @FXML
    private Label erroreTipo;
    @FXML
    private Label errorePartenza;
    @FXML
    private Label erroreArrivo;
    @FXML
    private Button bottoneAnnulla;
    @FXML
    private Button bottoneConferma;

    InViaggio inviaggio = InViaggio.getInstance();


    public void initialize(){
        erroreTipo.setVisible(false);
        errorePartenza.setVisible(false);
        erroreArrivo.setVisible(false);
    }

    public void onConfermaBottone(ActionEvent actionEvent) throws IOException {
        if(tipoTratta.getText().isEmpty()){
            erroreTipo.setVisible(true);
        }else{
            erroreTipo.setVisible(false);
        }

        if(cittaArrivo.getText().isEmpty()){
            erroreArrivo.setVisible(true);
        }else{
            erroreArrivo.setVisible(false);
        }
        if(cittaPartenza.getText().isEmpty()){
            errorePartenza.setVisible(true);
        }else{
            errorePartenza.setVisible(false);
        }

        if(!tipoTratta.getText().equals(" ") && !cittaPartenza.getText().equals(" ") && !cittaArrivo.getText().equals(" ")){
            inviaggio.inserisciNuovaTratta(Integer.parseInt(tipoTratta.getText()),cittaPartenza.getText(),cittaArrivo.getText());
            Stage stage = (Stage) bottoneConferma.getScene().getWindow();
            stage.close();
            Stage newStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("aggiungiCorsaAmministratore.fxml"));
            newStage.setTitle("Inserisci Corsa");
            newStage.setScene(new Scene(root, 1080,720));
            newStage.show();
        }

    }

    public void onAnnullaClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneAnnulla.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("paginaPrincipaleAmministratore.fxml"));
        newStage.setTitle("Bentornato Amministratore");
        newStage.setScene(new Scene(root, 1080,720));
        newStage.show();
    }

}
