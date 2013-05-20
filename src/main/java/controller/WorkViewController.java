package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import domain.Work;

public class WorkViewController implements Initializable {
	
	private Work work = new Work();
	
	@FXML
	private Label titleLabel;
	
	@FXML
	private VBox propertiesVBox;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	private void addProperties(){
		addPropertyToBox("Titel", work.getTitle());
		addPropertyToBox("Kunstenaar", work.getCreator());
		addPropertyToBox("Breedte", String.valueOf(work.getBreedte()));
		addPropertyToBox("Hoogte", String.valueOf(work.getHoogte()));
	}
	
	
	public void setWork(Work work){
		this.work = work;
		update();
	}
	
	
	private void update(){
		propertiesVBox.getChildren().clear();
		addProperties();
	}
	
	
	private void addPropertyToBox(String name, String value){
		HBox hbox = new HBox();
		hbox.setSpacing(20);
		
		Label label = new Label();
		label.setText(name);
		
		TextField textField = new TextField(value);
		
		hbox.getChildren().addAll(label, textField);
		propertiesVBox.getChildren().add(hbox);
	}
}
