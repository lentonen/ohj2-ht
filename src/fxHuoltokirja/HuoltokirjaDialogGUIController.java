package fxHuoltokirja;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import huoltokirja.Pyora;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * @author hemalein
 * @version 19.3.2021
 *
 */
public class HuoltokirjaDialogGUIController implements ModalControllerInterface<Pyora>, Initializable {
    
    @FXML private TextField textNimi;
    @FXML private TextField textMerkki;
    @FXML private TextField textMalli;
    @FXML private TextField textVuosimalli;
    @FXML private TextField textRunkoNro;
    
    @Override public Pyora getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public void handleShown() {
        textNimi.requestFocus();
    }

    @FXML void handleOK() {
        ModalController.closeStage(textNimi);   
    }
    
    @FXML void handleCancel() {
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
    }

    
    /**
     * Asetetaan muokattava pyörä
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
