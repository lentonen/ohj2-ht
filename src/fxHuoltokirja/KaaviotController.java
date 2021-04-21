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
 * Kontrolleri huoltokirjan kaaviot-näkymälle.
 * @author Henri Leinonen
 * @version 21.4.2021
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
     * Asettaa huoltokirjan käyttöön
     * @param huoltokirja käytössä oleva huoltokirja
     */
    private void setHuoltokirja(Huoltokirja huoltokirja) {
        this.huoltokirja = huoltokirja;
    }
    
    
    /**
     * Hakee vuosilukuvalikkoon kaikki vuodet, jolloin huoltoja on tehty. Valikosta on valittuna nykyinen
     * vuosi, jos silloin on tehty huoltoja.
     */
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
     * @param vuosi minkä vuoden hinnat haetaan
     */
    private void haeJaPaivitaHinnat(int vuosi) {
        hinnat = huoltokirja.annaHinnat(vuosi);
        for (int i = 0; i < hinnat.length; i++) {
            kuukausiHinnat.getData().get(i).setYValue(hinnat[i]);
            kuukausiHinnat.setName("Myynnit vuonna " +vuosiChooser.getSelectedText());
        }
    }

    
    /**
     * Asettaa taulukon hinnat kaavioon käynnistettäessä. Käyttää kuluvaa vuotta.
     * @param hintaTaul taulukko josta hinnat haetaan
     */
    private void asetaAlkuHinnat(double[] hintaTaul) {
        kuukausiHinnat.getData().clear();
        kuukausiHinnat.setName("Myynnit vuonna " +vuosiChooser.getSelectedText());
        for (int i = 0; i < hintaTaul.length; i++) {
            kuukausiHinnat.getData().add(new XYChart.Data<>(""+(i+1), hintaTaul[i]));
        }
        chartPylvaat.getData().add(kuukausiHinnat);
    }
    
    
    /**
     * Päivittää kaavioon valitun vuoden datan.
     */
    private void haeKaavio() {
        int vuosi = Integer.parseInt(vuosiChooser.getSelectedText());
        haeJaPaivitaHinnat(vuosi);
    }
    
    
    /**
     * Hakee hinnat huoltokirjalta ja asettaa hinnat kaavioon.
     */
    private void haejaAsetaHinnat() {
        // Otetaan kiinni poikkeus, joka johtuu siitä, että yhtäkään huoltoa ei ole vielä lisätty.
        try {
            int vuosiluku = Integer.parseInt(vuosiChooser.getSelectedText());
            hinnat = huoltokirja.annaHinnat(vuosiluku);
            asetaAlkuHinnat(hinnat);
        }catch (NumberFormatException e) {
            System.err.println("Yhtäkään huoltoa ei ole vielä lisätty");
        }  
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
                             ctrl -> {ctrl.setHuoltokirja(huoltokirja);ctrl.asetaVuosiChooser();ctrl.haejaAsetaHinnat();}
                         );
    }   
}