package org.example.inviaggio;

import dominio.Biglietto;
import dominio.Corsa;
import dominio.InViaggio;
import dominio.Tratta;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.event.InputMethodEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class ModificaBigliettoController {

    @FXML
    private ListView<String> listaModificabili;
    @FXML
    private ListView<String> listaTratte;
    @FXML
    private ListView<String> listaCorse;
    @FXML
    private Button bottoneIndietro;
    @FXML
    private Button bottoneConferma;
    @FXML
    private Label labelCorse;
    @FXML
    private Label labelTratte;
    @FXML
    private Label erroreData;
    @FXML
    private TextField data;
    @FXML
    private Label labelData;
    @FXML
    private TextArea risultato;

    InViaggio inviaggio = InViaggio.getInstance();
    String codiceModificare;
    String codiceTratta;
    String codiceCorsa;

    public void initialize() {
        listaTratte.setVisible(false);
        listaCorse.setVisible(false);
        bottoneConferma.setVisible(false);
        labelCorse.setVisible(false);
        labelTratte.setVisible(false);
        erroreData.setVisible(false);
        risultato.setVisible(false);
        labelData.setVisible(false);
        data.setVisible(false);
        ObservableList<String> bg = FXCollections.observableArrayList();
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        for(Biglietto b : inviaggio.mostraBigliettiModificabili()){
            String s = " "+b.getCodice() + " " + b.getCorsaPrenotata().getLuogoPartenza() + " " + b.getCorsaPrenotata().getLuogoArrivo() +" " +formatoData.format(b.getCorsaPrenotata().getData()) + " " + b.getCorsaPrenotata().getOraPartenza();
            bg.add(s);
        }
        listaModificabili.getItems().setAll(bg);
        listaModificabili.getSelectionModel().selectedItemProperty().addListener(this::cambiaSceltaModificabili);
        listaTratte.disableProperty().bind(Bindings.createBooleanBinding( //Bindo la listView delle tratte al textField, finchè è vuoto non fa utilizzare la listView
                () -> data.getText().isEmpty(),
                data.textProperty()
        ));
    }

    private void cambiaSceltaModificabili(ObservableValue<? extends String> Observable, String oldVal, String newVal){
        ObservableList<String> elencoSelezionato = listaModificabili.getSelectionModel().getSelectedItems();
        String getElementoSelezionato= (elencoSelezionato.isEmpty())?"Nessun elemento selezionato":elencoSelezionato.toString();
        String[] parte = getElementoSelezionato.split(" ");
        codiceModificare = parte[1];
        inviaggio.selezionaBigliettoDaModificare(codiceModificare);
        ObservableList<String> tr = FXCollections.observableArrayList();
        Iterator<Map.Entry<String, Tratta>> iterator = inviaggio.visualizzaElencoTratte().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Tratta> entry = iterator.next();
            Tratta t = entry.getValue();
            String s = " " + t.getCodTratta() + " " + t.getCittaPartenza() + " " + t.getCittaArrivo();
            tr.add(s);
        }
        labelData.setVisible(true);
        data.setVisible(true);
        labelTratte.setVisible(true);
        listaTratte.getItems().setAll(tr);
        listaTratte.setVisible(true);
        listaTratte.getSelectionModel().selectedItemProperty().addListener(this::cambiaSceltaTratta);
    }

    private void cambiaSceltaTratta(ObservableValue<? extends String> Observable, String oldVal, String newVal){
        listaCorse.getItems().clear();
        listaCorse.setDisable(false);
        ObservableList<String> elencoSelezionato = listaTratte.getSelectionModel().getSelectedItems();
        String getElementoSelezionato= (elencoSelezionato.isEmpty())?"Nessun elemento selezionato":elencoSelezionato.toString();
        String[] parte = getElementoSelezionato.split(" ");
        codiceTratta = parte[1];
        inviaggio.selezionaTratta(codiceTratta);
        if(!data.getText().isEmpty()) {
            try {
                SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
                Date date = formatoData.parse(data.getText()); //Prendo la data dal textbox e la converto in un tipo Date
                ObservableList<String> cs = FXCollections.observableArrayList();
                for (Corsa c : inviaggio.richiediCorsePerData(date)) {
                    String s = " " + c.getCodCorsa() + " " + c.getLuogoPartenza() + " " + c.getLuogoArrivo() + " " + formatoData.format(c.getData()) + " " + c.getOraPartenza() + " " + c.getOraArrivo();
                    cs.add(s);
                }
                if (cs.isEmpty()) {
                    String s = new String("Nessuna corsa presente in questa data");
                    cs.add(s);
                    listaCorse.getItems().addAll(cs);
                    listaCorse.setDisable(true);
                }
                erroreData.setVisible(false);
                labelCorse.setVisible(true);
                listaCorse.getItems().setAll(cs);
                listaCorse.setVisible(true);
                listaCorse.getSelectionModel().selectedItemProperty().addListener(this::cambiaSceltaCorsa);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            erroreData.setVisible(true);
        }

    }

    private void cambiaSceltaCorsa(ObservableValue<? extends String> Observable, String oldVal, String newVal){
        ObservableList<String> elencoSelezionato = listaCorse.getSelectionModel().getSelectedItems();
        String getElementoSelezionato= (elencoSelezionato.isEmpty())?"Nessun elemento selezionato":elencoSelezionato.toString();
        String[] parte = getElementoSelezionato.split(" ");
        codiceCorsa = parte[1];
        bottoneConferma.setVisible(true);
    }

    public void onConfermaBottone(ActionEvent actionEvent) throws IOException {
        bottoneConferma.setVisible(false);
        if(inviaggio.confermaCorsaSostitutiva(codiceCorsa)){
            risultato.setText("Modifica del biglietto "+codiceModificare+ " avvenuta con successo");
            risultato.setVisible(true);
        }else{
            risultato.setText("Procedura fallita, riprova più tardi");
            risultato.setVisible(true);
        }
    }

    public void onBottoneIndietro(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) bottoneIndietro.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("homeCliente.fxml"));
        newStage.setTitle("Benvenuto");
        newStage.setScene(new Scene(root));
        newStage.show();
    }
}
