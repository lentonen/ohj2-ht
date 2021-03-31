package fxHuoltokirja;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import huoltokirja.Tietue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * Kysytään tietueen tiedot dialogilla
 * @author hemalein
 * @version 31.3.2021
 * @param <TYPE> Käsiteltävän tietueen tyyppi
 */
public class TietueDialogController<TYPE extends Tietue> implements ModalControllerInterface<TYPE>, Initializable {
    
    @FXML private GridPane gridTietue;
    @FXML private Label labelVirhe;
    
    
    @Override public TYPE getResult() {
        return tietueKohdalla;
    }

    @Override public void handleShown() {
        //textNimi.requestFocus();
        kentta = Math.max(tietueKohdalla.ekaKentta(), Math.min(kentta, tietueKohdalla.getKenttia()));
        texts[this.kentta].requestFocus();
    }

    @FXML void handleOK() {
        if (tietueKohdalla != null && tietueKohdalla.anna(tietueKohdalla.ekaKentta()).trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        
        ModalController.closeStage(gridTietue);   
    }
    
    @FXML void handleCancel() {
        tietueKohdalla = null;
        ModalController.closeStage(gridTietue); 
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //alusta();
    }


    @Override
    public void setDefault(TYPE oletus) {
        tietueKohdalla = oletus;
        alusta();
        naytaTietue(texts, tietueKohdalla);
        
    }


    //=============================================================================================


    private TYPE tietueKohdalla;                 // Tietue jota käsitellään
    private TextInputControl[] texts;                   // Taulukko kentille
    private int kentta = 1;                      // Valittu kenttä, oletuksena 1 
    
    
    /**
     * Luo gridpaneen jäsenen tiedot
     * @param <TYPE> Tietueen tyyppi
     * @param grid mihin tiedot luodaan
     * @param apuTietue jolta kysytään tietoja
     * @return luodut tekstikentät
     */
    public static<TYPE extends Tietue> TextInputControl[] luoKentat(GridPane grid, TYPE apuTietue) {
        grid.getChildren().clear();                                                         // Tyhjentää gridpanen, jos siellä on jotakin ennestään
            TextInputControl[] textFields = new TextInputControl[apuTietue.getKenttia()];                   
            for (int i = 0, k = apuTietue.ekaKentta(); k < apuTietue.getKenttia(); k++, i++) {
                Label label = new Label(apuTietue.getKentanNimi(k));
                grid.add(label, 0, i);                                                          // Laitetaan label gridissä sarakkeeseen 0 riville i
                if (k < apuTietue.ekaIsoKentta()) {
                    TextField text = new TextField();
                    textFields[k] = text;
                    text.setId("t"+k);                                                              // antaa kentälle id:n t1, t2, t3...
                    grid.add(text, 1, i);
                }
                else {
                    TextArea text = new TextArea();
                    textFields[k] = text;
                    text.setId("t"+k);                                                              // antaa kentälle id:n t1, t2, t3...
                    grid.add(text, 1, i);
                }
                                                                           // Laitetaan tekstikenttä gridissä sarakkeeseen 1 riville i
        }
        return textFields;  
    }
    
    
    private void alusta() {
        texts = luoKentat(gridTietue, tietueKohdalla); 
        for (TextInputControl text : texts) {                     
            if (text != null)           // Tämä poistaa ongelman, jos texts-taulukkoon luodaan johonkin kohtaa
                text.setOnKeyReleased(e -> kasitteleMuutosTietueeseen(text));
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
    
    
    private void kasitteleMuutosTietueeseen(TextInputControl text) {
        if (tietueKohdalla == null) return;                          // Jos tietue ei ole valittuna, niin lähdetään pois
        int k = getFieldId(text,tietueKohdalla.ekaKentta());
        String s = text.getText();                                  // Haetaan annetun TextFieldin sisältö
        String virhe = tietueKohdalla.aseta(k, s);                   // Luodaan mj, johon tallennetaan virhetekstit.
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
     * Asetetaan muokattava tietue TODO: POISTA?
     * @param oletus tietue jonka tietoja halutaan muokata
     */
    public void setTietue(TYPE oletus) {
        this.tietueKohdalla = oletus;
    }  
    
    
    /**
     * @param kentta
     */
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }

    
    /**
     * @param <TYPE> Tietueen tyyppi
     * @param modalityStage mille ollaan modaalisia
     * @param oletus tietue jota halutaan käsitellä
     * @param kentta kenttä joka halutaan aktivoida
     * @return käsitelty tietue
     */
    public static<TYPE extends Tietue> TYPE muokkaaTietue(Stage modalityStage, TYPE oletus,int kentta) {
        return ModalController.<TYPE, TietueDialogController<TYPE>>showModal(
                TietueDialogController.class.getResource("TietueDialogView.fxml"),
                "Muokkaa",
                modalityStage, oletus,
                ctrl ->  {ctrl.setKentta(kentta);}   
            );
    }

    
    /**
     * Täytetään pyörän tiedot tekstikenttiin
     * @param texts taulukko jossa on tekstikenttiä
     * @param tietue näytettävä tietue
     */
    public static void naytaTietue(TextInputControl[] texts, Tietue tietue) {
        if (tietue == null) return;
        for (int k = tietue.ekaKentta(); k < tietue.getKenttia(); k++)
            texts[k].setText(tietue.anna(k));    
    }  
}
