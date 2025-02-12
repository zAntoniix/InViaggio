package org.example.inviaggio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;

public class HelloController {
    private Stage stage;
    private Scene scene;


    @FXML
    private Button bottoneAmministratore;
    @FXML
    private Button bottoneCliente;
    @FXML
    private Hyperlink link;


    @FXML
     public void onBottoneCliente(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneCliente.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("loginCliente.fxml"));
        newStage.setTitle("Login Cliente");
        newStage.setScene(new Scene(root, 1080,720));
        newStage.show();
    }

    public void onBottoneAmministratore(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneAmministratore.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("loginAmministratore.fxml"));
        newStage.setTitle("Login Amministratore");
        newStage.setScene(new Scene(root, 1080,720));
        newStage.show();
    }

    public void onHyperlinkClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) link.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("registrazioneCliente.fxml"));
        newStage.setTitle("Registrazione Cliente");
        newStage.setScene(new Scene(root, 1080,720));
        newStage.show();
    }
}