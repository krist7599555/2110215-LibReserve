package application;

import java.util.HashMap;
import java.util.Map;

import event.LibReserveEvent;
//import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
//import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
import javafx.scene.text.TextAlignment;

public class SecondFl extends Pane {
	
	Map<String, Button> btns;

	public SecondFl() {
		
		this.setPrefSize(700, 350);

		RegionButton x1 = new RegionButton("---X---", 115.5, 59.5, 0, 0, true);
		RegionButton x2 = new RegionButton("X", 42, 38.5, 616, 115.5, true);
		RegionButton ladder = new RegionButton("Ladder", 63, 80.5, 553, 269.5, true);
		RegionButton zoneG = new RegionButton("G", 178.5, 87.5, 182, 115.5);
		RegionButton zoneH = new RegionButton("H", 35, 175, 115.5, 115.5);
		RegionButton zoneI = new RegionButton("I", 115.5, 262.5, 0, 87.5);
		RegionButton zoneJ = new RegionButton("J", 42, 115.5, 616, 154);
		RegionButton zoneK = new RegionButton("K", 339.5, 49, 182, 220.5);
		RegionButton zoneL = new RegionButton("L", 437.5, 59.5, 115.5, 290.5);
		RegionButton zoneM = new RegionButton("M", 476, 80.5, 182, 0);

		ladder.setTextAlignment(TextAlignment.CENTER);
		
	    Label circu = new Label("Circulation Issues and Achieves Room");
	    circu.setRotate(-90);
	    circu.setPrefSize(350, 42);
	    circu.setAlignment(Pos.CENTER);
	    HBox labelPane = new HBox();
	    labelPane.setPrefSize(42, 350);
	    labelPane.setLayoutX(658);
	    labelPane.setLayoutY(0);
	    labelPane.setAlignment(Pos.CENTER);
	    Group group = new Group(circu);
	    
	    labelPane.getChildren().add(group);
	    
	    this.getChildren().addAll(x1, x2, ladder, zoneG, zoneH, zoneI, zoneJ, zoneK, zoneL, zoneM, labelPane);
	    
		btns = new HashMap<>();
		btns.put("G", zoneG);
		btns.put("H", zoneH);
		btns.put("I", zoneI);
		btns.put("J", zoneJ);
		btns.put("K", zoneK);
		btns.put("L", zoneL);
		btns.put("M", zoneM);
		for (var i : btns.entrySet()) {
			Button btn = i.getValue();
			btn.getStyleClass().add("zone-region");
			btn.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
				this.fireEvent(new LibReserveEvent(LibReserveEvent.SELECTED, i.getKey()));
			});
		}
		
	}

}
