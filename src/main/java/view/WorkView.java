package view;

import java.io.IOException;
import java.net.URL;

import controller.WorkViewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WorkView extends Application {
	
	private static Stage instance;
	
	private static FXMLLoader loader = new FXMLLoader();
	static {
		URL location = WorkView.class.getResource("WorkView.fxml");
		loader.setLocation(location);
		loader.setBuilderFactory(new JavaFXBuilderFactory());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Stage stage = getInstance();
		primaryStage.setScene(stage.getScene());
		primaryStage.show();
	}
	
	public static Stage getInstance() throws IOException{
		Stage stage = null;
		if(instance == null){
			Parent workView = (Parent) loader.load();
			stage = new Stage();
			stage.setScene(new Scene(workView));
			instance = stage;
		}
		return instance;
	}
	
	public static WorkViewController getController(){
		return (WorkViewController) loader.getController();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(WorkView.class, args);
	}
	
	

}
