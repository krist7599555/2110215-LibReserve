package application;

import event.LibReserveEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
 * ControlPane -> redirect to LoginPane
 */
public class ControlPane extends VBox {

	public ControlPane() {
		super();
		this.setPadding(new Insets(10));
		this.setPrefHeight(500);
		this.setAlignment(Pos.CENTER);
		Label title = new Label("Engineering Library");
		this.getChildren().add(title);
		title.setStyle("-fx-font-size: 23px; -fx-font-weight: bold");
		VBox mapBtnPane = new VBox(15);
		Button mapBtn = new Button("View Full Map");
		mapBtnPane.getChildren().add(mapBtn);
		mapBtnPane.setAlignment(Pos.CENTER_RIGHT);
		mapBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				VBox fullMap = new VBox(10);
				fullMap.setPadding(new Insets(10));
				fullMap.setAlignment(Pos.CENTER_RIGHT);
				//String firstFlpath = "res/images/firstFl.png";
				Label firstFlL = new Label("1st Floor");
				firstFlL.setAlignment(Pos.CENTER_RIGHT);
				firstFlL.setStyle("-fx-font-size: 20px; -fx-font-weight:bold");
				Label secondFlL = new Label("2nd Floor");
				secondFlL.setStyle("-fx-font-size: 20px; -fx-font-weight:bold");
				secondFlL.setAlignment(Pos.CENTER_RIGHT);
				ImageView firstFl = new ImageView(new Image("file:res/images/firstFl.png"));
				ImageView secondFl = new ImageView(new Image("file:res/images/secondFl.png"));
				fullMap.getChildren().addAll(secondFlL, secondFl, firstFlL, firstFl);
				Scene mapScene = new Scene(fullMap, 620, 700);
				Stage stage = new Stage();
				stage.setScene(mapScene);
				stage.show();
			}
		});
		VBox.setMargin(mapBtn, new Insets(10));
		this.getChildren().add(mapBtnPane);

		LoginPane loginPane = new LoginPane();
		loginPane.addEventHandler(LibReserveEvent.UPDATE_ROUTE, e -> fireEvent(e));
		this.getChildren().add(loginPane);
	}

}