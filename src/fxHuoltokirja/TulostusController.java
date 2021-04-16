package fxHuoltokirja;



import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;


/**
 * @author hemalein
 * @version 19.3.2021
 *
 */
public class TulostusController implements ModalControllerInterface<String> {

    @FXML private TextArea tulostusAlue;

    @FXML private void handleOK() {
        ModalController.closeStage(tulostusAlue);
    }

    @FXML
    void handleTulosta() {
        tulostaHuoltokirja();
    }
    

    @Override
    public String getResult() {
        return null;
    }

    @Override
    public void handleShown() {
        //  
    }

    @Override
    public void setDefault(String oletus) {
        tulostusAlue.setText(oletus);     
    }

    //=============================================================================================
    
    
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
    public static TulostusController tulosta(String tulostus) {
        TulostusController tulostusCtrl = 
          ModalController.showModeless(TulostusController.class.getResource("TulostusView.fxml"),
                                       "Tulostus", tulostus);
        return tulostusCtrl;
    }
}
    
  