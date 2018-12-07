package application;

import java.util.HashMap;
import java.util.Map;

//import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
//import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
import javafx.scene.text.TextAlignment;

public class SecondFl extends Pane {
	
	Map<String, Button> btns;

	public SecondFl() {
		
		this.setPrefSize(500, 250);

		RegionButton x1 = new RegionButton("---X---", 82.5, 42.5, 0, 0, true);
		RegionButton x2 = new RegionButton("X", 30, 27.5, 440, 82.5, true);
		RegionButton ladder = new RegionButton("Lad\nder", 45, 57.5, 395, 192.5, true);
		ladder.setTextAlignment(TextAlignment.CENTER);
		RegionButton zoneG = new RegionButton("G", 127.5, 62.5, 130, 82.5);
		RegionButton zoneH = new RegionButton("H", 25, 125, 82.5, 82.5);
		RegionButton zoneI = new RegionButton("I", 82.5, 187.5, 0, 62.5);
		RegionButton zoneJ = new RegionButton("J", 30, 82.5, 440, 110);
		RegionButton zoneK = new RegionButton("K", 242.5, 35, 130, 157.5);
		RegionButton zoneL = new RegionButton("L", 312.5, 42.5, 82.5, 207.5);
		RegionButton zoneM = new RegionButton("M", 340, 57.5, 130, 0);
		
	    Label circu = new Label("Circulation Issues and Achieves Room");
	    circu.setRotate(-90);
	    circu.setPrefSize(250, 30);
	    circu.setAlignment(Pos.CENTER);
	    HBox labelPane = new HBox();
	    labelPane.setPrefSize(30, 250);
	    labelPane.setLayoutX(470);
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
		
	}

}
