package application;

import event.LibReserveEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControlPane extends VBox {

	public ControlPane() {
		super();
		this.setPadding(new Insets(10));
		this.setPrefHeight(500);
		this.setAlignment(Pos.CENTER);
		Label title = new Label("Engineering Library");
		this.getChildren().add(title);
		title.setStyle("-fx-font-size: 23px; -fx-font-weight: bold");
		
		LoginPane loginPane = new LoginPane();
		loginPane.addEventHandler(LibReserveEvent.NAVIGATE, e -> {
			System.out.println("ControlPanal");
			fireEvent(e);
		});
		this.getChildren().add(loginPane);
	}
	
}