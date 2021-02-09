package fxHuoltokirja;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;



import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;

import javafx.scene.control.Button;



/**
 * @author hemalein
 * @version Jan 15, 2021
 *
 */
public class HuoltokirjaGUIController {
    
    
    @FXML private Button uusiPyora;
   
    @FXML void handleUusiPyora() {
        uusiPyora();
    }
    
    
    @FXML void handlePoistaPyora() {
        poistaPyora();
    }
    
    
    @FXML
    void handleMuokkaaPyoraa() {
        muokkaaPyoraa();
    }
    
    
    @FXML
    void handleAvaaHuoltokirja() {
        avaaHuoltokirja();
        
    }
    
    
    @FXML
    void handleTulosta() {
        tulosta();
    }
    
    
    @FXML
    void handleApua() {
        apua();
    }
    
    
    @FXML
    void handleTietoja() {
        tietoja();
    }
    
    
    @FXML
    void handleLopeta() {
        lopeta();
    }



    //=============================================================================================
    
    private void uusiPyora() {
       Dialogs.showMessageDialog("Lisätään uusi pyörä. Ei osata lisätä vielä!");
    }
    
    
    private void poistaPyora() {
        Dialogs.showQuestionDialog("Poisto?",
                "Poistetaanko pyörä: Fuji Rakan", "Kyllä", "Ei");
    }
    
    private void avaaHuoltokirja() {      
        // Suljetaan pyöränvalintadialogi, EI KÄYTÖSSÄ
        // ModalController.closeStage(uusiPyora);
        
        // resurssin lataaminen
        var resurssiHuoltokirjaAuki = HuoltokirjaGUIController.class.getResource("HuoltokirjaAukiGUIView.fxml");
        ModalController.showModal(resurssiHuoltokirjaAuki, "Huoltokirja", null, "");
        
        // Versio, jossa huoltokirjan aukaiseminen on kirjoitettu huoltokirjaAukiGUIControlleriin.       
        //HuoltokirjaAukiGUIController.avaaHuollot(null, kerhonnimi);
        
        
    }
    
    
    private void tulosta() {
        ModalController.showModal(HuoltokirjaGUIController.class.getResource("TulostusView.fxml"),
                "Tulosta", null, "");
        
    }
    
    
    private void muokkaaPyoraa() {
        ModalController.showModal(HuoltokirjaDialogGUIController.class.getResource("HuoltokirjaDialogGUIView.fxml"),
                "Pyörän tiedot", null, "");
        
    }
    
    
    private void apua() {
            Desktop desktop = Desktop.getDesktop();
            try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2021k/ht/hemalein");
            desktop.browse(uri);
            } catch (URISyntaxException e) {
            return;
            } catch (IOException e) {
            return;
            }
    }
    
    
    private void tietoja() {
        ModalController.showModal(HuoltokirjaGUIController.class.getResource("TietojaView.fxml"),
                "Tietoja", null, "");
        
    }
    
    
    private void lopeta() {
        Dialogs.showMessageDialog("Suljetaan ohjelma. Ei osata!");
    }


   


   
   
    
        
    
}
