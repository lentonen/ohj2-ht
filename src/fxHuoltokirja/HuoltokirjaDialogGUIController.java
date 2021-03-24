package fxHuoltokirja;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import huoltokirja.Pyora;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * @author hemalein
 * @version 23.3.2021
 *
 */
public class HuoltokirjaDialogGUIController implements ModalControllerInterface<Pyora>, Initializable {
    
    @FXML private TextField textNimi;
    @FXML private TextField textMerkki;
    @FXML private TextField textMalli;
    @FXML private TextField textVuosimalli;
    @FXML private TextField textRunkoNro;
    @FXML private Label labelVirhe;
    
    @Override public Pyora getResult() {
        return pyoraKohdalla;
    }

    @Override public void handleShown() {
        textNimi.requestFocus();
    }

    @FXML void handleOK() {
        if (pyoraKohdalla != null && pyoraKohdalla.getNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        
        ModalController.closeStage(textNimi);   
    }
    
    @FXML void handleCancel() {
        pyoraKohdalla = null;
        ModalController.closeStage(textNimi); 
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


    private Pyora pyoraKohdalla;
    @FXML private TextField[] texts;
    
    
    private void alusta() {
        texts = new TextField[]{textNimi, textMerkki, textMalli, textVuosimalli, textRunkoNro};
        int i = 0;
        for (TextField text : texts) {
            final int k = ++i;
            text.setOnKeyReleased(e -> kasitteleMuutosPyoraan(k, (TextField)e.getSource()));
        }
    }
    
    
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    
    private void kasitteleMuutosPyoraan(int k, TextField text) {
        if (pyoraKohdalla == null) return;                          // Jos pyörää ei ole valittuna, niin lähdetään pois
        String s = text.getText();                                  // Haetaan annetun TextFieldin sisältö
        String virhe = null;                                        // Luodaan mj, johon tallennetaan virhetekstit.
        switch (k) {                                                // Switch suorittaa casen sen mukaan, mikä k tuodaan parametrina.
        case 1 : virhe = pyoraKohdalla.setNimi(s); break;
        case 2 : virhe = pyoraKohdalla.setMerkki(s); break;
        case 3 : virhe = pyoraKohdalla.setMalli(s); break;
        case 4 : virhe = pyoraKohdalla.setMalli(s); break;
        case 5 : virhe = pyoraKohdalla.setVuosimalli(s); break;
        case 6 : virhe = pyoraKohdalla.setRunkoNro(s); break;
        default:
        }
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
     * @param modalityStage mille ollaan modaalisia
     * @param pyora Pyörä jota käsitellään
     * @return käsitelty pyörä
     */
    public static Pyora muokkaaPyora(Stage modalityStage, Pyora pyora) {
        return ModalController.showModal(
                HuoltokirjaDialogGUIController.class.getResource("HuoltokirjaDialogGUIView.fxml"),
                "Muokkaa",
                modalityStage, pyora
            );
        
    }

    
    /**
     * Täytetään pyörän tiedot tekstikenttiin
     * @param texts taulukko jossa on tekstikenttiä
     * @param pyora näytettävä pyörä
     */
    public static void naytaPyora(TextField[] texts, Pyora pyora) {
        if (pyora == null) return;
        texts[0].setText(pyora.getNimi());
        texts[1].setText(pyora.getMerkki());
        texts[2].setText(pyora.getMalli());
        texts[3].setText(Integer.toString(pyora.getVuosimalli()));
        texts[4].setText(pyora.getRunkoNro());    
    }  
}
