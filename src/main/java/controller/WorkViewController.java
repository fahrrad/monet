package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WorkViewController implements Initializable {
	
	@FXML
	private Label titleLabel;
	
	@FXML
	private VBox propertiesVBox;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addPropertyToBox("test", null);
	}
	
	private void addPropertyToBox(String name, ObservableValue<String> value){
		HBox hbox = new HBox();
		hbox.setSpacing(20);
		
		Label label = new Label();
		label.setText(name);
		
		TextField textField = new TextField(value.getValue());
		
		hbox.getChildren().addAll(label, textField);
		propertiesVBox.getChildren().add(hbox);
	}
}
