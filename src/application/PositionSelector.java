package application;

import event.LibReserveEvent;
import history.History;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PositionSelector extends VBox {
	
	String path;
	HBox navigate;
	TimePicker timePicker;
	HBox floorSelector;
	ObservableList<? extends Node> navigatelist;
	
	int currentFloor = 1;
	
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
		Button fl1btn = new Button("Floor 1"); fl1btn.getStyleClass().addAll("floor-btn", "left");
		Button fl2btn = new Button("Floor 2"); fl2btn.getStyleClass().addAll("floor-btn", "right");
		
		floorSelector.getChildren().addAll(fl1btn, fl2btn);
		
		timePicker = new TimePicker(true);
		timePicker.addEventHandler(LibReserveEvent.INPUT_CHANGE, e -> {
			TimePicker tp = (TimePicker) e.getParam();
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
			setNavigate("");
		});
		fl2btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (!fl2btn.getStyleClass().contains("is-active"))
				fl2btn.getStyleClass().add("is-active");
			fl1btn.getStyleClass().remove("is-active");
			this.currentFloor = 2;
			setNavigate("");
		});
		
		this.setNavigate("");
	}
	
	void updatePath(String str) {
		assert (str.length() > 0);
		this.path = str;
		var toks = str.split("/");
		this.setNavigate(toks[toks.length - 1].replace("root", ""));
	}
	
	/*	[1] nav = "" -> /root 
	 *  [2] nav = "A" -> /root/A
	 *  [3] nav = "A13" -> /root/A/A13
	 */
	void setNavigate(String nav) {
		var child = navigate.getChildren();
		int level = Math.min(2, nav.length());
		child.clear();
	
		if (level >= 0) {
			Label root = new Label("root");
			navigate.getChildren().add(new Label(" / "));
			navigate.getChildren().add(root);
			root.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				this.setNavigate("");
			});
			currentMiddleBox = getFloor(currentFloor);
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

		HBox hb = new HBox(currentMiddleBox);
		hb.setPrefSize(700, 350);
		hb.setAlignment(Pos.CENTER);
		this.getChildren().set(1, hb);
		
		floorSelector.setOpacity(level == 2 ? 0 : 1);
		timePicker.setOpacity(level == 2 ? 0 : 1);
	}
	VBox currentMiddleBox;
	private VBox getFloor(int i) {
		FirstFl  floor1 = new FirstFl();
		SecondFl floor2 = new SecondFl();
		floor1.addEventHandler(LibReserveEvent.SELECTED, e -> {
			String zone = (String) e.getParam();
			this.setNavigate(zone);
		});
		floor2.addEventHandler(LibReserveEvent.SELECTED, e -> {
			String zone = (String) e.getParam();
			this.setNavigate(zone);
		});
		return new VBox(i == 1 ? floor1 : floor2);
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
	
}
