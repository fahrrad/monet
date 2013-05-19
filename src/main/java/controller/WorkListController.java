package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import service.IWorkService;
import service.WorkServiceHibernateImpl;
import domain.Work;

public class WorkListController implements Initializable {
	
	@FXML
	private ListView<Work> workList;
	
	private final IWorkService workService = new WorkServiceHibernateImpl();
	
	private final ObservableList<Work> work = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		workList.setItems(work);
		workList.setCellFactory(new Callback<ListView<Work>, ListCell<Work>>() {

			@Override
			public ListCell<Work> call(ListView<Work> param) {				
				return new WorkCell();
			}
		});
		
		loadAllWork();
		
	}
	
	static class WorkCell extends ListCell<Work>{

		@Override
		protected void updateItem(Work item, boolean empty) {
			super.updateItem(item, empty);
			if(item != null){
				setText(String.format("%s (%s)", item.getTitle(), item.getCreator()));
			}
		}
		
	}
	
	private void loadAllWork() {
		work.setAll(FXCollections.observableArrayList(workService.getAll()));
	}
}
