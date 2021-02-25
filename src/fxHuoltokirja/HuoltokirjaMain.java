package fxHuoltokirja;
	
import huoltokirja.Huoltokirja;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
//import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Henri
 * @version Jan 15, 2021
 *
 */
public class HuoltokirjaMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    final FXMLLoader ldr = new FXMLLoader(getClass().getResource("HuoltokirjaGUIView.fxml"));                            // Lataa fxml resurssin?
			final Pane root = ldr.load(); //(BorderPane)FXMLLoader.load(getClass().getResource("HuoltokirjaGUIView.fxml"));      // käytössä oleva pane?
			final HuoltokirjaGUIController huoltokirjaCtrl = ldr.getController();                                                // Käytössä oleva kontrolleri
			    
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("huoltokirja.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Huoltokirja huoltokirja = new Huoltokirja();     // Luodaan uusi huoltokirja
			huoltokirjaCtrl.setHuoltokirja(huoltokirja);     // Asetetaan luotu huoltokirja kontrollerin käyttöön.
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
