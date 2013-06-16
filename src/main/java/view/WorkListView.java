package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class WorkListView extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			AnchorPane pane = FXMLLoader.load(CollectionView.class
					.getResource("WorkListView.fxml"));
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Werken");
			primaryStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(WorkListView.class, args);
	}

}
