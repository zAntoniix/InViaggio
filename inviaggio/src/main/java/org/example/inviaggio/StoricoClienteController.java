package org.example.inviaggio;

import dominio.Biglietto;
import dominio.Corsa;
import dominio.InViaggio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

public class StoricoClienteController {
InViaggio inViaggio = InViaggio.getInstance();

    @FXML
    private Button bottoneIndietro;
    @FXML
    private TextArea testo;

    public void initialize() {
        testo.setText("");
        testo.setEditable(false);
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Iterator<Map.Entry<String, Biglietto>> iterator=inViaggio.visualizzaStorico().entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,Biglietto> entry=iterator.next();
                Biglietto b =entry.getValue();
                if (b.getCorsaPrenotata().getTipoMezzo() == 1) {
                    String s = b.getCodice() + " " + "Autobus" + " " + b.getStato() + " " + formatter.format(b.getCorsaPrenotata().getData()) + " " + b.getCorsaPrenotata().getLuogoPartenza() + " " + b.getCorsaPrenotata().getLuogoArrivo() + " " + b.getCorsaPrenotata().getOraPartenza()+"\n";
                    testo.appendText(s);
                } else {
                    String s = b.getCodice() + " " + "Treno" + " " + b.getStato() + " " + formatter.format(b.getCorsaPrenotata().getData()) + " " + b.getCorsaPrenotata().getLuogoPartenza() + " " + b.getCorsaPrenotata().getLuogoArrivo() + " " + b.getCorsaPrenotata().getOraPartenza()+"\n";
                    testo.appendText(s);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void onAnnullaBottone(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneIndietro.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("homeCliente.fxml"));
        newStage.setTitle("Home Page");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

}
