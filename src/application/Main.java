package application;

import database.Config;
import database.Pwd;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		GridPane root = new GridPane();
		root.add(new PositionSelector(Config.STARTER_PATH), 0, 0);
		
//		root.add(new SecondFl(), 1, 0);
		root.add(new ControlPane(), 2, 0);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(Pwd.file + "/application/style.css");

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
