package fxHuoltokirja;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


/**
 * @author hemalein
 * @version 25.2.2021
 *
 */
public class HuoltokirjaAukiGUIController implements ModalControllerInterface<String> {
    
    String kerhonnimi = "jotain";
    @FXML private Button lisaaHuolto;

    
    @Override
    public String getResult() {
        return null;
    }

    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        //;    
    }

    @Override
    public void setDefault(String oletus) {
        //;   
    }
    
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
    
    /**
     * Lisää uuden uuden huollon
     */
    private void uusiHuolto() {
       Dialogs.showMessageDialog("Lisätään uusi huolto. Ei osata lisätä vielä!");
    }
    
    
    /**
     * Poistaa huollon
     */
    private void poistaHuolto() {
        Dialogs.showQuestionDialog("Poisto?",
                "Poistetaanko huolto: Takaiskarin huolto", "Kyllä", "Ei");
    }
    
    
    /**
     * Tulostaa valitun pyörän huoltokirjan
     */
    private void tulosta() {
        ModalController.showModal(HuoltokirjaAukiGUIController.class.getResource("TulostusView.fxml"),
                "Tulosta", null, "");  
    }
    
    
    /**
     * Muokataan huollon tietoja
     */
    private void muokkaaHuoltoa() {
        ModalController.showModal(HuoltokirjaDialogGUIController.class.getResource("HuoltokirjaAukiDialogGUIView.fxml"),
                "Huollon tiedot", null, "");
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
        ModalController.showModal(HuoltokirjaAukiGUIController.class.getResource("TietojaView.fxml"),
                "Tietoja", null, "");   
    }
    
    
    /**
     * Sulkee huoltokirjan
     */
    private void lopeta() {
        // Suljetaan huoltokirjadialogi
        ModalController.closeStage(lisaaHuolto);         
        
        //Dialogs.showMessageDialog("Suljetaan ohjelma. Ei osata!");
    }
    
    // Versio, jossa huoltokirjan palautus omana aliohjelmana. Ei käytössä tällä hetkellä.
    
    /**
      * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
      * @param modalityStage mille ollaan modaalisia, null = sovellukselle
      * @param oletus mitä nimeä näytetään oletuksena
      * @return null jos painetaan Cancel, muuten kirjoitettu nimi
      */
      
    /*
    public static String avaaHuollot(Stage modalityStage, String oletus) {
          return ModalController.showModal(
                  HuoltokirjaAukiGUIController.class.getResource("HuoltokirjaAukiGUIView.fxml"),
                  "Huoltokirja",
                  modalityStage, oletus);
          } */
}
