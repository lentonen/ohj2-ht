package fxHuoltokirja;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import huoltokirja.Huolto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * HUOM! TÄMÄ EI OLE KÄYTÖSSÄ. KÄYTÖSSÄ ON TIETUEDIALOG.
 * @author hemalein
 * @version 31.3.2021
 *
 */
public class HuoltokirjaAukiDialogGUIController implements ModalControllerInterface<Huolto>, Initializable {
    
    @FXML private TextField textAjotunnit;
    @FXML private TextField textNimi;
    @FXML private TextArea textToimenpiteet;

    
    @Override public Huolto getResult() {
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
    public void setDefault(Huolto oletus) {
       // huoltoKohdalla = oletus;
        naytaHuolto(oletus);
        
    }


    //=============================================================================================


    //private Huolto huoltoKohdalla;
    private void alusta() {
        //
    }

    
    /**
     * Asetetaan muokattava pyörä
     * @param oletus pyörä jonka tietoja halutaan muokata
     */
    //public void setHuolto(Huolto oletus) {
        //huoltoKohdalla = oletus;
   // }
    
    
    private void naytaHuolto(Huolto huolto) {
        naytaHuolto(textNimi, textAjotunnit, textToimenpiteet, huolto);

    }

    
    /**
     * Avaa modaalisen ikkunan, jossa näytetään valitun huollon tiedot
     * @param modalityStage mille ollaan modaalisia
     * @param oletus valittu huolto
     * @return käsitelty pyörä
     */
    public static Huolto muokkaaHuolto(Stage modalityStage, Huolto oletus) {
        return ModalController.showModal(
                HuoltokirjaAukiDialogGUIController.class.getResource("HuoltokirjaAukiDialogGUIView.fxml"),
                "Muokkaa",
                modalityStage, oletus
            ); 
    }

    
    /**
     * Näyttää huollon tiedot tekstikentissä
     * @param nimi kenttä huollon nimelle
     * @param tunnit kenttä huollon tunneille
     * @param toimenpiteet kenttä huollon toimenpiteille
     * @param huolto valittu huolto
     */
    public static void naytaHuolto(TextField nimi, TextField tunnit, TextArea toimenpiteet, Huolto huolto) {
        nimi.setText(huolto.getNimi());
        tunnit.setText(Integer.toString(huolto.getAjotunnit())); 
        toimenpiteet.setText(huolto.getToimenpiteet());
        
    }
}
