package history;

import java.util.ArrayList;
import java.util.function.Supplier;

import javax.swing.Action;

import org.json.JSONObject;


import database.Database;
import event.LibReserveEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import history.HistoryLog;


public class Records extends VBox {
	protected final ObservableList<HistoryLog> labelList = FXCollections.observableArrayList();
	private Supplier<ArrayList<JSONObject>> getRecords;
	private Log currentLog;
	
	public Records(Supplier<ArrayList<JSONObject>> getRecords) {
		this.setPadding(new Insets(10));
		this.setAlignment(Pos.CENTER);
		this.setSpacing(0);
		this.getChildren().addAll(new ListView<>(labelList));
		this.setAlignment(Pos.CENTER);
		
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
					fireEvent(new LibReserveEvent(LibReserveEvent.FOCUS_LOG, currentLog = log));
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
}
