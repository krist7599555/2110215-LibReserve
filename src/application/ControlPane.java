package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControlPane extends VBox {

	public ControlPane() {
		super();
		this.setPadding(new Insets(10));
		this.setAlignment(Pos.CENTER);
		Label title = new Label("Engineering Library");
		this.getChildren().add(title);
		title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold");
		
		LoginPane loginPane = new LoginPane();
		this.getChildren().add(loginPane);

	}
	
}