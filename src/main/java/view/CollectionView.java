package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CollectionView extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			AnchorPane pane = FXMLLoader.load(CollectionView.class
					.getResource("CollectionView.fxml"));
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Collection list");
			primaryStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(CollectionView.class, args);
	}
}
