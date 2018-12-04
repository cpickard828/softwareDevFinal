package boardClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

/**
 *
 * @author Joe Gregg
 * @modified Jane Cleland-Huang
 * @modified by HRCC
 */
public class BoardClient extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

		Scene scene = new Scene(root);
		final Canvas canvas = new Canvas(900, 1000);
		stage.setScene(scene);
		stage.setTitle("Board Client");
		stage.setOnCloseRequest(event -> System.exit(0));
		stage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}