package dialog;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ShowInfoDialog extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		Scene scene = new Scene(buildDialog(), 300, 100);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private Parent buildDialog(){
				
		Label helloLabel = LabelBuilder.create()
				.text("Hello!")
				.alignment(Pos.BOTTOM_RIGHT)
				.build();
		
		Label helloLabel2 = LabelBuilder.create()
				.text("world!").build();
		
		BorderPane borderpane = BorderPaneBuilder.create()
				.padding(new Insets(25, 25, 25, 25))
				.top(helloLabel2)
				.left(helloLabel)
				.build();
		return borderpane;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(ShowInfoDialog.class, args);

	}

}
