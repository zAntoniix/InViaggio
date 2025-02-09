package org.example.inviaggio;

import dominio.InViaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginAmministratoreController {

    @FXML
    private Button bottoneAnnulla;
    @FXML
    private Button bottoneConferma;

    InViaggio inviaggio = InViaggio.getInstance();


    public void onAnnullaClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneAnnulla.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        newStage.setTitle("Benvenuto");
        newStage.setScene(new Scene(root, 1080,720));
        newStage.show();
    }

    public void onConfermaClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneConferma.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("paginaPrincipaleAmministratore.fxml"));
        newStage.setTitle("Bentornato Amministratore");
        newStage.setScene(new Scene(root, 1080,720));
        newStage.show();
    }

}
