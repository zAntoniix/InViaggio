package org.example.inviaggio;

import dominio.InViaggio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeClienteController {

    @FXML
    private Button bottonePrenota;
    @FXML
    private Button bottoneAnnulla;
    @FXML
    private Button bottoneStorico;
    @FXML
    private Button bottoneNotifiche;
    @FXML
    private Button bottoneLogout;
    @FXML
    private Label nomeUtenteWelcome;

    InViaggio inviaggio = InViaggio.getInstance();

    public void initialize() {
        nomeUtenteWelcome.setText("Benvenuto, " + inviaggio.getClienteLoggato().getNome());
    }

    public void onPrenotaClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottonePrenota.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("prenotaBiglietto.fxml"));
        newStage.setTitle("Prenota Biglietto");
        newStage.setScene(new Scene(root, 1080,720));
        newStage.show();
    }

    public void onAnnullaClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneAnnulla.getScene().getWindow();
    }

    public void onStoricoClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneStorico.getScene().getWindow();
    }

    public void onLogoutClick(ActionEvent event) throws IOException {
        inviaggio.logout();
        Stage stage = (Stage) bottoneLogout.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("loginCliente.fxml"));
        newStage.setTitle("Login Cliente");
        newStage.setScene(new Scene(root, 1080,720));
        newStage.show();
    }
}
