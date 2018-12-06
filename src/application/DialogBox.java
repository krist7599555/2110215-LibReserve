package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DialogBox implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("Test");
		alert.showAndWait();
	}

}