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
	VBox floors;
	TimePicker timePicker;
	ObservableList<? extends Node> navigatelist;
	
	public PositionSelector(String path) {
		this();
		updatePath(path);
	}
	
	public PositionSelector() {
		super();
		this.path = "/root";
		this.setAlignment(Pos.CENTER);
		this.setPrefSize(1000, 700);
		floors = new VBox(new Label("loading.."));
		floors.setAlignment(Pos.CENTER);
		navigate = new HBox();
		navigate.setPadding(new Insets(40));
		navigate.setAlignment(Pos.CENTER_LEFT);
		timePicker = new TimePicker(true);
		timePicker.addEventHandler(LibReserveEvent.INPUT_CHANGE, e -> {
			TimePicker tp = (TimePicker) e.getParam();
			System.out.println(": " + tp.highLabel.getText() + "-" + tp.lowLabel.getText());
		});
		
		this.getChildren().add(navigate);
		this.getChildren().add(floors);
		this.getChildren().add(timePicker);
		
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
		int dest = 1;
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
			this.getChildren().set(dest, getFloor());
		}
		if (level >= 1) {
			String zone = nav.substring(0, 1);
			Label zoneLabel = new Label(nav.substring(0, 1));
			navigate.getChildren().add(new Label(" / "));
			navigate.getChildren().add(zoneLabel);
			zoneLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				this.setNavigate(zone);
			});
			this.getChildren().set(dest, getZone(zone));
		}
		if (level >= 2) {
			navigate.getChildren().add(new Label(" / "));
			navigate.getChildren().add(new Label(nav));
			this.getChildren().set(dest, getTable(nav));
		}
		
		timePicker.setOpacity(level == 2 ? 0 : 1);	
	}
	private VBox getFloor() {
		FirstFl floor1 = new FirstFl();
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
	
}
