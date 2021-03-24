package fxHuoltokirja;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import huoltokirja.ApuException;
import huoltokirja.Huolto;
import huoltokirja.Huoltokirja;
import huoltokirja.Pyora;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


/**
 * Kontrolleri huoltokirjan pääikkunalle
 * @author hemalein
 * @version 24.3.2021
 */
public class HuoltokirjaGUIController implements Initializable { // Pitää toteuttaa initializable, jotta päästään lisäämään väliaikaiset ikkunat testaamista varten.
    
    
    @FXML private Button uusiPyora; 
    @FXML private ListChooser<Pyora> chooserPyorat;
    @FXML private Label labelHakuError;
    @FXML private TextField labelHakuehto;
    @FXML private ComboBoxChooser<String> suodatinPyora;
    @FXML private ScrollPane panelPyora;
    
    @FXML private TextField textNimi;
    @FXML private TextField textMerkki;
    @FXML private TextField textMalli;
    @FXML private TextField textVuosimalli;
    @FXML private TextField textRunkoNro;
    
    
    @Override public void initialize(URL url, ResourceBundle bundle) {
        alusta(); // käydään alustamassa uusi näkymä pyörän tiedoille. Tämä on väliaikaista.  
    }

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
    
    @FXML void handleHaku() {
        hae();
    }
    
    @FXML private void handleLisaaHuolto() { // Tämä on väliaikainen testi, kunnes saadaan välitettyä pyörä huoltokirjalle TODO: poista kun ei tarvita.
        lisäähuolto();
    }
    


    //=============================================================================================

