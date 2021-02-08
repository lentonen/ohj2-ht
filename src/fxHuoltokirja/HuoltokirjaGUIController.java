package fxHuoltokirja;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * @author hemalein
 * @version Jan 15, 2021
 *
 */
public class HuoltokirjaGUIController implements Initializable {
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // 
        
    }
    
    @FXML
    private Button uusiPyora;
    
    private String kerhonnimi = "kelmit";
   
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
        //ModalController.closeStage(uusiPyora);        
        HuoltokirjaAukiGUIController.avaaHuollot(null, kerhonnimi);
                
                
                /*ModalController.showModal(HuoltokirjaGUIController.class.getResource("HuoltokirjaAukiGUIView.fxml"),
                "Huoltokirja", null, "");*/
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


    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
     public static String avaaPyorat(Stage modalityStage, String oletus) {
         return ModalController.showModal(
                 HuoltokirjaGUIController.class.getResource("HuoltokirjaGUIView.fxml"),
                 "Huoltokirja",
                 modalityStage, oletus);
         }
   
        
    
}
