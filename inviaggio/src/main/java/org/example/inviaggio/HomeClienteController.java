package org.example.inviaggio;

import dominio.InViaggio;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;
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
    private Button bottoneModifica;
    @FXML
    private Button bottoneLogout;
    @FXML
    private Label nomeUtenteWelcome;
    @FXML
    private Circle flagNotifica;
    @FXML
    private TextArea messaggioNotifica;
    @FXML
    private Button cancellaAccount;
    @FXML
    private Button bottoneTrasferisci;

    InViaggio inviaggio = InViaggio.getInstance();

    private BooleanProperty valoreNotifica;

    public void initialize() {
        valoreNotifica = new SimpleBooleanProperty(inviaggio.getClienteLoggato().getNotifica());
        flagNotifica.visibleProperty().bind(valoreNotifica);
        nomeUtenteWelcome.setText("Benvenuto, " + inviaggio.getClienteLoggato().getNome());
        messaggioNotifica.setVisible(false);
        messaggioNotifica.setEditable(false);
    }

    public void onPrenotaClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottonePrenota.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("prenotaBiglietto.fxml"));
        newStage.setTitle("Prenota Biglietto");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void onAnnullaClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneAnnulla.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("annullaBiglietto.fxml"));
        newStage.setTitle("Annulla Biglietto");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void onStoricoClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneStorico.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("storicoCliente.fxml"));
        newStage.setTitle("Storico cliente");
        newStage.setScene(new Scene(root));
        newStage.show();

    }

    public void onLogoutClick(ActionEvent event) throws IOException {
        inviaggio.logout();
        Stage stage = (Stage) bottoneLogout.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("loginCliente.fxml"));
        newStage.setTitle("Login Cliente");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void onBottoneNotifica(ActionEvent event) throws IOException {
        if(messaggioNotifica.isVisible()){
            inviaggio.resetNotifiche();
            messaggioNotifica.setVisible(false);
            flagNotifica.visibleProperty().unbind();
            flagNotifica.setVisible(false);
        }else{
            messaggioNotifica.setVisible(true);
            messaggioNotifica.setText(inviaggio.getClienteLoggato().getMessaggio());
        }
    }

    public void onTrasferisciBiglietto(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneTrasferisci.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("trasferisciBiglietto.fxml"));
        newStage.setTitle("Trasferimento biglietto");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void onModificaBiglietto(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneModifica.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("modificaBiglietto.fxml"));
        newStage.setTitle("Modifica biglietto");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void onCancellaAccount(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancellaAccount.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("cancellaAccountCliente.fxml"));
        newStage.setTitle("Cancella Account");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

}
