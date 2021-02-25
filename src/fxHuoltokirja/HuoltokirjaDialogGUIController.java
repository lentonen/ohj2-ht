package fxHuoltokirja;


//import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.Node;


/**
 * @author hemalein
 * @version 25.2.2021
 *
 */
public class HuoltokirjaDialogGUIController implements ModalControllerInterface<String> {
    
    @FXML private Node testi;

    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }
    
    
    @FXML
    void handleOK() {
        ModalController.closeStage(testi);
        
    }
    
    
    @FXML
    void handlePeruuta() {
        ModalController.closeStage(testi);
        
    }
   



    //=============================================================================================
    
   
        
    
}
