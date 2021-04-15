package fxHuoltokirja;



import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import huoltokirja.Huoltokirja;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * @author hemalein
 * @version 19.3.2021
 *
 */
public class KaaviotController implements ModalControllerInterface<Huoltokirja>, Initializable {

    @FXML private Button buttonOK;
    @FXML private BarChart<String, Number> chartPylvaat;

    @FXML private void handleOK() {
        ModalController.closeStage(buttonOK);
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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }



    //=============================================================================================
    
    private Huoltokirja huoltokirja;
    private double[] hinnat;
    
    private void alusta() {
        //
    }
    
    
    /**
     * Avaa kaaviot modaalisena
     * @param modalityStage mille ollaan modaalisia
     * @param huoltokirja huoltokirja jota käytetään
     * @return huoltokirja jota on käytetty
     */
    public static Huoltokirja avaaKaaviot(Stage modalityStage, Huoltokirja huoltokirja) {     
        return ModalController.<Huoltokirja, KaaviotController>showModal(
                             KaaviotController.class.getResource("KaaviotGUIView.fxml"),
                             "Huoltokirja",
                             modalityStage, huoltokirja,
                             ctrl -> {ctrl.setHuoltokirja(huoltokirja); ctrl.haejaAsetaHinnat();}  // tähän varmaan pitäisi lisätä myös setPyora, jos halutaan ottaa käyttöön parametrina tuotu pyörä?
                         );
    }

    
    /**
     * Hakee hinnat huoltokirjalta ja asettaa hinnat kaavioon
     */
    private void haejaAsetaHinnat() {
        hinnat = huoltokirja.annaHinnat();
        asetaHinnat(hinnat);
        
    }

    /**
     * Asettaa taulukon hinnat kaavioon
     * @param hintaTaul
     */
    private void asetaHinnat(double[] hintaTaul) {
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Huoltojen hinnat");
        for (int i = 0; i < hintaTaul.length; i++) {
            series1.getData().add(new XYChart.Data<>(""+(i+1), hintaTaul[i]));
        }
        chartPylvaat.getData().add(series1); 
        
    }

    /**
     * Asettaa huoltokirjan käyttöön
     * @param huoltokirja
     */
    private void setHuoltokirja(Huoltokirja huoltokirja) {
        this.huoltokirja = huoltokirja;
    }
   
}
    
  