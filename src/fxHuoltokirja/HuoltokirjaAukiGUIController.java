package fxHuoltokirja;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import huoltokirja.ApuException;
import huoltokirja.Huolto;
import huoltokirja.Huoltokirja;
import huoltokirja.Pyora;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * @author hemalein
 * @version 19.3.2021
 *
 */
public class HuoltokirjaAukiGUIController implements ModalControllerInterface<Pyora>, Initializable {
    
    @FXML private ScrollPane panelHuolto;  // Pistetään tähän paneen väliaikaiset tiedot huolloista.
    @FXML private Button lisaaHuolto;
    @FXML private ListChooser<Huolto> chooserHuollot;
    @FXML private TextField labelHakuEhto;
    
    @FXML void handleUusiHuolto() {
        uusiHuolto();
    }
    
    @FXML void handlePoistaHuolto() {
        poistaHuolto();
    }
    
    @FXML void handleMuokkaaHuoltoa() {
        muokkaaHuoltoa();
    }

    @FXML void handleTulosta() {
        tulosta();
    }
    
    @FXML void handleApua() {
        apua();
    }
    
    @FXML void handleTietoja() {
        tietoja();
    }
    
    @FXML void handleLopeta() {
        lopeta();
    }
    
    @FXML void handleHaku() {
        hae();
    }
    

    /**
     * Palauttaa dialogin tuloksen sulkemisen jälkeen.
     */
    @Override public Pyora getResult() {
        return null;
    }

    /**
     * Mitä tehdään kun dialogi on näytetty.
     */
    @Override
    public void handleShown() {
        // Älä tee mitään    
    }

    /**
     * Mitä näytetään oletuksena.
     */
    @Override
    public void setDefault(Pyora oletus) {
        //;   
    }


    //=============================================================================================
    
    
    private Pyora pyoraKohdalla; // Pyörä jonka huoltoja käsitellään. Tuodaan avaamisessa.
    private Huolto huoltoKohdalla;
    private Huoltokirja huoltokirja; // Käytössä oleva huoltokirja, joka tuodaan kun huoltokirja avataan
    private TextArea huollonTiedot = new TextArea();
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        panelHuolto.setContent(huollonTiedot);                   // Korvaa alkuperäisen suunnitelman mukaisen alueen omalla väliaikaisella textArealla.
        // pyoranTiedot.setFont(new Font("Courier New", 12));    // TODO: säädä fontit lopuksi
        panelHuolto.setFitToHeight(true);                        // Kenttä kasvaa koko alueen kokoiseksi
        chooserHuollot.clear(); 
        chooserHuollot.addSelectionListener(e -> naytaHuolto()); // lambda-lauseke. Kun valitaan listasta, niin suoritetaan funktio e joka suorittaa naytaHuolto();
        }
    
    
    /**
     * Näyttää listasta valitun huollon tiedot
     */
    private void naytaHuolto() {
        huoltoKohdalla = chooserHuollot.getSelectedObject();   // Hakee muuttujaan listasta valitun huollon
        if (huoltoKohdalla == null) return;                    // Huolehtii siitä, jos valitaan kohta jossa ei ole huoltoa
        
        huollonTiedot.setText("");                             // Tämä sen vuoksi, että edellisen pyörän tiedot saadaan pois näytöltä.
        
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(huollonTiedot)) {   // Haetaan os-muuttujaan printstream pyoranTiedot                                                 
                huoltoKohdalla.tulosta(os);                                                // printstream on resurssi, joka täytyy sulkea ohjelman käytettyä sitä. try with resources huolehtii sulkemisesta automaattisesti.
        }
    }
    
    
    /**
     * Lisää uuden uuden huollon
     */
    private void uusiHuolto() {
        Huolto huolto = new Huolto(pyoraKohdalla.getTunnusNro());
        huolto.arvoHuolto();          // TODO: korvaa dialogilla, johon tiedot syötetään.
        huolto.rekisteroi();
        try {
            huoltokirja.lisaa(huolto);
        } catch (ApuException e) {
            Dialogs.showMessageDialog("Ongelmia huollon luomisessa");
        }
        paivitaLista();
    }
    
    
    /**
     * Päivittää listan kun uusi huolto lisätään
     */
    private void paivitaLista() {
        chooserHuollot.clear();
        List<Huolto> huollot = huoltokirja.annaHuollot(pyoraKohdalla);
        for (Huolto huolto: huollot) {
            chooserHuollot.add(huolto.getNimi(), huolto);  // Laittaa listaan kohdassa i olevan pyörän nimen ja viitteen Pyora-olioon.
        }
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
    
    
    private void hae() {
        paivitaLista();
        
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
    
    
    /**
     * Asetetaan käytettävä huoltokirja
     * @param huoltokirja huoltokirja jota käyttöliittymässä käytetään
     */
    public void setHuoltokirja(Huoltokirja huoltokirja) {
        this.huoltokirja = huoltokirja;
        // naytaJasen();
    }
    
    
    /**
     * Asetetaan tarkasteltava pyörä
     * @param pyora pyörä jonka tietoja käyttöliittymässä käytetään
     */
    public void setPyora(Pyora pyora) {
        this.pyoraKohdalla = pyora;
        // naytaJasen();
    }
    

    // TODO: Jatka alla olevaa jotta saadaan välitettyä parametrina huoltokirja ja valittu pyörä. Nyt ei tomi 
    
    /**
     * @param modalityStage s
     * @param valittuPyora f
     * @param huoltokirja s
     * @return s
     */
    public static Pyora avaaHuollot(Stage modalityStage, Pyora valittuPyora, Huoltokirja huoltokirja) {     
        return ModalController.<Pyora, HuoltokirjaAukiGUIController>showModal(
                             HuoltokirjaAukiGUIController.class.getResource("HuoltokirjaAukiGUIView.fxml"),
                             "Huoltokirja",
                             modalityStage, valittuPyora,
                             ctrl -> {ctrl.setHuoltokirja(huoltokirja); ctrl.setPyora(valittuPyora); ctrl.paivitaLista();}  // tähän varmaan pitäisi lisätä myös setPyora, jos halutaan ottaa käyttöön parametrina tuotu pyörä?
                         );
             }
}
