package application;

import database.Store;
import event.LibReserveEvent;
import history.History;
import history.UserHistory;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class PositionSelector extends VBox {

	String path;
	HBox navigate;
	TimePicker timePicker;
	HBox floorSelector;
	Button fl1btn, fl2btn;
	ObservableList<? extends Node> navigatelist;

	public PositionSelector(String path) {
		this();
		updatePath(path);
	}

	public PositionSelector() {
		super(20);
		this.path = "/root";
		this.setAlignment(Pos.TOP_CENTER);
		this.setPrefSize(700, 600);

		navigate = new HBox();
		navigate.setPadding(new Insets(15, 20, 15, 20));
		navigate.setAlignment(Pos.CENTER_LEFT);

		floorSelector = new HBox(10);
		floorSelector.setPrefWidth(700);
		floorSelector.setAlignment(Pos.CENTER);
		fl1btn = new Button("Floor 1");
		fl2btn = new Button("Floor 2");
		floorSelector.getChildren().addAll(fl1btn, fl2btn);

		timePicker = new TimePicker(true);
		timePicker.addEventHandler(LibReserveEvent.INPUT_CHANGE, e -> {
			TimePicker tp = (TimePicker) e.getParam();
			System.out.println(": " + tp.highLabel.getText() + "-" + tp.lowLabel.getText());
		});

		this.getChildren().add(navigate);
		this.getChildren().add(currentMiddleBox = new VBox());
		this.getChildren().add(floorSelector);
		this.getChildren().add(timePicker);

		this.setNavigate("");
	}

	void updatePath(String str) {
		assert (str.length() > 0);
		this.path = str;
		var toks = str.split("/");
		this.setNavigate(toks[toks.length - 1].replace("root", ""));
	}

	/*
	 * [1] nav = "" -> /root [2] nav = "A" -> /root/A [3] nav = "A13" -> /root/A/A13
	 */
	public void setNavigate(String nav) {
		int dest = 1;
		var child = navigate.getChildren();
		int level = Math.min(2, nav.length());
		child.clear();

		if (nav.equals("/root/history")) {
			System.out.println("Working");
			Label root = new Label("root/history");
			navigate.getChildren().add(new Label(" / "));
			navigate.getChildren().add(root);
			root.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				this.setNavigate("");
			});
			currentMiddleBox = getUserHistory(Store.getUsername());
		} else {
			if (level >= 0) {
				Label root = new Label("root");
				navigate.getChildren().add(new Label(" / "));
				navigate.getChildren().add(root);
				root.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					this.setNavigate("");
				});
				currentMiddleBox = getFloor();
			}
			if (level >= 1) {
				String zone = nav.substring(0, 1);
				Label zoneLabel = new Label(nav.substring(0, 1));
				navigate.getChildren().add(new Label(" / "));
				navigate.getChildren().add(zoneLabel);
				zoneLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					this.setNavigate(zone);
				});
				currentMiddleBox = getZone(zone);
			}
			if (level >= 2) {
				navigate.getChildren().add(new Label(" / "));
				navigate.getChildren().add(new Label(nav));
				currentMiddleBox = getTable(nav);
			}
		}

		HBox hb = new HBox(currentMiddleBox);
		hb.setPrefSize(700, 350);
		hb.setAlignment(Pos.CENTER);
		this.getChildren().set(1, hb);

		floorSelector.setOpacity(level == 2 ? 0 : 1);
		timePicker.setOpacity(level == 2 ? 0 : 1);
	}

	VBox currentMiddleBox;

	private VBox getFloor() {
		FirstFl floor1 = new FirstFl();
		SecondFl floor2 = new SecondFl();
		floor1.addEventHandler(LibReserveEvent.SELECTED, e -> {
			String zone = (String) e.getParam();
			this.setNavigate(zone);
		});
		return new VBox(floor1);
	}

	private VBox getZone(String zone) {
		return new VBox(new QuietRoom(zone) {
			@Override
			void handle(Button btn) {
				setNavigate(btn.getText());
			}
		});
	}

	private VBox getTable(String seat) {
		return new VBox(new History(seat));
	}

	private VBox getUserHistory(String username) {
		return new VBox(new UserHistory(username));
	}

}
