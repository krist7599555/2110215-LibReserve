package application;

import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SecondFl extends Application {
	
	Map<String, Button> btns;

	@Override
	public void start(Stage primaryStage) {
		Pane root = new Pane();
		root.setPrefSize(1000, 500);

		Button x1 = new Button("---------------\n-------X-------\n---------------");
		x1.setPrefSize(165, 85);
		x1.setDisable(true);
		x1.setLayoutX(0);
	    x1.setLayoutY(0);
	    
	    Button x2 = new Button("--X--");
	    x2.setPrefSize(60, 55);
	    x2.setDisable(true);
	    x2.setLayoutX(880);
		x2.setLayoutY(165);
		
		Button ladder = new Button("Ladder");
	    ladder.setPrefSize(90, 115);
	    ladder.setDisable(true);
	    ladder.setLayoutX(790);
		ladder.setLayoutY(385);
	    
	    Button zoneG = new Button("G");
	    zoneG.setPrefSize(255, 125);
	    zoneG.setLayoutX(260);
	    zoneG.setLayoutY(165);
	    
	    Button zoneH = new Button("H");
	    zoneH.setPrefSize(50, 250);
	    zoneH.setLayoutX(165);
	    zoneH.setLayoutY(165);
	    
	    Button zoneI = new Button("I");
	    zoneI.setPrefSize(165, 375);
	    zoneI.setLayoutX(0);
	    zoneI.setLayoutY(125);
	    
	    Button zoneJ = new Button("J");
	    zoneJ.setPrefSize(60, 165);
	    zoneJ.setLayoutX(880);
	    zoneJ.setLayoutY(220);
	    
	    Button zoneK = new Button("K");
	    zoneK.setPrefSize(485, 70);
	    zoneK.setLayoutX(260);
	    zoneK.setLayoutY(315);
	    
	    Button zoneL = new Button("L");
	    zoneL.setPrefSize(625, 85);
	    zoneL.setLayoutX(165);
	    zoneL.setLayoutY(415);
	    
	    Button zoneM = new Button("M");
	    zoneM.setPrefSize(680, 115);
	    zoneM.setLayoutX(260);
	    zoneM.setLayoutY(0);
	    
	    Label circu = new Label("Circulation Issues and Achieves Room");
	    circu.setRotate(-90);
	    circu.setPrefSize(500, 60);
	    circu.setAlignment(Pos.CENTER);
	    HBox labelPane = new HBox();
	    labelPane.setPrefSize(60, 500);
	    labelPane.setLayoutX(940);
	    labelPane.setLayoutY(0);
	    labelPane.setAlignment(Pos.CENTER);
	    Group group = new Group(circu);
	    
	    labelPane.getChildren().add(group);
	    
	    root.getChildren().addAll(x1, x2, ladder, zoneG, zoneH, zoneI, zoneJ, zoneK, zoneL, zoneM, labelPane);
	    
		Scene scene = new Scene(root, 1000, 500);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Library 2nd Fl.");
		primaryStage.show();
		
		btns.put("G", zoneG);
		btns.put("H", zoneH);
		btns.put("I", zoneI);
		btns.put("J", zoneJ);
		btns.put("K", zoneK);
		btns.put("L", zoneL);
		btns.put("M", zoneM);
		
	}

	public static void main(String[] args) {
		
		launch(args);
	}
}
