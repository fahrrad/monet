package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WorkView extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent workView = FXMLLoader.load(WorkView.class.getResource("WorkView.fxml"));
		Scene scene = new Scene(workView);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(WorkView.class, args);
	}

}
