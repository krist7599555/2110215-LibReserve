package application;

import java.util.HashMap;
import java.util.Map;

import database.Pwd;
import event.LibReserveEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

public class FirstFl extends Pane {

	Map<String, RegionButton> btns;

	public FirstFl() {
		
		this.setPrefSize(500, 250);

		RegionButton x1 = new RegionButton("X", 22.5, 30, 270, 0, true);
		RegionButton x2 = new RegionButton("--X--", 112.5, 195, 387.5, 0, true);
		RegionButton ladder = new RegionButton("Lad\nder", 45, 57.5, 325, 192.5, true);
		ladder.setTextAlignment(TextAlignment.CENTER);
		RegionButton libr = new RegionButton("", 60, 167.5, 327.5, 0, true);
        Label label = new Label("Libraian Room");
        label.setRotate(-90);
        libr.setGraphic(new Group(label));
		RegionButton zoneA = new RegionButton("A", 197.5, 132.5, 95, 50);
		RegionButton zoneB = new RegionButton("B", 200, 30, 70, 0);
		RegionButton zoneC = new RegionButton("C", 200, 42.5, 125, 207.5);
		RegionButton zoneD = new RegionButton("D", 100, 50, 25, 200);
		RegionButton zoneE = new RegionButton("E", 130, 30, 370, 220);
		RegionButton zoneF = new RegionButton("F", 50, 150, 25, 50);

	    Label EE = new Label("Exit");
	    EE.setAlignment(Pos.CENTER);
	    EE.setPrefSize(35, 30);
	    EE.setLayoutX(292.5);
	    EE.setLayoutY(0);
	    EE.getStyleClass().addAll("zone-region", "is-disabled");
	    
	    Label enthbooks = new Label("English Books and Thai Books");
	    enthbooks.setRotate(-90);
	    enthbooks.setPrefSize(250, 25);
	    enthbooks.setAlignment(Pos.CENTER);
	    HBox labelPane = new HBox();
	    labelPane.setPrefSize(25, 250);
	    labelPane.setLayoutX(0);
	    labelPane.setLayoutY(0);
	    labelPane.setAlignment(Pos.CENTER);
	    Group group = new Group(enthbooks);
	    
	    labelPane.getChildren().add(group);
	    
	    this.getChildren().addAll(x1, x2, ladder, libr, zoneA, zoneB, zoneC, zoneD, zoneE, zoneF, EE, labelPane);

		btns = new HashMap<>();
		btns.put("A", zoneA);
		btns.put("B", zoneB);
		btns.put("C", zoneC);
		btns.put("D", zoneD);
		btns.put("E", zoneE);
		btns.put("F", zoneF);
		for (var i : btns.entrySet()) {
			Button btn = i.getValue();
			btn.getStyleClass().add("zone-region");
			btn.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
				this.fireEvent(new LibReserveEvent(LibReserveEvent.SELECTED, i.getKey()));
			});
		}

	}
	
}
