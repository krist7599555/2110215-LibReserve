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

public class FirstFl extends Pane {

	Map<String, Button> btns;

	public FirstFl() {
		this.setPrefSize(1000, 500);

		Button x1 = new Button("X");
		x1.setPrefSize(45, 60);
		x1.setDisable(true);
		x1.setLayoutX(540);
	    x1.setLayoutY(0);
	    x1.getStyleClass().addAll("zone-region", "is-disabled");
	    
	    Button x2 = new Button("--X--");
	    x2.setPrefSize(225, 390);
	    x2.setDisable(true);
	    x2.setLayoutX(775);
		x2.setLayoutY(0);
		x2.getStyleClass().addAll("zone-region", "is-disabled");
		
		Button ladder = new Button("Ladder");
	    ladder.setPrefSize(90, 115);
	    ladder.setDisable(true);
	    ladder.setLayoutX(650);
		ladder.setLayoutY(385);
		ladder.getStyleClass().addAll("zone-region", "is-disabled");
		
	    Label EE = new Label("Entrance/\nExit");
	    EE.setAlignment(Pos.CENTER);
	    EE.setPrefSize(70, 60);
	    EE.setLayoutX(585);
	    EE.setLayoutY(0);
	    EE.getStyleClass().addAll("zone-region", "is-disabled");
	    
	    Button libr = new Button("Librarian Room");
	    libr.setPrefSize(120, 335);
	    libr.setDisable(true);
	    libr.setLayoutX(655);
	    libr.setLayoutY(0);
	    libr.getStyleClass().addAll("zone-region", "is-disabled");
	    
	    Button zoneA = new Button("A");
	    zoneA.setPrefSize(395, 265);
	    zoneA.setLayoutX(190);
	    zoneA.setLayoutY(100);
	    
	    Button zoneB = new Button("B");
	    zoneB.setPrefSize(400, 60);
	    zoneB.setLayoutX(140);
	    zoneB.setLayoutY(0);
	    
	    Button zoneC = new Button("C");
	    zoneC.setPrefSize(400, 85);
	    zoneC.setLayoutX(250);
	    zoneC.setLayoutY(415);
	    
	    Button zoneD = new Button("D");
	    zoneD.setPrefSize(200, 100);
	    zoneD.setLayoutX(50);
	    zoneD.setLayoutY(400);
	    
	    Button zoneE = new Button("E");
	    zoneE.setPrefSize(260, 60);
	    zoneE.setLayoutX(740);
	    zoneE.setLayoutY(440);
	    
	    Button zoneF = new Button("F");
	    zoneF.setPrefSize(100, 300);
	    zoneF.setLayoutX(50);
	    zoneF.setLayoutY(100);
	    
	    Label enthbooks = new Label("English Books and Thai Books");
	    enthbooks.setRotate(-90);
	    enthbooks.setPrefSize(500, 50);
	    enthbooks.setAlignment(Pos.CENTER);
	    HBox labelPane = new HBox();
	    labelPane.setPrefSize(50, 500);
	    labelPane.setLayoutX(0);
	    labelPane.setLayoutY(0);
	    labelPane.setAlignment(Pos.CENTER);
	    Group group = new Group(enthbooks);
	    
	    labelPane.getChildren().add(group);

	    this.getChildren().addAll(x1, x2, ladder, libr, zoneA, zoneB, zoneC, zoneD, zoneE, zoneF, EE, labelPane);
	    this.getStylesheets().add(Pwd.file + "/application/style.css");
	   
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
