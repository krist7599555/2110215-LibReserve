package history;

import java.util.ArrayList;

import org.json.JSONObject;

import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class History extends HBox {

	private final ArrayList<Log> logs = new ArrayList<Log>();
	private final ObservableList<HistoryLog> labelList = FXCollections.observableArrayList();
	
	protected Log currentLog;
	protected RightDetail RightDetail;
	
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
				if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() >= 1) {
					this.getChildren().set(1, new RightDetail(currentLog = log));
				}
			});
			labelList.add(nameLabel);
		}
		this.setAlignment(Pos.CENTER_LEFT);
		this.getChildren().addAll(root, new RightDetail());
	}

}

class HistoryLog extends HBox {
	public HistoryLog(final Log log) {
		super();
		final Label nm = new Label(log.getUser());
		final Label tm = new Label(log.getReserveTime());
		final Region rg = new Region();
		HBox.setHgrow(rg, Priority.ALWAYS);
		this.getChildren().addAll(nm, rg, tm);
	}
}

