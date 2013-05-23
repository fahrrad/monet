package controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import service.CollectionServiceHibernateImpl;
import service.ICollecionService;
import service.IWorkService;
import service.WorkServiceHibernateImpl;
import domain.Collection;
import domain.Work;

public class WorkViewController implements Initializable {
	
	private final ICollecionService collectionService = new CollectionServiceHibernateImpl();

	private static final int LABEL_WIDTH = 105;
	private static final int TEXT_WIDTH = 150;

	private Map<String, TextInputControl> propertiesMap = new HashMap<String, TextInputControl>();
	
	private Work work = new Work();
	
	@SuppressWarnings("unused")
	private boolean changed = false;

	@FXML private VBox propertiesVBox;
	
	@FXML private Button saveButton; 
	
	@FXML private ImageView imageView;

	private class saveThread extends Thread{
		final private Work work;
		private final IWorkService workService = new WorkServiceHibernateImpl();
		
		public saveThread(Work workToSave){
			work = workToSave; 
		}

		@Override
		public void run() {
			workService.insertOrUpdate(work);
			
		}	
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		saveButton.setDisable(true);
		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Work workToSave = new Work();
				workToSave.setId(work.getId());
				workToSave.setTitle(propertiesMap.get("Titel").getText());
				workToSave.setCreator(propertiesMap.get("Kunstenaar").getText());
				workToSave.setBreedte(Double.valueOf(propertiesMap.get("Breedte").getText()));
				workToSave.setHoogte(Double.valueOf(propertiesMap.get("Hoogte").getText()));
				workToSave.setMedium(propertiesMap.get("Medium").getText());
				workToSave.setVorigeEigenaar(propertiesMap.get("Vorige Eigenaar").getText());
 				workToSave.setJaar(Integer.valueOf(propertiesMap.get("Jaar").getText()));
 				workToSave.setThema(propertiesMap.get("Thema").getText());
 				workToSave.setPersonen(propertiesMap.get("Personen").getText());
 				workToSave.setOpmerking(propertiesMap.get("Opmerking").getText());
				
				new saveThread(workToSave).start();
			}
		});
		
		imageView.setImage(new Image("http://cache-www.coderanch.com/mooseImages/moosefly.gif"));
		HBox.setHgrow(imageView, Priority.ALWAYS);
	}

	private void addProperties() {
		addPropertyToBox("Titel", work.getTitle());
		addPropertyToBox("Kunstenaar", work.getCreator());
		addPropertyToBox("Breedte", String.valueOf(work.getBreedte()));
		addPropertyToBox("Hoogte", String.valueOf(work.getHoogte()));
		addPropertyToBox("Medium", work.getMedium());
		addPropertyToBox("Vorige Eigenaar", work.getVorigeEigenaar());
		addPropertyToBox("Jaar", String.valueOf(work.getJaar()));
		addPropertyToBox("Thema", work.getThema());
		addPropertyToBox("Personen", work.getPersonen(),2); 

		
		addPropertyToBox("Opmerking", work.getOpmerking(), 3);
		
		// collection ComboBox
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(5, 10, 5, 12));
		hbox.setSpacing(20);

		Label label = new Label();
		label.setText("Collection");
		label.setMaxWidth(LABEL_WIDTH);
		label.setMinWidth(LABEL_WIDTH);
		
		ChoiceBox<Collection> collections = new ChoiceBox<>(FXCollections.observableArrayList(collectionService.getAll()));
		hbox.getChildren().addAll(label, collections);
		propertiesVBox.getChildren().add(hbox);
		
	}
	
	private void setChanged(boolean changed){
		this.changed = changed;
		saveButton.setDisable(!changed);
	}

	public void setWork(Work work) {
		this.work = work;
		update();
		setChanged(false);
	}

	private void update() {
		propertiesVBox.getChildren().clear();
		addProperties();
	}

	private void addPropertyToBox(String name, String value, int rowCount) {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(5, 10, 5, 12));
		hbox.setSpacing(20);

		Label label = new Label();
		label.setText(name);
		label.setMaxWidth(LABEL_WIDTH);
		label.setMinWidth(LABEL_WIDTH);

		if (rowCount == 1) {
			TextField textField = new TextField(value);
			textField.setAlignment(Pos.CENTER_RIGHT);
			textField.setMinWidth(TEXT_WIDTH);
			textField.setMaxWidth(TEXT_WIDTH);
			textField.setMinHeight(20);
			textField.textProperty().addListener(new InvalidationListener() {
				
				@Override
				public void invalidated(Observable observable) {
					setChanged(true);
				}
			});
			propertiesMap.put(name, textField);
			hbox.getChildren().addAll(label, textField);
		} else {
			TextArea textArea = new TextArea();
			textArea.setPrefRowCount(rowCount);
			textArea.setMinWidth(TEXT_WIDTH);
			textArea.setMaxWidth(TEXT_WIDTH);
			
			textArea.setMinHeight(20 * rowCount);
			textArea.textProperty().addListener(new InvalidationListener() {
				
				@Override
				public void invalidated(Observable observable) {
					setChanged(true);
				}
			});
			propertiesMap.put(name, textArea);
			hbox.getChildren().addAll(label, textArea);
		}

		propertiesVBox.getChildren().add(hbox);
		VBox.setVgrow(hbox, Priority.NEVER);
	}

	private void addPropertyToBox(String name, String value) {
		addPropertyToBox(name, value, 1);
	}
}
