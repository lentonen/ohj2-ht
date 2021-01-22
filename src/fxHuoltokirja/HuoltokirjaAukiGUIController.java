package fxHuoltokirja;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;

/**
 * @author hemalein
 * @version Jan 15, 2021
 *
 */
public class HuoltokirjaAukiGUIController {
   
    @FXML void handleUusiHuolto() {
        uusiHuolto();
    }
    
    
    @FXML void handlePoistaHuolto() {
        poistaHuolto();
    }
    
    
    @FXML
    void handleMuokkaaHuoltoa() {
        muokkaaHuoltoa();
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
    
    private void uusiHuolto() {
       Dialogs.showMessageDialog("Lisätään uusi huolto. Ei osata lisätä vielä!");
    }
    
    
    private void poistaHuolto() {
        Dialogs.showQuestionDialog("Poisto?",
                "Poistetaanko huolto: Takaiskarin huolto", "Kyllä", "Ei");
    }
    
    
    private void tulosta() {
        ModalController.showModal(HuoltokirjaAukiGUIController.class.getResource("TulostusView.fxml"),
                "Tulosta", null, "");
        
    }
    
    
    private void muokkaaHuoltoa() {
        ModalController.showModal(HuoltokirjaDialogGUIController.class.getResource("HuoltokirjaAukiDialogGUIView.fxml"),
                "Huollon tiedot", null, "");
        
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
        ModalController.showModal(HuoltokirjaAukiGUIController.class.getResource("TietojaView.fxml"),
                "Tietoja", null, "");
        
    }
    
    
    private void lopeta() {
        Dialogs.showMessageDialog("Suljetaan ohjelma. Ei osata!");
    }
        
    
}
