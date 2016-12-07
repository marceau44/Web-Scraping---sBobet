package VIEW;
/**
 * 
 * @author K.Misho
 * @date august '15
 * @version 1.0
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import CONTROLLER.Controller;

public class View_History extends Application{
	private Scene scene = null;
	Controller cont = null;
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader fxml = new FXMLLoader(getClass().getResource("/VIEW/History.fxml"));
		Parent root = fxml.load();
		//cont = fxml.getController();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	public static void main(String [] args){
		launch(args);
	}

}