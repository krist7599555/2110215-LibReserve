package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		GridPane root = new GridPane();
		QuietRoom quietRoom = new QuietRoom();
		root.add(quietRoom, 0, 0);
		root.add(new Map(), 1, 0);
		root.add(new Controll(), 2, 0);
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
		// KT2323
	}

	public static void main(String[] args) {
		launch(args);
	}

}
