package fxHuoltokirja;

import static fxHuoltokirja.HuoltokirjaDialogGUIController.getFieldId;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import huoltokirja.ApuException;
import huoltokirja.Huolto;
import huoltokirja.Huoltokirja;
import huoltokirja.Pyora;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * Kontrolleri valitun pyörän huoltokirjan ikkunalle.
 * @author Henri Leinonen
 * @version 13.4.2021
 *
 */
public class HuoltokirjaAukiGUIController implements ModalControllerInterface<Pyora>, Initializable {
    
    @FXML private ScrollPane panelHuolto;  // Pistetään tähän paneen väliaikaiset tiedot huolloista.
    @FXML private Button lisaaHuolto;
    @FXML private ListChooser<Huolto> chooserHuollot;
    @FXML private TextField labelHakuEhto;
    @FXML private Button buttonMuokkaa;
    @FXML private ComboBoxChooser<?> suodatinHuollot;
    
    // Kentät huollon tiedoille
   // @FXML private TextField textAjotunnit; // TODO: poista
   // @FXML private TextField textNimi;
   // @FXML private TextArea textToimenpiteet;
    @FXML private GridPane gridHuollot;
    
    @FXML void handleUusiHuolto() {
        uusiHuolto();
    }
    
    @FXML void handlePoistaHuolto() {
        poistaHuolto();
    }
    
