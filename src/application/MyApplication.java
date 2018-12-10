package application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import database.Pwd;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MyApplication extends Application {

	@Override
	public void start(Stage primaryStage) {
		System.out.println(Pwd.getFile("/database/table.json"));
		primaryStage.setScene(new Scene(new VBox(), 100, 100));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
