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

import javax.swing.*;
import java.io.IOException;

public class RegistrazioneClienteController {

    private Stage stage;
    private Scene scene;

    @FXML
    private Button bottoneAnnulla;
    @FXML
    private Button bottoneConferma;
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField CF;
    @FXML
    private TextField codicePersonale;
    @FXML
    private Label erroreNome;
    @FXML
    private Label erroreCognome;
    @FXML
    private Label erroreCF;
    @FXML
    private Label erroreCodPersonale;

    InViaggio inviaggio = InViaggio.getInstance();

    public void initialize(){
        erroreNome.setVisible(false);
        erroreCognome.setVisible(false);
        erroreCF.setVisible(false);
        erroreCodPersonale.setVisible(false);

    }


        public void onAnnullaClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneAnnulla.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        newStage.setTitle("Benvenuto");
        newStage.setScene(new Scene(root));
        newStage.show();
    }


    public void onConfermaClick(ActionEvent event) throws IOException {
        String name= "a";
        String surname="b";
        String cf="c";
        String codPersonal="d";
        if (!nome.getText().equals("")) {
            name=nome.getText();
            erroreNome.setVisible(false);
        }else{
            erroreNome.setVisible(true);
        }
        if (!cognome.getText().equals("")) {
            surname=cognome.getText();
            erroreCognome.setVisible(false);
        }else{
            erroreCognome.setVisible(true);
        }

        if (!CF.getText().equals("")) {
            cf=CF.getText();
            erroreCF.setVisible(false);
        }else{
            erroreCF.setVisible(true);
        }

        if (!codicePersonale.getText().equals("")) {
            codPersonal=codicePersonale.getText();
            erroreCodPersonale.setVisible(false);
        }else{
            erroreCodPersonale.setVisible(true);
        }

        if(!nome.getText().equals("") && !cognome.getText().equals("") && !CF.getText().equals("") && !codicePersonale.getText().equals("") ){
            inviaggio.registrati(name,surname,cf,codPersonal);
            Stage stage = (Stage) bottoneConferma.getScene().getWindow();
            stage.close();
            Stage newStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            newStage.setTitle("Login Cliente");
            newStage.setScene(new Scene(root));
            newStage.show();
        }
    }


}
