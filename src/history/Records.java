package history;

import java.util.ArrayList;
import java.util.function.Supplier;


import org.json.JSONObject;

import event.LibReserveEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import history.HistoryLog;


public class Records extends VBox {
	protected final ObservableList<HistoryLog> labelList = FXCollections.observableArrayList();
	private Supplier<ArrayList<JSONObject>> getRecords;
	private ListView<HistoryLog> listView;
	private Log currentLog;
	
	public Records(Supplier<ArrayList<JSONObject>> getRecords) {
		this.setAlignment(Pos.CENTER);
		listView = new ListView<>(labelList);
		listView.getStyleClass().add("ListView");
		this.getChildren().addAll(listView);
		this.setAlignment(Pos.CENTER);
		this.getStyleClass().add("Records");
		
		this.getRecords = getRecords;
		initialze();
	}
	
	public void initialze() {
		labelList.clear();
		for (JSONObject jo : this.getRecords.get()) {
			Log log = new Log(jo);
			HistoryLog nameLabel = new HistoryLog(log);

			nameLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				if (e.getButton().equals(MouseButton.PRIMARY)) {
					fireEvent(new LibReserveEvent(LibReserveEvent.FOCUS_LOG, setCurrentLog(log)));
					focus(log);
				}
			});
			labelList.add(nameLabel);
		}
	}

	public void remove(Log param) {
		for (int i = 0; i < labelList.size(); ++i) {
			if (labelList.get(i).getLog().equals(param)) {
				labelList.remove(i);
			}
		}
	}

	public Log getCurrentLog() {
		return currentLog;
	}

	public Log setCurrentLog(Log currentLog) {
		if (this.currentLog != currentLog) {
			this.currentLog = currentLog;
			initialze();
			focus(currentLog);
		}
		return currentLog;
	}
	public void focus(Log log) {
		for (int i = 0; i != labelList.size(); ++i) {
			Log nxt = labelList.get(i).getLog();
			if (nxt.equals(log)) {				
				listView.getFocusModel().focus(i);
			}
		}
	}
}
