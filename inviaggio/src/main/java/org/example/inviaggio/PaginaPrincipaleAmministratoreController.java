package org.example.inviaggio;

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

    public void onBottoneEsci() throws IOException {
        Stage stage = (Stage) bottoneEsci.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        newStage.setTitle("Benvenuto");
        newStage.setScene(new Scene(root, 1080,720));
        newStage.show();
    }

    public void onBottoneTratta() throws IOException {
        Stage stage = (Stage) bottoneAggiungiTratta.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("aggiungiTrattaAmministratore.fxml"));
        newStage.setTitle("Nuova Tratta");
        newStage.setScene(new Scene(root, 1080,720));
        newStage.show();
    }

    public void onBottoneCorsa() throws IOException {
        Stage stage = (Stage) bottoneAggiungiCorsa.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("aggiungiCorsaAmministratore.fxml"));
        newStage.setTitle("Nuova corsa");
        newStage.setScene(new Scene(root, 1080,720));
        newStage.show();
    }
}
