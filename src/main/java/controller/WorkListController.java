package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.CollectionServiceHibernateImpl;
import service.ICollecionService;
import service.IWorkService;
import service.WorkServiceHibernateImpl;
import view.WorkView;
import domain.Work;

public class WorkListController implements Initializable {

	/**
	 * FXML components
	 * 
	 */
	@FXML
	private ListView<Work> workList;

	@FXML
	private TextField titleTextField;

	@FXML
	private TextField creatorTextField;

	@FXML
	private ChoiceBox<String> collectionChoiceBox;

	@FXML
	private Button filterButton;

	@FXML
	private Button clearButton;

	@FXML
	private Accordion accordion;

	@FXML
	private Button detailButton;

	@FXML
	private Button delButton;

	@FXML
	private Button addButton;

	/**
	 * services
	 */
	private final IWorkService workService = new WorkServiceHibernateImpl();

	private final ICollecionService collectionService = new CollectionServiceHibernateImpl();

	/**
	 * Observable Lists
	 * 
	 */
	public final ObservableList<Work> work = FXCollections
			.observableArrayList();

	public final ObservableList<String> collections = FXCollections
			.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// loading data
		loadAllWork();
		loadCollections();

		// Setting items
		workList.setItems(work);
		workList.setCellFactory(new Callback<ListView<Work>, ListCell<Work>>() {

			@Override
			public ListCell<Work> call(ListView<Work> param) {
				return new WorkCell();
			}
		});

		collectionChoiceBox.setItems(collections);

		// adding action handlers
		filterButton.setOnAction(new FilterEventHandler());
		clearButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				titleTextField.setText("");
				creatorTextField.setText("");
				collectionChoiceBox.getSelectionModel().clearSelection();

				loadAllWork();

			}
		});
		delButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Work work = workList.getSelectionModel().getSelectedItem();
				workService.delete(work.getId());
				workList.getItems().remove(work);
			}
		});
		addButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

			}
		});

		detailButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stage workViewStage = new Stage();
				Work work = workList.getSelectionModel().getSelectedItem();

				if (work != null) {
					try {

						workViewStage.setScene(WorkView.getInstance());
						workViewStage.show();
						WorkView.getController().setWork(work);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

	}

	private class FilterEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			work.setAll(workService.getByQuery(buildHql()));

		}

		private String buildHql() {
			StringBuilder query = new StringBuilder();

			if (!titleTextField.getText().isEmpty()) {
				query.append(String.format(" w.title like '%%%s%%' \n",
						titleTextField.getText()));
			}
			if (!creatorTextField.getText().isEmpty()) {
				if (query.length() > 0) {
					query.append(" and ");
				}
				query.append(String.format(" w.creator like '%%%s%%' \n",
						creatorTextField.getText()));
			}
			if (collectionChoiceBox.getSelectionModel().getSelectedItem() != null) {
				if (query.length() > 0) {
					query.append(" and ");
				}
				query.append(String.format(" c.title like '%%%s%%' \n",
						collectionChoiceBox.getSelectionModel()
								.getSelectedItem()));
			}

			if (query.length() > 0) {
				query.insert(0, "where ");
			}

			query.insert(0,
					"from Work as w left outer join w.collecties as c\n");
			query.insert(0, "select w\n");

			return query.toString();
		}

	}

	static class WorkCell extends ListCell<Work> {

		@Override
		protected void updateItem(Work item, boolean empty) {
			super.updateItem(item, empty);
			if (item != null) {
				setText(String.format("%s (%s)", item.getTitle(),
						item.getCreator()));
			}
		}

	}

	private void loadAllWork() {
		work.setAll(workService.getAll());
	}

	private void loadCollections() {
		collections.setAll(collectionService.getAllNames());
	}
}
