package fxHuoltokirja;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import huoltokirja.Pyora;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * Kontrolleri pyörän muokkaus/lisäys -dialogille
 * @author hemalein
 * @version 24.3.2021
 */
public class HuoltokirjaDialogGUIController implements ModalControllerInterface<Pyora>, Initializable {
    
    @FXML private GridPane gridPyora;
    @FXML private Label labelVirhe;
    
    @Override public Pyora getResult() {
        return pyoraKohdalla;
    }

    @Override public void handleShown() {
        //textNimi.requestFocus();
        kentta = Math.max(apuPyora.ekaKentta(), Math.min(kentta, apuPyora.getKenttia()));
        texts[this.kentta].requestFocus();
    }

    @FXML void handleOK() {
        if (pyoraKohdalla != null && pyoraKohdalla.getNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        
        ModalController.closeStage(gridPyora);   
    }
    
    @FXML void handleCancel() {
        pyoraKohdalla = null;
        ModalController.closeStage(gridPyora); 
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }


    @Override
    public void setDefault(Pyora oletus) {
        pyoraKohdalla = oletus;
        naytaPyora(oletus);
        
    }


    //=============================================================================================


    private Pyora pyoraKohdalla;                 // Pyörä jota käsitellään
    private TextField[] texts;                   // Taulukko kentille
    private int kentta = 1;                      // Valittu kenttä, oletuksena 1 
    private static Pyora apuPyora = new Pyora(); // Vastaa gridpanelle paljonko kenttiä yms. Static sen vuoksi, että on vakion kaltainen
    
    
    /**
     * Luo gridpaneen jäsenen tiedot
     * @param grid mihin tiedot luodaan
     * @return luodut tekstikentät
     */
    public static TextField[] luoKentat(GridPane grid) {
        grid.getChildren().clear();                                                         // Tyhjentää gridpanen, jos siellä on jotakin ennestään
        TextField[] textFields = new TextField[apuPyora.getKenttia()];                   // +1 sen vuoksi, että ensimmäinen kenttä on 1. Paikkaan 0 jää null-viite
        for (int i = 0, k = apuPyora.ekaKentta(); k < apuPyora.getKenttia(); k++, i++) {
            Label label = new Label(apuPyora.getKentanNimi(k));
            grid.add(label, 0, i);                                                          // Laitetaan label gridissä sarakkeeseen 0 riville i
            TextField text = new TextField();
            textFields[k] = text;
            text.setId("t"+k);                                                              // antaa kentälle id:n t1, t2, t3...
            grid.add(text, 1, i);                                                           // Laitetaan tekstikenttä gridissä sarakkeeseen 1 riville i
        }
        return textFields;
    }
    
    
    private void alusta() {
        //texts = new TextField[]{textNimi, textMerkki, textMalli, textVuosimalli, textRunkoNro};
        texts = luoKentat(gridPyora);   // TODO: tässä jotakin ongelmaa
        for (TextField text : texts) {                     
            if (text != null)           // Tämä poistaa ongelman, jos texts-taulukkoon luodaan johonkin kohtaa
                text.setOnKeyReleased(e -> kasitteleMuutosPyoraan(text));
        }
    }
    
    
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.getStyleClass().setAll("virhe"); //TODO:virhe maalaa punaiseksi
        labelVirhe.setText(virhe);
        
    }
    
    
    /**
     * Palauttaa komponentin id:stä saatavan luvun
     * @param obj tutkittava komponentti
     * @param oletus arvo joka palautetaan, jos id ei ole kunnollinen
     * @return komponentin id lukuna
     */
    public static int getFieldId(Object obj, int oletus) {
        if ( !(obj instanceof Node)) return oletus;                 // Jos ei ole tyyppiä node, niin poistutaan
        Node node = (Node)obj;                                      // typecast nodeksi
        return Mjonot.erotaInt(node.getId().substring(1), oletus);  // erotetaan numero
    }
    
    
    private void kasitteleMuutosPyoraan(TextField text) {
        if (pyoraKohdalla == null) return;                          // Jos pyörää ei ole valittuna, niin lähdetään pois
        int k = getFieldId(text,apuPyora.ekaKentta());
        String s = text.getText();                                  // Haetaan annetun TextFieldin sisältö
        String virhe = pyoraKohdalla.aseta(k, s);                   // Luodaan mj, johon tallennetaan virhetekstit.
        if (virhe == null) {                                        // Mitä tehdään kun syötössä ei tule virheitä
            Dialogs.setToolTipText(text, "");                       // Ei näytetä "tip"-tekstiä
            text.getStyleClass().removeAll("virhe");                // Poistetaan virhe-tyyli käytöstä
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(text, virhe);                    // Näytetään "tip"-teksti
            text.getStyleClass().add("virhe");                      // otetaan virhe-tyyli käyttöön
            naytaVirhe(virhe);
        }
    }

    
    /**
     * Asetetaan muokattava pyörä TODO: POISTA?
     * @param oletus pyörä jonka tietoja halutaan muokata
     */
    public void setPyora(Pyora oletus) {
        this.pyoraKohdalla = oletus;
    }
    
    
    private void naytaPyora(Pyora pyora) {
        naytaPyora(texts, pyora); 
    }
    
    
    /**
     * @param kentta
     */
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }

    
    /**
     * @param modalityStage mille ollaan modaalisia
     * @param pyora Pyörä jota käsitellään
     * @param kentta kenttä joka halutaan aktivoida
     * @return käsitelty pyörä
     */
    public static Pyora muokkaaPyora(Stage modalityStage, Pyora pyora,int kentta) {
        return ModalController.showModal(
                HuoltokirjaDialogGUIController.class.getResource("HuoltokirjaDialogGUIView.fxml"),
                "Muokkaa",
                modalityStage, pyora,
                ctrl -> ((HuoltokirjaDialogGUIController) ctrl).setKentta(kentta)   
            );
    }

    
    /**
     * Täytetään pyörän tiedot tekstikenttiin
     * @param texts taulukko jossa on tekstikenttiä
     * @param pyora näytettävä pyörä
     */
    public static void naytaPyora(TextField[] texts, Pyora pyora) {
        if (pyora == null) return;
        for (int k = pyora.ekaKentta(); k < pyora.getKenttia(); k++)
            texts[k].setText(pyora.anna(k));    
    }  
}
