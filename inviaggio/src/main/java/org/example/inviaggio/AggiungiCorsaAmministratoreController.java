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
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AggiungiCorsaAmministratoreController {
    @FXML
    private Button bottoneAnnulla;
    @FXML
    private Button bottoneConferma;
    @FXML
    private TextField tipoMezzo;
    @FXML
    private TextField data;
    @FXML
    private TextField luogoPartenza;
    @FXML
    private TextField luogoArrivo;
    @FXML
    private TextField costoBase;
    @FXML
    private TextField oraPartenza;
    @FXML
    private TextField oraArrivo;
    @FXML
    private TextField codiceTratta;
    @FXML
    private Label erroreTipo;
    @FXML
    private Label erroreData;
    @FXML
    private Label errorePartenza;
    @FXML
    private Label erroreArrivo;
    @FXML
    private Label erroreOraPartenza;
    @FXML
    private Label erroreOraArrivo;
    @FXML
    private Label erroreCosto;
    @FXML
    private Label erroreTratta;
    @FXML
    private Label labelTratta;

    InViaggio inviaggio = InViaggio.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
    String datePattern = "^\\d{2}/\\d{2}/\\d{4}$";
    Pattern patternData = Pattern.compile(datePattern);
    String timePattern = "^\\d{2}:\\d{2}:\\d{2}$";
    Pattern patternTime = Pattern.compile(timePattern);

    public void initialize() {
        erroreTipo.setVisible(false);
        erroreData.setVisible(false);
        errorePartenza.setVisible(false);
        erroreArrivo.setVisible(false);
        erroreOraPartenza.setVisible(false);
        erroreOraArrivo.setVisible(false);
        erroreCosto.setVisible(false);
        erroreTratta.setVisible(false);
        codiceTratta.setVisible(false);
        labelTratta.setVisible(false);
        if(inviaggio.getTrattaCorrente()==null){
            codiceTratta.setVisible(true);
            labelTratta.setVisible(true);
        }
    }

    public void onBottoneConferma(ActionEvent event) throws IOException {

        if(tipoMezzo.getText().isEmpty()){
            erroreTipo.setVisible(true);
        }else{
            erroreTipo.setVisible(false);
        }

        if(data.getText().isEmpty() || !patternData.matcher(data.getText()).matches()){
            erroreData.setVisible(true);
        }else{
            erroreData.setVisible(false);
        }

        if(luogoPartenza.getText().isEmpty()){
            errorePartenza.setVisible(true);
        }else{
            errorePartenza.setVisible(false);
        }

        if(luogoArrivo.getText().isEmpty()){
            erroreArrivo.setVisible(true);
        }else{
            erroreArrivo.setVisible(false);
        }

        if(costoBase.getText().isEmpty()){
            erroreCosto.setVisible(true);
        }else{
            erroreCosto.setVisible(false);
        }

        if(oraPartenza.getText().isEmpty() || !patternTime.matcher(oraPartenza.getText()).matches()){
            erroreOraPartenza.setVisible(true);
        }else{
            erroreOraPartenza.setVisible(false);
        }

        if(oraArrivo.getText().isEmpty() || !patternTime.matcher(oraArrivo.getText()).matches()){
            erroreOraArrivo.setVisible(true);
        }else{
            erroreOraArrivo.setVisible(false);
        }

        if(!tipoMezzo.getText().isEmpty() && !data.getText().isEmpty() && !luogoPartenza.getText().isEmpty() && !luogoArrivo.getText().isEmpty()
        && !costoBase.getText().isEmpty() && !oraPartenza.getText().isEmpty() && !oraArrivo.getText().isEmpty()) {
            try {
                LocalTime localTimePartenza = LocalTime.parse(oraPartenza.getText(), formatterTime);
                LocalTime localTimeArrivo = LocalTime.parse(oraArrivo.getText(), formatterTime);
                Time timePartenza = Time.valueOf(localTimePartenza);
                Time timeArrivo = Time.valueOf(localTimeArrivo);
                Date dataCorsa = formatter.parse(data.getText());
                if(inviaggio.getTrattaCorrente()!=null){
                    inviaggio.inserisciCorsa(Integer.parseInt(tipoMezzo.getText()),dataCorsa,luogoPartenza.getText(),luogoArrivo.getText(),timePartenza ,timeArrivo ,Float.valueOf(costoBase.getText()).floatValue());
                    Stage stage = (Stage) bottoneConferma.getScene().getWindow();
                    stage.close();
                    Stage newStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("paginaPrincipaleAmministratore.fxml"));
                    newStage.setTitle("Bentornato Amministratore");
                    newStage.setScene(new Scene(root, 1080,720));
                    newStage.show();
                }
                else{
                    if(!codiceTratta.getText().isEmpty()){
                        if(inviaggio.inserisciNuovaCorsa(codiceTratta.getText())){
                            inviaggio.inserisciCorsa(Integer.parseInt(tipoMezzo.getText()),dataCorsa,luogoPartenza.getText(),luogoArrivo.getText(),timePartenza ,timeArrivo ,Float.parseFloat(costoBase.getText()));
                            Stage stage = (Stage) bottoneConferma.getScene().getWindow();
                            stage.close();
                            Stage newStage = new Stage();
                            Parent root = FXMLLoader.load(getClass().getResource("paginaPrincipaleAmministratore.fxml"));
                            newStage.setTitle("Bentornato Amministratore");
                            newStage.setScene(new Scene(root, 1080,720));
                            newStage.show();
                        }
                        else{
                            erroreTratta.setVisible(true);
                        }
                    }else{
                        erroreTratta.setVisible(true);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onAnnullaClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneAnnulla.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("paginaPrincipaleAmministratore.fxml"));
        newStage.setTitle("Bentornato Amministratore");
        newStage.setScene(new Scene(root, 1080,720));
        newStage.show();
    }
}
