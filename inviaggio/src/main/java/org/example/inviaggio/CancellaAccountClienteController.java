package org.example.inviaggio;

import dominio.InViaggio;
import dominio.Tratta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Iterator;
import java.util.Map;

public class CancellaAccountClienteController {

    @FXML
    private Button bottoneAnnulla;
    @FXML
    private Button bottoneConferma;
    @FXML
    private Label erroreCancellazione;
    @FXML
    private Label erroreCodPersonale;
    @FXML
    private Label erroreCF;
    @FXML
    private TextField codiceFiscale;
    @FXML
    private TextField codicePersonale;

    InViaggio inViaggio = InViaggio.getInstance();

    public void initialize() {
        erroreCancellazione.setVisible(false);
        erroreCF.setVisible(false);
        erroreCodPersonale.setVisible(false);
    }

    public void onConfermaClick(ActionEvent event) throws IOException {
        if (codiceFiscale.getText().isEmpty()) {
            erroreCF.setVisible(true);

        }else{
            erroreCF.setVisible(false);
        }
        if (codicePersonale.getText().isEmpty()) {
            erroreCodPersonale.setVisible(true);
        }else{
            erroreCodPersonale.setVisible(false);
        }
        if(!codiceFiscale.getText().isEmpty() && !codicePersonale.getText().isEmpty()){
            if(inViaggio.rimuovi(codiceFiscale.getText(), codicePersonale.getText())){
                Stage stage = (Stage) bottoneConferma.getScene().getWindow();
                stage.close();
                Stage newStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                newStage.setTitle("Benvenuto");
                newStage.setScene(new Scene(root));
                newStage.show();
            }else{
                erroreCancellazione.setVisible(true);
            }
        }
    }


    public void onAnnullaClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneAnnulla.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("homeCliente.fxml"));
        newStage.setTitle("Benvenuto");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

}
