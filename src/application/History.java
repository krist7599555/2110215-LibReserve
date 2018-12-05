package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventType;
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

class Log {
	static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	static final SimpleDateFormat DATEONLYFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public String user;
	public String zone;
	public Integer seat;
	public String startTime;
	public String endTime;
	public String reserveTime;

	static String now() {
		return DATEFORMAT.format(new Date());
	}

	static String toSimpleDate(String date) {
		try {
			Calendar c = Calendar.getInstance();
			for (int i = -1; i <= 1; ++i) {
				c.setTime(DATEFORMAT.parse(now()));
				c.add(Calendar.DATE, i);
				String newstr = DATEONLYFORMAT.format(c.getTime());
				date = date.replace(newstr, i == -1 ? "yesterday" : i == 0 ? "today" : i == 1 ? "tomorrow" : "");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public Log(String user, String zone, Integer seat) {
		this(user, zone, seat, now(), now(), now());
	}

	public Log(String user, String zone, Integer seat, String startTime, String endTime, String reserveTime) {
		this.user = user;
		this.zone = zone;
		this.seat = seat;
		this.startTime = startTime;
		this.endTime = endTime;
		this.reserveTime = reserveTime;
	}

	String getUser() {
		return user;
	}

	String getZone() {
		return zone;
	}

	String getSeat() {
		return seat.toString();
	}

	String getStartTime() {
		return toSimpleDate(this.startTime);
	}

	String getEndTime() {
		return toSimpleDate(this.endTime);
	}

	String getReserveTime() {
		return toSimpleDate(this.reserveTime);
	}

	String getPosition() {
		return getZone() + getSeat();
	}

	String getTitle() {
		return getZone() + getSeat() + " " + getStartTime();
	}
};

class LogStage extends Stage {
	
	Log log;
	Button exit;
	Button cancelReserve;

	LogStage(Log log) {
		this(log, false);
	}

	LogStage(Log log, boolean allowcancel) {
		this.log = log;
		this.setTitle(log.getTitle());

		VBox root = new VBox(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().add(new Label(log.getUser() + " (" + log.getPosition() + ")"));
		root.getChildren()
				.add(new Label(log.getStartTime() + "-" + log.getEndTime().substring(log.getEndTime().length() - 5)));
		root.getChildren().add(new Label("reserve at " + log.getReserveTime()));

		HBox btns = new HBox(5);
		if (allowcancel) {
			btns.getChildren().add(this.cancelReserve = new Button("cancel reserve"));
			this.cancelReserve.setOnAction(e -> {
				System.out.println("On ACtion");
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

		Scene scene = new Scene(root, 300, 200);
		this.setScene(scene);
		this.show();
	}
};

class HistoryLog extends HBox {
	public HistoryLog(Log log) {
		super();
		Label nm = new Label(log.getUser());
		Label tm = new Label(log.getReserveTime());
		Region rg = new Region();
		HBox.setHgrow(rg, Priority.ALWAYS);
		this.getChildren().addAll(nm, rg, tm);
	}
}

public class History extends HBox {

	private final String[] NAME_LIST = { "Cherprang", "Faii", "Fond", "Jennis", "June", "Kaew", "Kaimook", "Mobile",
			"Munich", "Music", "6030265431", "Orn", "6031301721", "Pun", "Natherine" };
	private ObservableList<HistoryLog> labelList = FXCollections.observableArrayList();

	public History() {
		super();
		VBox root = new VBox();

		root.setPadding(new Insets(10));
		root.setAlignment(Pos.CENTER);
		root.setSpacing(5);

		ListView<HistoryLog> listView = new ListView<HistoryLog>(labelList);
		root.getChildren().addAll(listView);

		for (String name : NAME_LIST) {
			Log log = new Log(name, "A", 16);
			HistoryLog nameLabel = new HistoryLog(log);
			nameLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 2) {
					LogStage popup = new LogStage(log, true);
					popup.addEventHandler(LibReserveEvent.DELETE_LOG, event -> {
						LogStage lgst = (LogStage) event.getParam();
						System.out.println("Field handled strike: " + lgst.log.getUser());
					});

				}
			});
			labelList.add(nameLabel);
		}
		this.getChildren().add(root);
	}

}
