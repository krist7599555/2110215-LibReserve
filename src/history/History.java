package history;

import java.util.ArrayList;

import org.json.JSONObject;

import database.Database;
import event.LibReserveEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class History extends HBox {

	private final ArrayList<Log> logs = new ArrayList<Log>();
	private final ObservableList<HistoryLog> labelList = FXCollections.observableArrayList();
	
	public History() {
		this("A1");
	}
	public History(String position) {
		super();
		final VBox root = new VBox();

		root.setPadding(new Insets(10));
		root.setAlignment(Pos.CENTER);
		root.setSpacing(5);

		final ListView<HistoryLog> listView = new ListView<HistoryLog>(labelList);
		root.getChildren().addAll(listView);

		for (JSONObject jo : Database.getPositionRecord(position)) {		
			
			final Log log = new Log(jo);
			System.out.println(log.toString());
			logs.add(new Log(jo));
			final HistoryLog nameLabel = new HistoryLog(log);
			
			nameLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
					final LogStage popup = new LogStage(log, true);
					popup.addEventHandler(LibReserveEvent.DELETE_LOG, event -> {
						final LogStage lgst = (LogStage) event.getParam();
						System.out.println("try to delete record: " + lgst.log.getUser());
					});

				}
			});
			labelList.add(nameLabel);
		}
		this.getChildren().add(root);
	}

}
