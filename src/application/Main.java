package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		GridPane root = new GridPane();
		root.setPrefHeight(500);
		QuietRoom quietRoom = new QuietRoom();
		root.add(quietRoom, 0, 0);
		root.add(new Map(), 1, 0);
<<<<<<< HEAD
		root.add(new Controll(), 2, 0);
//		root.add(new History(), 3, 0);
=======
		root.add(new ControlPane(), 2, 0);
>>>>>>> 996505be56431a20b3731f11004b313f65dd6e19
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		
		primaryStage.show();
<<<<<<< HEAD
=======
		
>>>>>>> 996505be56431a20b3731f11004b313f65dd6e19
	}

	public static void main(String[] args) {
		launch(args);
	}

}