    @FXML void handleMuokkaaHuoltoa() {
        muokkaaHuoltoa(kentta);
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
        paivitaLista(0);;
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
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
        chooserHuollot.requestFocus();   
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
    private Huolto huoltoKohdalla = new Huolto();
    private Huoltokirja huoltokirja; // Käytössä oleva huoltokirja, joka tuodaan kun huoltokirja avataan
    @FXML private TextInputControl[] texts;  // Tietokentät taulukossa
    private int kentta = 1;
    
    private void alusta() {
        chooserHuollot.clear(); 
        chooserHuollot.addSelectionListener(e -> naytaHuolto());                                    // lambda-lauseke. Kun valitaan listasta, niin suoritetaan funktio e joka suorittaa naytaHuolto();
        texts = TietueDialogController.luoKentat(gridHuollot, new Huolto()); 
        gridHuollot.add(buttonMuokkaa, 1, huoltoKohdalla.getKenttia()-huoltoKohdalla.ekaKentta());  // Asettaa muokkaaButtonin viimeisen kentän alapuolelle
        for (TextInputControl text : texts) {
            if (text != null) {
                text.setEditable(false);
            text.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaaHuoltoa(getFieldId(e.getSource(),0)); });  
            text.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(text,kentta));
            }
        }  
        
        suodatinHuollot.clear();
        for (int i = huoltoKohdalla.ekaKentta(); i <huoltoKohdalla.getKenttia(); i++) {
            suodatinHuollot.add(huoltoKohdalla.getKentanNimi(i), null);
        }
        suodatinHuollot.setSelectedIndex(3);
    }
    
    
    /**
     * Näyttää listasta valitun huollon tiedot
     */
    private void naytaHuolto() {
        huoltoKohdalla = chooserHuollot.getSelectedObject();   // Hakee muuttujaan listasta valitun huollon
        if (huoltoKohdalla == null) return;                    // Huolehtii siitä, jos valitaan kohta jossa ei ole huoltoa
        TietueDialogController.naytaTietue(texts, huoltoKohdalla);
    }
    
    
    /**
     * Lisää uuden uuden huollon
     */
    private void uusiHuolto() {
        try {
            Huolto uusi = new Huolto(pyoraKohdalla.getTunnusNro());
            uusi = TietueDialogController.muokkaaTietue(null, uusi, 1);  // Uuden pyörän lisäämisessä ensimmäinen kenttä valittuna.
            if (uusi == null) return;
            uusi.rekisteroi();
            huoltokirja.lisaa(uusi);
            paivitaLista(uusi.getTunnusNro());
        } catch (ApuException e) {
            Dialogs.showMessageDialog("Ongelmia huollon luomisessa");
        }
    }
    
    
    /**
     * Päivittää listan kun uusi huolto lisätään
     */
    private void paivitaLista(int tunnusNumero) {
        String ehto = labelHakuEhto.getText();
        if (ehto.indexOf('*') < 0) ehto = "*" +ehto+ "*";
        Collection<Huolto> loydetytHuollot;
        loydetytHuollot = huoltokirja.etsiHuollot(ehto, suodatinHuollot.getSelectionModel().getSelectedIndex()+ huoltoKohdalla.ekaKentta(), pyoraKohdalla.getTunnusNro());
        chooserHuollot.clear();
        int index = 0;
        int i = 0;
        for (Huolto huolto: loydetytHuollot) {
            chooserHuollot.add(huolto.getNimi(), huolto);
            if (huolto.getTunnusNro() == tunnusNumero) index = i;
            i++;
        }
        chooserHuollot.setSelectedIndex(index);
        
        
        //chooserHuollot.clear();
        //int index = 0;
        //List<Huolto> huollot = huoltokirja.annaHuollot(pyoraKohdalla);
        //for (int i = 0; i < huollot.size(); i++) {
        //    Huolto huolto = huollot.get(i);
        //    chooserHuollot.add(huolto.getNimi(), huolto);  // Laittaa listaan kohdassa i olevan pyörän nimen ja viitteen Pyora-olioon.
        //    if (huolto.getTunnusNro() == tunnusNumero) index = i;
        //}
        //chooserHuollot.setSelectedIndex(index); 
    }
    
    
    /**
     * Poistaa huollon
     */
    private void poistaHuolto() {
        Huolto huolto = huoltoKohdalla;
        if (huolto == null) return;        
        if (!Dialogs.showQuestionDialog("Poisto?",
                "Poistetaanko huolto: "+huoltoKohdalla.getNimi() , "Kyllä", "Ei"))
            return;
        huoltokirja.poista(huolto);
        paivitaLista(0); 
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
    private void muokkaaHuoltoa(int k) { 
        //HuoltokirjaAukiDialogGUIController.muokkaaHuolto(null, huoltoKohdalla);
        if (huoltoKohdalla == null) return;                                     // Ei muokata jos huoltoa ei ole valittu
        try {
            Huolto huolto = huoltoKohdalla.clone();                             // Luodaan uusi klooni valitusta huollosta ja muokataan sitä
            huolto = TietueDialogController.muokkaaTietue(null, huolto, k);    
            if (huolto == null) return;                                         // Jos painaa cancel, niin palautuu null. Tällöin lähdetään pois.
            huoltokirja.korvaaTailisaa(huolto);
            paivitaLista(huolto.getTunnusNro());
        } catch (CloneNotSupportedException e1) {
            System.err.println(e1.getMessage());;
        } catch (ApuException e) {
            System.err.println("Ongelmia pyörän lisäämisessä");
        }    
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
    
    
    /**
     * @param modalityStage mille ollaan modaalisia
     * @param valittuPyora Pyörä jota käsitellään
     * @param huoltokirja huoltokirja jota käytetään
     * @return Pyörä jota on käsitelty
     */
    public static Pyora avaaHuollot(Stage modalityStage, Pyora valittuPyora, Huoltokirja huoltokirja) {     
        return ModalController.<Pyora, HuoltokirjaAukiGUIController>showModal(
                             HuoltokirjaAukiGUIController.class.getResource("HuoltokirjaAukiGUIView.fxml"),
                             "Huoltokirja",
                             modalityStage, valittuPyora,
                             ctrl -> {ctrl.setHuoltokirja(huoltokirja); ctrl.setPyora(valittuPyora); ctrl.paivitaLista(valittuPyora.getTunnusNro());}  // tähän varmaan pitäisi lisätä myös setPyora, jos halutaan ottaa käyttöön parametrina tuotu pyörä?
                         );
    }
}
