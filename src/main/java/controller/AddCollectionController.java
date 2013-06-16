package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.CollectionServiceHibernateImpl;
import service.ICollectionService;
import domain.Collection;

public class AddCollectionController implements Initializable {

	@FXML
	private Button okButton;

	@FXML
	private Button cancelButton;

	@FXML
	private TextField nameTextField;

	private static final ICollectionService collectionService = new CollectionServiceHibernateImpl();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) cancelButton.getScene().getWindow();
				stage.close();
			}
		});

		okButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Collection col = new Collection();
				col.setName(nameTextField.getText());

				collectionService.insertOrUpdate(col);

				Stage stage = (Stage) cancelButton.getScene().getWindow();
				stage.close();
			}
		});
	}

}
