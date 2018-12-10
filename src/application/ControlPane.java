package application;

import event.LibReserveEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/*
 * ControlPane -> redirect to LoginPane
 */
public class ControlPane extends VBox {

	public ControlPane() {
		super();
		this.setPadding(new Insets(90, 0, 50, 0));
		this.setPrefHeight(500);
		this.setAlignment(Pos.CENTER);
		Label title = new Label("Engineering Library");
		this.getChildren().add(title);
		title.setStyle("-fx-font-size: 23px; -fx-font-weight: bold");
		VBox mapBtnPane = new VBox(15);
		
		Button mapBtn = new Button();
		mapBtnPane.getChildren().add(mapBtn);
		mapBtnPane.setAlignment(Pos.CENTER_RIGHT);
		mapBtn.getStyleClass().add("map-btn");
		mapBtn.setAlignment(Pos.CENTER);
		
		var logoURL = FullMapPopup.class.getClassLoader().getResource("image/location-on-map.png");
		ImageView mapLogo = new ImageView(new Image("file:" + logoURL.getFile()));
		mapLogo.setFitHeight(45);
	    mapLogo.setFitWidth(45);
	    ColorAdjust blackout = new ColorAdjust();
        blackout.setBrightness(1.0);
        mapLogo.setEffect(blackout);
	    
		mapBtn.setGraphic(mapLogo);
		mapBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e  -> FullMapPopup.show());
		
		VBox.setMargin(mapBtn, new Insets(15, 10, 10, 10));
		HBox wrapper = new HBox(mapBtnPane);
		wrapper.setAlignment(Pos.CENTER);
		this.getChildren().add(wrapper);

		LoginPane loginPane = new LoginPane();
		loginPane.addEventHandler(LibReserveEvent.UPDATE_ROUTE, e -> fireEvent(e));
		this.getStyleClass().add("ControlPane");
		this.getChildren().add(loginPane);

		final Region rg = new Region();
		VBox.setVgrow(rg, Priority.ALWAYS);
		this.getChildren().add(rg);
		
		this.getChildren().add(new ContactPane());
	}

}