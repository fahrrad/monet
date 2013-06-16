package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.stage.FileChooser;
import service.CollectionServiceHibernateImpl;
import service.ICollectionService;
import service.IWorkService;
import service.WorkServiceHibernateImpl;
import domain.Collection;
import domain.Work;

public class WorkViewController implements Initializable {

	// Services
	// private final ICollectionService collectionService = new
	// CollectionServiceHibernateImpl();
	private final IWorkService workService = new WorkServiceHibernateImpl();
	private final ICollectionService collectionService = new CollectionServiceHibernateImpl();

	private static final Logger logger = LoggerFactory
			.getLogger(WorkViewController.class);

	private static final int LABEL_WIDTH = 105;
	private static final int TEXT_WIDTH = 150;

	private static final String SCANS_DIR = "C:/";
	private static final String IMAGE_REPO = "images/";

	private Map<String, TextInputControl> propertiesMap = new HashMap<String, TextInputControl>();

	private Work work = new Work();

	@SuppressWarnings("unused")
	private boolean changed = false;

	@FXML
	private VBox propertiesVBox;

	@FXML
	private Button saveButton;

	@FXML
	private Button changeImageButton;

	@FXML
	private ImageView imageView;

	private ChoiceBox<Collection> collections;

	// The caller will want to be refreshed when a new work is added
	private WorkListController caller;

	private class saveThread extends Task<Void> {
		final private Work work;

		public saveThread(Work workToSave) {
			work = workToSave;
			work.setTitle(propertiesMap.get("Titel").getText());
			work.setCreator(propertiesMap.get("Kunstenaar").getText());
			work.setBreedte(Double.valueOf(propertiesMap.get("Breedte")
					.getText().replace(',', '.')));
			work.setHoogte(Double.valueOf(propertiesMap.get("Hoogte").getText()
					.replace(',', '.')));
			work.setMedium(propertiesMap.get("Medium").getText());
			work.setVorigeEigenaar(propertiesMap.get("Vorige Eigenaar")
					.getText());
			work.setJaar(Integer.valueOf(propertiesMap.get("Jaar").getText()));
			work.setThema(propertiesMap.get("Thema").getText());
			work.setPersonen(propertiesMap.get("Personen").getText());
			work.setOpmerking(propertiesMap.get("Opmerking").getText());
			work.setAfbeeldingspad(propertiesMap.get("AfbeeldingsPad")
					.getText());
			
			work.setCollectie(collections.getSelectionModel().getSelectedItem());

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
			saveButton.setDisable(true);
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		logger.debug("javaFX version: "
				+ com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());

		// Ask the user for confirmation before closing the window
		saveButton.setDisable(true);
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new saveThread(work).run();
			}
		});

		changeImageButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO :save last choosen location
				FileChooser fileChooser = new FileChooser();
				fileChooser.setInitialDirectory(new File(SCANS_DIR));
				File imageFile = fileChooser.showOpenDialog(null);

				if (imageFile != null) {

					String newFileName = copyFileToImageRepo(imageFile
							.getAbsolutePath());

					work.setAfbeeldingspad(newFileName);

					loadImage();
					saveButton.setDisable(false);
				}

			}

			private String copyFileToImageRepo(String absolutePath) {
				String newFileName = UUID.randomUUID().toString();
				File originalFile = new File(absolutePath);
				Path newFilePath = Paths.get(IMAGE_REPO, newFileName + "."
						+ FilenameUtils.getExtension(absolutePath));

				try {
					Files.copy(Paths.get(originalFile.getPath()), newFilePath,
							StandardCopyOption.COPY_ATTRIBUTES);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,
							"Kan de afbeelding niet kopieren!", "error",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					propertiesMap.get("AfbeeldingsPad").setText("");
					return null;
				}
				propertiesMap.get("AfbeeldingsPad").setText(
						newFilePath.toString());
				new saveThread(work).run();
				return newFilePath.toString();
			}
		});

		HBox.setHgrow(imageView, Priority.ALWAYS);
	}

	private void addProperties() {
		loadImage();

		addPropertyToBox("Titel", work.getTitle());
		propertiesMap.get("Titel").textProperty()
				.addListener(new InvalidationListener() {

					@Override
					public void invalidated(Observable observable) {
						StringProperty stringProperty = (StringProperty) observable;
						if (stringProperty.getValue().isEmpty()) {
							changeImageButton.setDisable(true);
						} else {
							changeImageButton.setDisable(false);
						}
					}
				});

		addPropertyToBox("Kunstenaar", work.getCreator());
		addPropertyToBox("Breedte", String.valueOf(work.getBreedte()));
		addPropertyToBox("Hoogte", String.valueOf(work.getHoogte()));
		addPropertyToBox("Medium", work.getMedium());
		addPropertyToBox("Vorige Eigenaar", work.getVorigeEigenaar());
		addPropertyToBox("Jaar", String.valueOf(work.getJaar()));
		addPropertyToBox("Thema", work.getThema());
		addPropertyToBox("Personen", work.getPersonen(), 2);
		addPropertyToBox("Opmerking", work.getOpmerking(), 3);
		addPropertyToBox("AfbeeldingsPad", work.getAfbeeldingspad());

		// collection ComboBox
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(5, 10, 5, 12));
		hbox.setSpacing(20);

		Label label = new Label();
		label.setText("Collection");
		label.setMaxWidth(LABEL_WIDTH);
		label.setMinWidth(LABEL_WIDTH);

		collections = new ChoiceBox<>(
				FXCollections.observableArrayList(collectionService.getAll()));
		hbox.getChildren().addAll(label, collections);
		collections.getSelectionModel().select(work.getCollectie());
		collections.getSelectionModel().selectedIndexProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable observable) {
				setChanged(true);
				
			}
		});
		propertiesVBox.getChildren().add(hbox);

	}

	private void loadImage() {
		double imageViewWidth = imageView.getFitWidth();

		if (!work.getAfbeeldingspad().isEmpty()) {
			Image image = new Image(work.getAfbeeldingspad());
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			imageView.setCache(true);
			imageView.setFitWidth(imageViewWidth);
			imageView.setImage(image);
		} else {
			imageView.setImage(null);
		}

	}

	private void setChanged(boolean changed) {
		this.changed = changed;
		saveButton.setDisable(!changed);
	}

	public void setWork(Work work, WorkListController caller) {
		if (work.getId() != null) {
			this.work = workService.getById(work.getId());
		} else {
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
