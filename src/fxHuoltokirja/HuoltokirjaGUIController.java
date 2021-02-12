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
   
    @FXML private void handleUusiPyora() {
        uusiPyora();
    }
     
    @FXML private void handlePoistaPyora() {
        poistaPyora();
    }

    @FXML private void handleMuokkaaPyoraa() {
        muokkaaPyoraa();
    }
    
    @FXML private void handleAvaaHuoltokirja() {
        avaaHuoltokirja();
        
    }
    
    @FXML private void handleTulosta() {
        tulosta();
    }

    @FXML private void handleApua() {
        apua();
    }
    
    @FXML private void handleTietoja() {
        tietoja();
    }
    
    @FXML private void handleLopeta() {
        lopeta();
    }



    //=============================================================================================
    
    /**
     * Lisää uuden pyörän.
     */
    private void uusiPyora() {
       Dialogs.showMessageDialog("Lisätään uusi pyörä. Ei osata lisätä vielä!");
    }
    
    
    /**
     * Poistaa pyörän.
     */
    private void poistaPyora() {
        Dialogs.showQuestionDialog("Poisto?",
                "Poistetaanko pyörä: Fuji Rakan", "Kyllä", "Ei");
    }
    
    
    /**
     * Avaa huoltokirjan.
     */
    private void avaaHuoltokirja() {      
        // Suljetaan pyöränvalintadialogi, EI KÄYTÖSSÄ
        // ModalController.closeStage(uusiPyora);
        
        // resurssin lataaminen
        var resurssiHuoltokirjaAuki = HuoltokirjaGUIController.class.getResource("HuoltokirjaAukiGUIView.fxml");
        ModalController.showModal(resurssiHuoltokirjaAuki, "Huoltokirja", null, "");
        
        // Versio, jossa huoltokirjan aukaiseminen on kirjoitettu huoltokirjaAukiGUIControlleriin.       
        //HuoltokirjaAukiGUIController.avaaHuollot(null, kerhonnimi);

    }
    
    
    /**
     * Avaa tulostusikkunan
     */
    private void tulosta() {
        ModalController.showModal(HuoltokirjaGUIController.class.getResource("TulostusView.fxml"),
                "Tulosta", null, "");
    }
    
    
    /**
     * Avaa dialogin, jonka avulla pyörän tietoja muokataan
     */
    private void muokkaaPyoraa() {
        ModalController.showModal(HuoltokirjaDialogGUIController.class.getResource("HuoltokirjaDialogGUIView.fxml"),
                "Pyörän tiedot", null, "");
        
    }
    
    
    /**
     * Avaa verkkosivun, josta löytyy apua. Tässä käytössä HT TIM-sivu.
     */
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
    
    
    /**
     * Avaa dialogin, josta näkee ohjelman tiedot
     */
    private void tietoja() {
        ModalController.showModal(HuoltokirjaGUIController.class.getResource("TietojaView.fxml"),
                "Tietoja", null, "");
        
    }
    
    
    /**
     * Sulkee ohjelman.
     */
    private void lopeta() {
        Dialogs.showMessageDialog("Suljetaan ohjelma. Ei osata!");
    }


   


   
   
    
        
    
}
