package fxHuoltokirja;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;

/**
 * @author hemalein
 * @version Jan 15, 2021
 *
 */
public class HuoltokirjaGUIController {
   
    @FXML void handleUusiPyora() {
        uusiPyora();
    }
    
    
    @FXML void handlePoistaPyora() {
        poistaPyora();
    }
    
    
    @FXML
    void handleMuokkaaPyoraa() {
        ModalController.showModal(HuoltokirjaGUIController.class.getResource("HuoltokirjaDialogGUIView.fxml"),
                "Huoltokirja", null, "");
    }
    
    
    @FXML
    void handleAvaaHuoltokirja(ActionEvent event) {
        ModalController.showModal(HuoltokirjaGUIController.class.getResource("HuoltokirjaAukiGUIView.fxml"),
                "Huoltokirja", null, "");
    }
    
    
    //=============================================================================================
    
    private void uusiPyora() {
       Dialogs.showMessageDialog("Lisätään uusi pyörä. Ei osata lisätä vielä!");
    }
    
    
    private void poistaPyora() {
        Dialogs.showQuestionDialog("Poisto?",
                "Poistetaanko pyörä: Fuji Rakan", "Kyllä", "Ei");
    }
}
