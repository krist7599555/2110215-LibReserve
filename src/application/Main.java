package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class KTMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		GridPane root = new GridPane();
		QuietRoom quietRoom = new QuietRoom();
		root.add(quietRoom, 0, 0);
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
