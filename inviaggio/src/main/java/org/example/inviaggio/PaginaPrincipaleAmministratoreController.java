package org.example.inviaggio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PaginaPrincipaleAmministratoreController {

    @FXML
    private Button bottoneEsci;
    @FXML
    private Button bottoneAggiungiTratta;
    @FXML
    private Button bottoneAggiungiCorsa;
    @FXML
    private Button bottoneSospendiTratta;
    @FXML
    private Button bottoneRimuoviCorsa;

    public void onBottoneEsci(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) bottoneEsci.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        newStage.setTitle("Benvenuto");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void onBottoneTratta(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) bottoneAggiungiTratta.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("aggiungiTrattaAmministratore.fxml"));
        newStage.setTitle("Nuova Tratta");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void onBottoneCorsa(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) bottoneAggiungiCorsa.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("aggiungiCorsaAmministratore.fxml"));
        newStage.setTitle("Nuova corsa");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void onBottoneSospendiTratta(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) bottoneSospendiTratta.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("sospendiTratta.fxml"));
        newStage.setTitle("Sospendi Tratta");
        newStage.setScene(new Scene(root));
        newStage.show();

    }


    public void onBottoneRimuoviCorsa(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) bottoneRimuoviCorsa.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("rimuoviCorsa.fxml"));
        newStage.setTitle("Rimuovi corsa");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

}
