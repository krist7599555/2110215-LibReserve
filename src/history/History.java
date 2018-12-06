package application;

import java.util.ArrayList;

import org.json.JSONObject;

import database.Database;
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

class LogStage extends Stage {
	
	Log log;
	Button exit;
	Button cancelReserve;

	LogStage(final Log log) {
		this(log, false);
	}

	LogStage(final Log log, final boolean allowcancel) {
		this.log = log;
		this.setTitle(log.getTitle());

		final VBox root = new VBox(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().add(new Label(log.getUser() + " (" + log.getPosition() + ")"));
		root.getChildren()
				.add(new Label(log.getStartTime() + "-" + log.getEndTime().substring(log.getEndTime().length() - 5)));
		root.getChildren().add(new Label("reserve at " + log.getReserveTime()));

		final HBox btns = new HBox(5);
		if (allowcancel) {
			btns.getChildren().add(this.cancelReserve = new Button("cancel reserve"));
			this.cancelReserve.setOnAction(e -> {
				this.fireEvent(new LibReserveEvent(LibReserveEvent.DELETE_LOG, this));
			});
		}
		btns.getChildren().add(this.exit = new Button("exit"));
		this.exit.setOnAction(e -> {
			System.out.println("exit");
			this.close();
		});

		btns.setAlignment(Pos.CENTER);
		root.getChildren().add(btns);

		final Scene scene = new Scene(root, 300, 200);
		this.setScene(scene);
		this.show();
	}
};

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
