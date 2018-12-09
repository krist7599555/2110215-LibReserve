package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * FullMapPopup = Popup Map View
 */
public class FullMapPopup {
	static private Stage stage = new Stage();
	static public void show() {
		VBox fullMap = new VBox(10);
		fullMap.setPadding(new Insets(10));
		fullMap.setAlignment(Pos.CENTER_RIGHT);
		
		Label firstFlL = new Label("1st Floor");
		firstFlL.setStyle("-fx-font-size: 20px; -fx-font-weight:bold");
		firstFlL.setAlignment(Pos.CENTER_RIGHT);
		
		Label secondFlL = new Label("2nd Floor");
		secondFlL.setStyle("-fx-font-size: 20px; -fx-font-weight:bold");
		secondFlL.setAlignment(Pos.CENTER_RIGHT);
		
		ImageView firstFl = new ImageView(new Image("file:res/images/firstFl.png"));
		ImageView secondFl = new ImageView(new Image("file:res/images/secondFl.png"));
		fullMap.getChildren().addAll(secondFlL, secondFl, firstFlL, firstFl);
		Scene mapScene = new Scene(fullMap, 620, 700);
		
		stage.setScene(mapScene);
		stage.show();
	}
}
