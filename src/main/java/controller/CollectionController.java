package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import service.CollectionServiceHibernateImpl;
import service.ICollecionService;
import domain.Collection;

public class CollectionController implements Initializable {

	ICollecionService collectionService = new CollectionServiceHibernateImpl();

	@FXML
	private ListView<String> collectionList;

	@FXML
	private Button addButton;

	@FXML
	private Button deleteButton;

	// items of the list
	ObservableList<String> items = FXCollections.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		loadAllCollections();

		collectionList.setItems(items);

		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Stage stage = new Stage();
				Group root = new Group();
				Scene scene = new Scene(root);

				AnchorPane pane;
				try {
					pane = FXMLLoader.load(this.getClass().getResource(
							"../dialog/getText.fxml"));

					root.getChildren().add(pane);
					stage.setScene(scene);

					stage.setOnHidden(new EventHandler<WindowEvent>() {

						@Override
						public void handle(WindowEvent event) {
							System.out.println("reloading all the collections");
							loadAllCollections();
						}
					});

					stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// adding delete action
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String selectedCol = collectionList.getSelectionModel()
						.getSelectedItem();

				if (selectedCol != null) {
					Collection collection = collectionService
							.getByName(selectedCol);
					collectionService.delete(collection.getId());
					items.remove(selectedCol);
				}
			}
		});
	}

	private void loadAllCollections() {
		items.clear();
		items.addAll(collectionService.getAllNames());
	}
}
