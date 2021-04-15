package fxHuoltokirja;



import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;


/**
 * @author hemalein
 * @version 19.3.2021
 *
 */
public class KaaviotController implements ModalControllerInterface<String>, Initializable {

    @FXML private Button buttonOK;
    @FXML private BarChart<String, Number> chartPylvaat;

    @FXML private void handleOK() {
        ModalController.closeStage(buttonOK);
    }

    @FXML
    void handleTulosta() {
        Dialogs.showMessageDialog("Ei osata vielä tulostaa");
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
        // 
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }



    //=============================================================================================
    
    
    private void alusta() {
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Hinta");
        series1.getData().add(new XYChart.Data<>("tammikuu", 20));
        series1.getData().add(new XYChart.Data<>("helmikuu", 100));
        series1.getData().add(new XYChart.Data<>("maaliskuu", 80));
        series1.getData().add(new XYChart.Data<>("huhtikuu", 180));
        series1.getData().add(new XYChart.Data<>("toukokuu", 20));
        series1.getData().add(new XYChart.Data<>("kesäkuu", 0));
        chartPylvaat.getData().add(series1);    
    }

    
    
}
    
  