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

public class LoginClienteController {

    @FXML
    private TextField cf;
    @FXML
    private Label cfErrato;
    @FXML
    private TextField password;
    @FXML
    private Label passErrata;
    @FXML
    private Button bottoneAnnulla;
    @FXML
    private Button bottoneConferma;

    InViaggio inviaggio = InViaggio.getInstance();

    public void initialize() {
        cfErrato.setVisible(false);
        passErrata.setVisible(false);
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
        if(!cf.getText().isEmpty() && !password.getText().isEmpty()) {
            if(inviaggio.accedi(cf.getText(), password.getText())){
                Stage stage = (Stage) bottoneConferma.getScene().getWindow();
                stage.close();
                Stage newStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("homeCliente.fxml"));
                newStage.setTitle("Home Page");
                newStage.setScene(new Scene(root));
                newStage.show();
            } else {
                cfErrato.setVisible(true);
                passErrata.setVisible(true);
            }
        } else {
            cfErrato.setVisible(true);
            passErrata.setVisible(true);
        }
    }
}
