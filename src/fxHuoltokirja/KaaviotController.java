package fxHuoltokirja;



import java.util.Calendar;
import java.util.Collection;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import huoltokirja.Huoltokirja;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * @author Henri Leinonen
 * @version 15.4.2021
 *
 */
public class KaaviotController implements ModalControllerInterface<Huoltokirja> {

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

    
    @FXML
    void handleVuosi() {
        haeKaavio();
    }


    //=============================================================================================

    private Huoltokirja huoltokirja;
    private double[] hinnat;
    private XYChart.Series<String, Number> kuukausiHinnat = new XYChart.Series<>();
    

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
                             ctrl -> {ctrl.setHuoltokirja(huoltokirja); ctrl.haejaAsetaHinnat(Calendar.getInstance().get(Calendar.YEAR));ctrl.asetaVuosiChooser();}
                         );
    }

    
    private void asetaVuosiChooser() {
        vuosiChooser.clear();
        Collection<String> vuodet = huoltokirja.annaVuodet();
        int i = 0;
        int k = 0;
        for (String vuosi : vuodet) {
            vuosiChooser.add(vuosi, null);
            if (Integer.parseInt(vuosi) == Calendar.getInstance().get(Calendar.YEAR)) k = i;
            i++;
        }
        vuosiChooser.setSelectedIndex(k); 
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
     * Asettaa taulukon hinnat kaavioon.
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
    
    
    /**
     * Päivittää kaavioon valitun vuoden datan.
     */
    private void haeKaavio() {
        int vuosi = Integer.parseInt(vuosiChooser.getSelectedText());
        haeJaPaivitaHinnat(vuosi);
    }
}
    
  