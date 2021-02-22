package fxHuoltokirja;



import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;

import javafx.scene.control.TextArea;


/**
 * @author hemalein
 * @version Jan 15, 2021
 *
 */
public class TulostusController implements ModalControllerInterface<String> {

    @FXML private TextArea tulostusAlue;

    @FXML private void handleOK() {
        ModalController.closeStage(tulostusAlue);
    }

    
    @FXML
    void handleTulosta() {
        Dialogs.showMessageDialog("Ei osata vielä tulostaa");
    }
    
    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void handleShown() {
        //
        
    }

    @Override
    public void setDefault(String oletus) {
        tulostusAlue.setText(oletus);     
    }
    

    /**
     * Saantimetodi tulostusalueelle
     * @return tulostusalue
     */
    public TextArea getTextArea() {
        return tulostusAlue;
    }


    //=============================================================================================
    
    
    /**
     * Näyttää tulostusalueessa tekstin
     * @param tulostus tulostettava teskti
     * @return kontrolleri, jolta voidaan pyytää lisää tietoa
     */
    public static TulostusController tulosta(String tulostus) {
        TulostusController tulostusCtrl = 
          ModalController.showModeless(TulostusController.class.getResource("TulostusView.fxml"),
                                       "Tulostus", tulostus);
        return tulostusCtrl;
    }
    
    
    
}
    
  