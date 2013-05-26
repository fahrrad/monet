package view;

import controller.CollectionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CollectionView extends Application {
	
	private static CollectionView instance;
	
	private static CollectionController controller;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(CollectionView.class.getResource("CollectionView.fxml")) ;
			AnchorPane pane = (AnchorPane) loader.load();
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Collection list");
			controller = (CollectionController) loader.getController();
			
			primaryStage.show();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static CollectionView getInstance(){
		if(instance == null){
			instance = new CollectionView();
		}
		
		return instance;
	}
	
	public CollectionController getController(){
		return controller;
	}

}
