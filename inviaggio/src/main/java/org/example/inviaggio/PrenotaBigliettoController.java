package org.example.inviaggio;

import dominio.Biglietto;
import dominio.Corsa;
import dominio.InViaggio;
import dominio.Tratta;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PrenotaBigliettoController {
    InViaggio inviaggio = InViaggio.getInstance();
    Tratta t;
    String codice;

    @FXML
    private Label nomeUtente;
    @FXML
    private Label data;
    @FXML
    private Label nomeCorse;
    @FXML
    private ListView<String> corseDisponibili;
    @FXML
    private ListView<String> menuTratte;
    @FXML
    private Label nomeView;
    @FXML
    private Label erroreData;
    @FXML
    private Button bottoneScelta;
    @FXML
    private Button bottoneSceltaCorsa;
    @FXML
    private Button bottoneSceltaBiglietto;
    @FXML
    private TextField dataScelta;
    @FXML
    private Label erroreCorsa;
    @FXML
    private TextArea biglietto;
    @FXML
    private Label viewBiglietto;
    @FXML
    private Button bottoneBack;



// mostro tutte le tratte ed indico cosa deve accadere ogni volta che clicco una tratta
    public void initialize(){
        nomeUtente.setText(inviaggio.getClienteLoggato().getNome());
        //Utilizzo il metodo visualizzaElencoTratte per rispettare l'SD. Utilizzo l'iteretor per andare a scorrere la mappa
        ObservableList<String> tr = FXCollections.observableArrayList();
        Iterator<Map.Entry<String,Tratta>>iterator=inviaggio.visualizzaElencoTratte().entrySet().iterator();
        while(iterator.hasNext()){ //Scorro la mappa delle Tratte
            Map.Entry<String,Tratta> entry=iterator.next();
            Tratta t=entry.getValue();
            String s = new String(" "+t.getCodTratta()+" "+t.getCittaPartenza()+" "+t.getCittaArrivo() + " "); //Creo la stringa dalle informazioni della singola tratta
            //Nota ho messo lo spazio perchè il primo carattere è [ mettendo lo spazio vado a toglierlo con il parse [vedi dopo]
            tr.add(s); //Aggiungo tutto nella lista di stringhe Observable
        }
        menuTratte.getItems().addAll(tr); //Riempio la ListView
        menuTratte.getSelectionModel().selectedItemProperty().addListener(this::cambiaSceltaLista); //Abilito la funzione che deve eseguire ogni volta che cambia elemento
        corseDisponibili.setVisible(false);
        bottoneSceltaCorsa.setVisible(false);
        nomeCorse.setVisible(false);
        bottoneSceltaBiglietto.setVisible(false);
        biglietto.setVisible(false);
        viewBiglietto.setVisible(false);

    }

    //Cosa accade ogni volta che clicco una tratta.. ossia vado a prendere il codice relativo alla tratta selezionata
    private void cambiaSceltaLista(ObservableValue<? extends String> Observable, String oldVal, String newVal){
        ObservableList<String> elencoSelezionato = menuTratte.getSelectionModel().getSelectedItems();
        String getElementoSelezionato= (elencoSelezionato.isEmpty())?"Nessun elemento selezionato":elencoSelezionato.toString();
        bottoneSceltaCorsa.setVisible(false);
        dataScelta.setVisible(true);
        data.setVisible(true);
        bottoneScelta.setVisible(true);
        corseDisponibili.getItems().clear();
        String[] parte = getElementoSelezionato.split(" "); //Serve per spezzettare la stringa in un array di stringhe, crea una stringa ogni spazio
        codice = parte[1]; //Prendo il secondo elemento perchè il primo contiene [
    }

    //Appena clicco il tasto vado a vedere se è presente la data, se è presente stampo tutte le corse
    public void onConfermaBottone(ActionEvent event) throws IOException {
        if(menuTratte.getSelectionModel().isEmpty()){
            erroreData.setText("Seleziona una Tratta");
        } else {
            if (!dataScelta.getText().equals("")) { //Controllo se il campo della data non è vuoto se no da errore
                erroreData.setVisible(false);
                bottoneScelta.setVisible(false);//Nascondo il bottone che cerca la tratta per evitare errori
                dataScelta.setVisible(false);
                data.setVisible(false);
                nomeCorse.setVisible(true);
                ObservableList<String> cr = FXCollections.observableArrayList();
                List<Corsa> listaCorse = new ArrayList<Corsa>();
                t = inviaggio.selezionaTratta(codice);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //Permette di dare il patter alla data
                try {
                    Date date = formatter.parse(dataScelta.getText()); //Prendo la data dal textbox e la converto in un tipo Date
                    inviaggio.richiediCorsePerData(date);
                    listaCorse = inviaggio.richiediCorsePerData(date); //Uso il metodo richiediCorsePerData per farmi ritornare la lista delle corse per quella data rispettando l'SD
                    corseDisponibili.setVisible(true);
                    if (listaCorse.isEmpty()) {
                        bottoneSceltaCorsa.setVisible(false);
                        String s = new String("Nessuna corsa presente in questa data");
                        cr.add(s);
                        corseDisponibili.getItems().addAll(cr);
                        corseDisponibili.setDisable(true);
                    } else {
                        corseDisponibili.setDisable(false);
                        bottoneSceltaCorsa.setVisible(true);
                        for (Corsa c : listaCorse) { //Scorro la lista di corse
                            if (c.getTipoMezzo() == 1) {
                                String s = new String(" " + c.getCodCorsa() + " " + "Autobus" + " " + c.getLuogoPartenza() + " " + c.getLuogoArrivo() + " " + c.getOraPartenza().toString() + " " + c.getOraArrivo().toString() + " " + c.getCostoBase() + "€");
                                cr.add(s);
                            } else {
                                String s = new String(" " + c.getCodCorsa() + " " + "Treno" + " " + c.getLuogoPartenza() + " " + c.getLuogoArrivo() + " " + c.getOraPartenza().toString() + " " + c.getOraArrivo().toString() + " " + c.getCostoBase() + "€");
                                cr.add(s);
                            }
                        }
                        corseDisponibili.getItems().addAll(cr);
                    }
                } catch (ParseException e) {
                    erroreData.setText("Inserire Data nel fotmato gg/mm/aaaa");
                    erroreData.setVisible(true);
                    dataScelta.setVisible(true);
                    data.setVisible(true);
                    bottoneScelta.setVisible(true);
                    corseDisponibili.setVisible(false);
                    nomeCorse.setVisible(false);
                }
            } else {
                erroreData.setText("Inserire Data nel fotmato gg/mm/aaaa");
            }
        }
    }

    //comportamento appena clicco il tasto per confermare la corsa
    public void onConfermaBottoneCorsa(ActionEvent event) throws IOException {
        ObservableList<String> elencoSelezionato = corseDisponibili.getSelectionModel().getSelectedItems();
        String getElementoSelezionato= (elencoSelezionato.isEmpty())?"Nessun elemento selezionato":elencoSelezionato.toString();

        if(corseDisponibili.getSelectionModel().isEmpty()){
            erroreCorsa.setText("Selezionare una corsa");
            erroreCorsa.setVisible(true);
        }else {
            menuTratte.setVisible(false);
            nomeView.setVisible(false);
            data.setVisible(false);
            dataScelta.setVisible(false);
            erroreData.setVisible(false);
            erroreCorsa.setVisible(false);
            bottoneSceltaCorsa.setVisible(false);
            nomeCorse.setVisible(false);
            corseDisponibili.setVisible(false);
            viewBiglietto.setVisible(true);
            biglietto.setVisible(true);
            String[] parte = getElementoSelezionato.split(" ");
            codice = parte[1];
            Corsa c = t.selezionaCorsa(codice);
            String codiceBiglietto = inviaggio.generaCodBiglietto();
            float costoBase = c.getCostoBase();
            Biglietto b = new Biglietto(codiceBiglietto, costoBase, c);
            inviaggio.setBigliettoCorrente(b);
            // Inizio creazione Stringa
            if(c.getTipoMezzo() == 1) {
                String s = new String("Tipologia Mezzo: Autobus \n" + "Luogo Partenza: " + c.getLuogoPartenza() + "\n" + "Luogo Arrivo: "+
                        c.getLuogoArrivo() + "\n" +"Data: "+ c.getData() + "\n"+"Ora partenza: "+ c.getOraPartenza().toString() + "\n"+"Ora Arrivo: " + c.getOraArrivo().toString()+ "\n"+"Costo totale: "+b.getCostoFinale()+"€");
                biglietto.setText(s);
            } else {
                String s = new String("Tipologia Mezzo: Treno \n" + "Luogo Partenza: " + c.getLuogoPartenza() + "\n" + "Luogo Arrivo: "+
                        c.getLuogoArrivo() + "\n" +"Data: "+ c.getData() + "\n"+"Ora partenza: "+ c.getOraPartenza().toString() + "\n"+"Ora Arrivo: " + c.getOraArrivo().toString()+ "\n"+"Costo totale: "+b.getCostoFinale()+"€");
                biglietto.setText(s);
            }
            biglietto.setEditable(false);
            bottoneSceltaBiglietto.setVisible(true);//Visualizzo il bottone del conferma biglietto
        }
    }

    //comportamento non appena viene premuto il tasto conferma biglietto
    public void onConfermaBottoneBiglietto(ActionEvent event) throws IOException {
        inviaggio.confermaBiglietto();
        System.out.println(inviaggio.getClienteLoggato().getElencoBiglietti());
        Stage stage = (Stage) bottoneSceltaBiglietto.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("homeCliente.fxml"));
        newStage.setTitle("Home Page");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

    public void onBackClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) bottoneBack.getScene().getWindow();
        stage.close();
        Stage newStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("homeCliente.fxml"));
        newStage.setTitle("Home Page");
        newStage.setScene(new Scene(root));
        newStage.show();
    }

}
