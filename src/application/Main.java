package application;

import database.Config;
import database.Pwd;
import event.LibReserveEvent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		GridPane root = new GridPane();
		PositionSelector positionSelector = new PositionSelector(Config.STARTER_PATH);
		root.add(positionSelector, 0, 0);
	
		ControlPane ctrlPane = new ControlPane();
		root.add(ctrlPane, 2, 0);
		
		ctrlPane.addEventHandler(LibReserveEvent.NAVIGATE, e -> {
			String s = (String) e.getParam();
			positionSelector.setNavigate(s);
		});
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(Pwd.file + "/application/style.css");

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
