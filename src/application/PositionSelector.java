package application;

import database.Store;
import event.LibReserveEvent;
import history.History;
import history.Log;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utility.STD;

/*
 * PositionSelector
 * 		have 3 section
 * 			- navigate router 
 * 				eg. /root/A/A6
 * 				every component is rely on "this.setNavigate"			
 * 
 * 			- middle pane
 * 				- /root 		= [FirstFl, SecondFloor]
 * 				- /root/Z 		= [QuietRoom]
 * 				- /root/Z/Z0	= [History(+position)]
 * 				- /root/history	= [History(+username), ReservePane]
 * 
 *			- interactive bottons
 *				- floor select (FirstFl & SecondFl)
 *				- time interval select (TimePicker)
 */

public class PositionSelector extends VBox {

	String path;
	HBox navigate;
	HBox floorSelector;
	TimePicker timePicker;
	Button fl1btn;
	Button fl2btn;
	
	int currentFloor = 1;

	public PositionSelector(String path) {
		this();
		setNavigate(path);
	}

	public PositionSelector() {
		super(20);
		this.getStyleClass().add("PositionSelector");
		this.path = "/root";
		this.setAlignment(Pos.TOP_CENTER);
		this.setPrefSize(720, 570);

		navigate = new HBox();
		navigate.setPadding(new Insets(15, 20, 15, 20));
		navigate.setAlignment(Pos.CENTER_LEFT);

		floorSelector = new HBox(10);
		floorSelector.setPrefWidth(700);
		floorSelector.setAlignment(Pos.CENTER);
		fl1btn = new Button("Floor 1");
		fl1btn.getStyleClass().addAll("floor-btn", "left");
		fl1btn.getStyleClass().add("is-active");
		fl2btn = new Button("Floor 2");
		fl2btn.getStyleClass().addAll("floor-btn", "right");

		floorSelector.getChildren().addAll(fl1btn, fl2btn);

		timePicker = new TimePicker(true);
		timePicker.addEventHandler(LibReserveEvent.INPUT_CHANGE, e -> {
			this.timeUpdate();
		});

		this.getChildren().add(navigate);
		this.getChildren().add(currentMiddleBox = new VBox());
		this.getChildren().add(floorSelector);
		this.getChildren().add(timePicker);

		fl1btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (!fl1btn.getStyleClass().contains("is-active"))
				fl1btn.getStyleClass().add("is-active");
			fl2btn.getStyleClass().remove("is-active");
			this.currentFloor = 1;
			setNavigate("/root");
		});
		fl2btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (!fl2btn.getStyleClass().contains("is-active"))
				fl2btn.getStyleClass().add("is-active");
			fl1btn.getStyleClass().remove("is-active");
			this.currentFloor = 2;
			setNavigate("/root");
		});

		this.setNavigate("/root");
	}

// 	[1] nav = /root 
//	[2] nav = /root/A 
// 	[3] nav = /root/A/A13
//	[4] nav = /root/history
	void setNavigate(String nav) {
		this.path = nav;
		String back = STD.back(nav.split("/"));
		var child = navigate.getChildren();
		child.clear();
		int level = 0;

		if (nav.matches("/root.*")) {
			Label root = new Label("root");
			root.getStyleClass().add("nav-link");
			navigate.getChildren().add(new Label(" / "));
			navigate.getChildren().add(root);
			root.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				this.setNavigate("/root");
			});
			level |= 1;
			currentMiddleBox = getFloor(currentFloor);
		}
		if (nav.matches("/root/[A-Z]") || nav.matches("/root/[A-Z]/[A-Z][0-9]+")) {
			Label zoneLabel = new Label(back.substring(0, 1));
			zoneLabel.getStyleClass().add("nav-link");
			navigate.getChildren().add(new Label(" / "));
			navigate.getChildren().add(zoneLabel);
			zoneLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				this.setNavigate("/root/" + back.substring(0, 1));
			});
			level |= 2;
			currentMiddleBox = getZone(back);
		}
		if (nav.matches("/root/[A-Z]/[A-Z][0-9]+")) {
			Label seatLabel = new Label(back);
			seatLabel.getStyleClass().add("nav-link");
			navigate.getChildren().add(new Label(" / "));
			navigate.getChildren().add(seatLabel);
			level |= 4;
			currentMiddleBox = getTable(back);
		}
		if (nav.matches("/root/history")) {
			Label historyLabel = new Label("history");
			historyLabel.getStyleClass().add("nav-link");
			navigate.getChildren().add(new Label(" / "));
			navigate.getChildren().add(historyLabel);
			level |= 8;
			currentMiddleBox = getHistory();
		}

//		For Improving in next version
//		currentMiddleBox.addEventHandler(LibReserveEvent.NAVIGATE_BACK, e -> this.navigateBack());

		HBox hb = new HBox(currentMiddleBox);
		hb.setPrefSize(800, 350);
		hb.setAlignment(Pos.CENTER);
		
		this.getChildren().set(1, hb);

		floorSelector.setOpacity(level == 1 || level == 3 ? 1 : 0);
		timePicker.setOpacity(level == 1 || level == 3 || level == 7 ? 1 : 0);

// 	 	Calling middleBox.each(IntervalUpdate)
		timeUpdate(); 
	}

	VBox currentMiddleBox;

	private VBox getFloor(int i) {
		FirstFl floor1 = new FirstFl();
		SecondFl floor2 = new SecondFl();
		floor1.addEventHandler(LibReserveEvent.SELECTED, e -> {
			String zone = (String) e.getParam();
			this.setNavigate("/root/" + zone);
		});
		floor2.addEventHandler(LibReserveEvent.SELECTED, e -> {
			String zone = (String) e.getParam();
			this.setNavigate("/root/" + zone);
		});
		return new VBox(i == 1 ? floor1 : floor2);
	}

	private VBox getZone(String zone) {
		QuietRoom qr = new QuietRoom(zone);
		qr.addEventHandler(LibReserveEvent.UPDATE_ROUTE, e -> {
			String path = (String) e.getParam();
			setNavigate(path);
		});
		return new VBox(qr);
	}

	private VBox getTable(String seat) {
		var historyPane = new History(seat);
		historyPane.addEventHandler(LibReserveEvent.UPDATE_LOG, e -> {
			historyPane.initialize();
		});
		historyPane.addEventHandler(LibReserveEvent.FOCUS_LOG, e -> {
			Log log = (Log) e.getParam();
			timePicker.setLow((long) log.startTime);
			timePicker.setHigh((long) log.endTime);
		});
		return new VBox(historyPane);

	}

	private VBox getHistory() {
		assert (Store.isLogin());
		return new VBox(new History(Store.getUsername(), true));
	}

	public void navigateBack() {
		setNavigate(this.path.substring(0, this.path.length() - STD.back(this.path.split("/")).length() - 1));
	}

	private void timeUpdate() {
		long s = timePicker.lowTime;
		long t = timePicker.highTime;
		var child = currentMiddleBox.getChildren();
		child.forEach(node -> {
			if (node instanceof TimeIntervalUpdate) {
				((TimeIntervalUpdate) node).intervalUpdate(s, t);
			}
		});
	}

}
