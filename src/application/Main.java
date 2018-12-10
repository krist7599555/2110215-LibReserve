package application;

import database.Config;
import database.Pwd;
import event.LibReserveEvent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/*
 * MAIN class for control
 * 
 * 	1.) left  - PositionSelector
 *  2.) right - ControlPane
 */
public class Main extends Application {

	PositionSelector positionSelector;
	ControlPane ctrlPane;

	@Override
	public void start(Stage primaryStage) {
		GridPane root = new GridPane();
		root.add(positionSelector = new PositionSelector(Config.STARTER_PATH), 0, 0);
		root.add(ctrlPane = new ControlPane(), 2, 0);

		Scene scene = new Scene(root);
		scene.getStylesheets().add("file:" + Pwd.root() + "/application/style.css");
		
		primaryStage.setTitle("Engineering Library");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);

		ctrlPane.addEventHandler(LibReserveEvent.UPDATE_ROUTE, e -> {
			positionSelector.setNavigate((String) e.getParam());
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}
