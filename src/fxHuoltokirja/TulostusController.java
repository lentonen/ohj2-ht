package fxHuoltokirja;



import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import huoltokirja.Huolto;
import huoltokirja.Huoltokirja;
import huoltokirja.Pyora;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;


/**
 * @author hemalein
 * @version 19.3.2021
 *
 */
public class TulostusController implements ModalControllerInterface<Huoltokirja> {

    @FXML private TextArea tulostusAlue;

    @FXML private void handleOK() {
        ModalController.closeStage(tulostusAlue);
    }

    @FXML
    void handleTulosta() {
        tulostaHuoltokirja();
    }
    

    @Override
    public Huoltokirja getResult() {
        return null;
    }

    @Override
    public void handleShown() {
        //  
    }

    @Override
    public void setDefault(Huoltokirja oletus) {
        //  
    }

    //=============================================================================================
    
    Huoltokirja huoltokirja;
    Pyora pyoraKohdalla;
    
    private void tulostaHuoltokirja() {
        // Dialogs.showMessageDialog("Ei osata vielä tulostaa");
        PrinterJob job = PrinterJob.createPrinterJob();
                  if ( job != null && job.showPrintDialog(null) ) {
                      WebEngine webEngine = new WebEngine();
                      webEngine.loadContent("<pre>" + tulostusAlue.getText() + "</pre>");
                      webEngine.print(job);
                      job.endJob();
                  }        
    }
    
    
    /**
     * Saantimetodi tulostusalueelle
     * @return tulostusalue
     */
    public TextArea getTextArea() {
        return tulostusAlue;
    }
    
    
    /**
     * Avaa modaalisen ikkunan tulostamista varten
     * @param tulostus tulostettava teskti
     * @return kontrolleri, jolta voidaan pyytää lisää tietoa
     */
    /*public static TulostusController tulosta(String tulostus) {
        TulostusController tulostusCtrl = 
          ModalController.showModeless(TulostusController.class.getResource("TulostusView.fxml"),
                                       "Tulostus", tulostus);
        return tulostusCtrl;
    }*/
    
    /**
     * Avaa tulostuksen modaalisena
     * @param modalityStage mille ollaan modaalisia
     * @param pyora Pyörä jonka huoltokija halutaan tulostaa
     * @param huoltokirja huoltokirja jota käytetään
     * @return huoltokirja jota on käytetty
     */
    public static Huoltokirja tulostaHuoltokirja(Stage modalityStage, Pyora pyora, Huoltokirja huoltokirja) {     
        return ModalController.<Huoltokirja, TulostusController>showModal(
                             TulostusController.class.getResource("TulostusView.fxml"),
                             "Tulostus",
                             modalityStage, huoltokirja,
                             ctrl -> {ctrl.setHuoltokirja(huoltokirja);ctrl.setPyora(pyora); ctrl.setTulostusAlue();}
                         );
    }

    
    private void setTulostusAlue() {
        tulostusAlue.clear();
        try (PrintStream out = TextAreaOutputStream.getTextPrintStream(tulostusAlue);) {    // try-with sulkee resurssin automaattisesti
            out.println("HUOLTOKIRJA");
                Pyora pyora = pyoraKohdalla;
                out.println("=========================================\n\nPYÖRÄN TIEDOT:");
                pyora.tulosta(out);
                                
                // Tulostetaan myös huollot
                out.println("HUOLLOT:");
                List<Huolto> loytyneet = huoltokirja.annaHuollot(pyora);
                Collections.sort(loytyneet, new Huolto.Vertailija(5)); // Järjestää tulostuksen ajotuntien mukaan
                for (Huolto huolto : loytyneet) {
                    huolto.tulosta(out);
                }
                out.println("");     
        }     
        
    }

    private void setPyora(Pyora pyora) {
        pyoraKohdalla = pyora;
        
    }

    
    private void setHuoltokirja(Huoltokirja huoltokirja) {
        this.huoltokirja = huoltokirja;
    }
}
    
  