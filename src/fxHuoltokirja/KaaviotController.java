package fxHuoltokirja;



import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
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
 * @author Henri Leinonen
 * @version 15.4.2021
 *
 */
public class KaaviotController implements ModalControllerInterface<Huoltokirja>, Initializable {

    @FXML private Button buttonOK;
    @FXML private BarChart<String, Number> chartPylvaat;
    @FXML private ComboBoxChooser<String> vuosiChooser;

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
    
    @FXML
    void handleVuosi() {
        haeKaavio();
    }


    //=============================================================================================

    private Huoltokirja huoltokirja;
    private double[] hinnat;
    private XYChart.Series<String, Number> kuukausiHinnat = new XYChart.Series<>();
    
    private void alusta() {
        vuosiChooser.clear();
        //for (int i = apuPyora.ekaKentta(); i <apuPyora.getKenttia(); i++) {     // Tyhjennetään ja haetaan kentät, jotka laitetaan hakuehtoihin.
        vuosiChooser.add("2021", null);
        vuosiChooser.add("2020", null);
        //}
        vuosiChooser.setSelectedIndex(0);
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
                             ctrl -> {ctrl.setHuoltokirja(huoltokirja); ctrl.haejaAsetaHinnat(Calendar.getInstance().get(Calendar.YEAR));}
                         );
    }

    
    /**
     * Hakee hinnat huoltokirjalta ja asettaa hinnat kaavioon
     */
    private void haejaAsetaHinnat(int vuosi) {
        hinnat = huoltokirja.annaHinnat(vuosi);
        asetaHinnat(hinnat);
        
    }
    
    
    /**
     * Hakee hinnat huoltokirjalta ja asettaa hinnat kaavioon
     */
    private void haeJaPaivitaHinnat(int vuosi) {
        hinnat = huoltokirja.annaHinnat(vuosi);
        paivitaHinnat(hinnat);
        
    }

    /**
     * Asettaa taulukon hinnat kaavioon. Käyttää kuluvaa vuotta.
     * @param hintaTaul taulukko josta hinnat haetaan
     */
    private void asetaHinnat(double[] hintaTaul) {
        kuukausiHinnat.getData().clear();
        kuukausiHinnat.setName("Myynnit vuonna " +vuosiChooser.getSelectedText());
        for (int i = 0; i < hintaTaul.length; i++) {
            kuukausiHinnat.getData().add(new XYChart.Data<>(""+(i+1), hintaTaul[i]));
        }
        chartPylvaat.getData().add(kuukausiHinnat);
    }
    
    

    /**
     * Asettaa taulukon hinnat kaavioon. Käyttää kuluvaa vuotta.
     * @param hintaTaul taulukko josta hinnat haetaan
     */
    private void paivitaHinnat(double[] hintaTaul) {
        for (int i = 0; i < hintaTaul.length; i++) {
            kuukausiHinnat.getData().get(i).setYValue(hintaTaul[i]);
            kuukausiHinnat.setName("Myynnit vuonna " +vuosiChooser.getSelectedText());
        }
    }

    
    /**
     * Asettaa huoltokirjan käyttöön
     * @param huoltokirja
     */
    private void setHuoltokirja(Huoltokirja huoltokirja) {
        this.huoltokirja = huoltokirja;
    }
    
    
    private void haeKaavio() {
        int vuosi = Integer.parseInt(vuosiChooser.getSelectedText());
        haeJaPaivitaHinnat(vuosi);
    }
}
    
  