    private Huoltokirja huoltokirja;                  // huoltokirja, johon pyöriä lähdetään lisäämään 
    //private TextArea pyoranTiedot = new TextArea();   // väliaikainen teksti-ikkuna, jolla voidaan näyttää lisätyn pyörän tietoja.
    private Pyora pyoraKohdalla;
    @FXML private TextField[] texts;
    
    
    /**
     * Palauttaa pyörän, joka on valittuna.
     * @return pyörä joka on valittuna listasta.
     */
    public Pyora getPyoraKohdalla() {
        return this.pyoraKohdalla;
    }
    
    
    /**
     * Tallentaa huoltokirjaan tehdyt muutokset
     * @return null jos tallennus onnistuu, virhe palautetaan tekstinä
     */
    private String tallenna() {
        try {
            huoltokirja.talleta();
            return null;
        } catch (ApuException ex) {
              Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
              return ex.getMessage();
        }
    }
    
    
    /**
     * Tarkistetaa että tiedot tallennetaan ennen sulkemista
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    
    /**
     * Palauttaa käytössä olevan huoltokirjan. Tällä hetkellä huoltokirja luodaan huoltokirjaMain:ssa.
     * @return Huoltokirja
     */
    public Huoltokirja getHuoltokirja() {
        return this.huoltokirja;
    }
    
    
    /**
     * Lisätään uusi pyörä.
     */
    private void uusiPyora() {
        try {
            Pyora uusi = new Pyora();
            uusi = HuoltokirjaDialogGUIController.muokkaaPyora(null, uusi);
            if (uusi == null) return;
            uusi.rekisteroi();
            huoltokirja.lisaa(uusi);
            paivitaLista(uusi.getTunnusNro());
        } catch (ApuException e) {
            Dialogs.showMessageDialog("Ongelmia pyörän luomisessa");
        }
    }
    
    
    /**
     * Poistetaan pyörä
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
        // var resurssiHuoltokirjaAuki = HuoltokirjaGUIController.class.getResource("HuoltokirjaAukiGUIView.fxml");
        // ModalController.showModal(resurssiHuoltokirjaAuki, "Huoltokirja", null, "");
        
       // Versio, jossa huoltokirjan aukaiseminen on kirjoitettu huoltokirjaAukiGUIControlleriin.       
        HuoltokirjaAukiGUIController.avaaHuollot(null, pyoraKohdalla, huoltokirja);

    }
    
    
    /**
     * Avaa tulostusikkunan, johon tuodaan listaan lisätyt pyörät
     */
    private void tulosta() {
        TulostusController tulostusCtrl = TulostusController.tulosta(null);  // Avaa Modaalisen ikkunan ikkunan, joka palauttaa tulostuksen kontrollerin. Ei välitetä parametreja. Parametrina voitaisiin välitää String-merkkijono, joka näytettäsiin tulostusalueessa.
        TulostaPyorat(tulostusCtrl.getTextArea());                           // Tulostetaan lisättyjen pyörien tiedot tulostusalueelle
    }
    
    
    /**
     * Lisää käyttäjän luomat pyörät tulostusalueeseen
     * @param textArea Tulostusalue johon pyörien tiedot lisätään.
     */
    public void TulostaPyorat(TextArea textArea) {
        try (PrintStream out = TextAreaOutputStream.getTextPrintStream(textArea);) {    // try-with sulkee resurssin automaattisesti
            out.println("Huoltokirja \n----------------------------------------------\n");
            for (int i = 0; i<huoltokirja.getPyoria(); i++) {
                Pyora pyora = huoltokirja.annaPyora(i);
                out.println("=========================================");
                pyora.tulosta(out);
                
                // Tulostetaan myös huollot
                List<Huolto> loytyneet = huoltokirja.annaHuollot(pyora);
                for (Huolto huolto : loytyneet) {
                    huolto.tulosta(out);
                }
                out.println("----------------------------------------------");
            }
        }     
    }

    
    /**
     * Avaa dialogin, jonka avulla pyörän tietoja muokataan
     */
    private void muokkaaPyoraa() {
        if (pyoraKohdalla == null) return;      // Ei muokata jos pyörää ei ole valittu
        try {
            Pyora pyora = pyoraKohdalla.clone(); // Luodaan uusi klooni valitusta pyörästä ja muokataan sitä
            pyora = HuoltokirjaDialogGUIController.muokkaaPyora(null, pyora);
            if (pyora == null) return;              // Jos painaa cancel, niin palautuu null. Tällöin lähdetään pois.
            huoltokirja.korvaaTailisaa(pyora);
            paivitaLista(pyora.getTunnusNro());
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
            Desktop desktop = Desktop.getDesktop();     // Hakee nykyisen desktopin
            try {
                URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2021k/ht/hemalein");    
                desktop.browse(uri);                    // Käynnistää desktopin oletusselaimella annetun uri-osoitteen
            } catch (URISyntaxException e) {
                return;
            } catch (IOException e) {
                return;
            }
    }
    
    
    /**
     * Päivittää listan, jossa pyörät on esitetty. Hoitaa myös sen, että oikea pyörä on valittuna listasta.
     * Kun ohjelma avautuu, niin ensimmäinen pyörä on valittuna.
     * Lisäämisen tai muokkaamisen jälkeen lisätty tai muokattu pyörä valittuna.
     * @param tunnusNumero Pyörä jonka kohdalla ollaan
     */
    private void paivitaLista(int tunnusNumero) {
        chooserPyorat.clear();
        int index = 0;
        for (int i = 0; i < huoltokirja.getPyoria(); i++) {
            Pyora pyora = huoltokirja.annaPyora(i);
            chooserPyorat.add(pyora.getNimi(), pyora);  // Laittaa listaan kohdassa i olevan pyörän nimen ja viitteen vastaavaan Pyora-olioon.
            if (pyora.getTunnusNro() == tunnusNumero) index = i;
        }
        chooserPyorat.setSelectedIndex(index);
    }
    
    
    /**
     * Alustaa uuden väliaikaisen näkymän pyörän tiedoille.
     */
    private void alusta() {
        //panelPyora.setContent(pyoranTiedot);                   // Korvaa alkuperäisen suunnitelman mukaisen alueen omalla väliaikaisella textArealla.
        // pyoranTiedot.setFont(new Font("Courier New", 12));  // TODO: säädä fontit lopuksi
        //panelPyora.setFitToHeight(true);                       // Kenttä kasvaa koko alueen kokoiseksi
        chooserPyorat.clear();                                 // Tyhjentää pyörien chooserlistan
        chooserPyorat.addSelectionListener(e -> naytaPyora()); // lambda-lauseke. Kun valitaan listasta, niin suoritetaan funktio naytaPyora().   
        texts = new TextField[]{textNimi, textMerkki, textMalli, textVuosimalli, textRunkoNro};
    }
    
    
    /**
     * Näyttää listasta valitun pyörän tiedot
     */
    private void naytaPyora() {
        pyoraKohdalla = chooserPyorat.getSelectedObject();  // Hakee muuttujaan listasta valitun pyörän
        if (pyoraKohdalla == null) return;                  // Huolehtii siitä, jos valitaan kohta jossa ei ole pyörää
        HuoltokirjaDialogGUIController.naytaPyora(texts, pyoraKohdalla); 
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
        tallenna();
        Platform.exit();
    }        
    
    
    /**
     * Asetetaan käytettävä huoltokirja
     * @param huoltokirja huoltokirja jota käyttöliittymässä käytetään
     */
    public void setHuoltokirja(Huoltokirja huoltokirja) {
        this.huoltokirja = huoltokirja;
        try {                               // Yrittää lukea huoltokirjan tiedostosta.
            huoltokirja.lueTiedosto();
        } catch (ApuException ex) {
            // TODO Auto-generated catch block
            System.err.println(ex.getMessage());;
        }
        paivitaLista(0);
    }

    
    /**
     * Käsittelee hakukenttään syötetyn hakuehdon.
     * Jos käyttäjä syöttää jotakin, näyttää punaisen varoitustekstin siitä, että ei osata hakea.
     * Kun käyttäjä tyhjentää tekstialueen, punainen väri häviää.
     */
    private void hae() {
        // Mitä tehdään kun kenttä on null tai tyhjä
        if (labelHakuehto.getText() == null || labelHakuehto.getText() == "") {
            labelHakuError.setText("");  
            labelHakuError.getStyleClass().removeAll("virhe");      // poistaa .virhe-kohdan tyylin käytöstä.
        }
        else {
            labelHakuError.getStyleClass().setAll("virhe");         // Hakee .virhe-kohdan tyylin käyttöön.
            labelHakuError.setText("Ei osata hakea vielä " +suodatinPyora.getSelectedText() + " : " + labelHakuehto.getText());        
        }
    }
    
    
    /**
     * Väliaikainen huollon lisääminen pääikkunasta TODO: poista kun et enää tarvi!
     */
    private void lisäähuolto() {
        if (pyoraKohdalla == null) return;
        Huolto huolto = new Huolto(pyoraKohdalla.getTunnusNro());
        huolto.arvoHuolto();          
        huolto.rekisteroi();
        try {
            huoltokirja.lisaa(huolto);
            naytaPyora();
        } catch (ApuException e) {
            Dialogs.showMessageDialog("Ongelmia huollon luomisessa");
        }
    }
}
