package org.example.inviaggio;

import dominio.InViaggio;
import dominio.Tratta;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginAmministratoreController {

    @FXML
    private Button bottoneAnnulla;
    @FXML
    private Button bottoneConferma;
    @FXML
    private TextField codiceAmministratore;
    @FXML
    private Label pinErrato;

    InViaggio inviaggio = InViaggio.getInstance();

    public void initialize(){
        pinErrato.setVisible(false);

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
        if(!codiceAmministratore.getText().isEmpty()){
            if(inviaggio.accediAmministratore(Integer.parseInt(codiceAmministratore.getText()))){
                Stage stage = (Stage) bottoneConferma.getScene().getWindow();
                stage.close();
                Stage newStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("paginaPrincipaleAmministratore.fxml"));
                newStage.setTitle("Bentornato Amministratore");
                newStage.setScene(new Scene(root));
                newStage.show();
            }
            else{
                pinErrato.setVisible(true);
            }
        }else{
            pinErrato.setVisible(true);
        }
    }

}
