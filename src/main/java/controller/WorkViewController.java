package controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
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
	
	// Services
	private final ICollecionService collectionService = new CollectionServiceHibernateImpl();
	private final IWorkService workService = new WorkServiceHibernateImpl();

	private static final int LABEL_WIDTH = 105;
	private static final int TEXT_WIDTH = 150;

	private Map<String, TextInputControl> propertiesMap = new HashMap<String, TextInputControl>();
	
	private Work work = new Work();
	
	@SuppressWarnings("unused")
	private boolean changed = false;

	@FXML private VBox propertiesVBox;
	
	@FXML private Button saveButton; 
	
	@FXML private ImageView imageView;

	// The caller will want to be refreshed when a new work is added
	private WorkListController caller;

	private class saveThread extends Task<Void>{
		final private Work work;
		
		public saveThread(Work workToSave){
			work = workToSave; 
		}

		@Override
		protected Void call() throws Exception {
			workService.insertOrUpdate(work);
			return null;
		}

		@Override
		protected void succeeded() {
			super.succeeded();
			caller.loadAllWork();
		}	
		
	}
	
	private class loadImage extends Task<Void>{

		@Override
		protected Void call() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void succeeded() {
			// TODO Auto-generated method stub
			super.succeeded();
		}
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		saveButton.setDisable(true);
		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				work.setTitle(propertiesMap.get("Titel").getText());
				work.setCreator(propertiesMap.get("Kunstenaar").getText());
				work.setBreedte(Double.valueOf(propertiesMap.get("Breedte").getText().replace(',','.')));
				work.setHoogte(Double.valueOf(propertiesMap.get("Hoogte").getText().replace(',','.')));
				work.setMedium(propertiesMap.get("Medium").getText());
				work.setVorigeEigenaar(propertiesMap.get("Vorige Eigenaar").getText());
 				work.setJaar(Integer.valueOf(propertiesMap.get("Jaar").getText()));
 				work.setThema(propertiesMap.get("Thema").getText());
 				work.setPersonen(propertiesMap.get("Personen").getText());
 				work.setOpmerking(propertiesMap.get("Opmerking").getText());
				
				new saveThread(work).run();
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

	public void setWork(Work work, WorkListController caller) {
		if(work.getId() != null){
			this.work = workService.getById(work.getId());
		}else{
			this.work = work;
		}
		
		update();
		setChanged(false);
		
		this.caller = caller;
		
		
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
			textArea.setText(value);
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
