package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ContactPane extends VBox {
	public ContactPane() {
		
		Label location = new Label("Chula Engineering Library");
		Label position = new Label("3rd and 4th floor, Building 3.");
		Label phone = new Label("tel: 02-218-6364");
		
		setSpacing(6);
		getStyleClass().add("Contract");
		setAlignment(Pos.CENTER_LEFT);
		setPrefWidth(300);
		setPadding(new Insets(25, 25, 25, 25));

		this.getChildren().addAll(location, position, phone);
	}
}
