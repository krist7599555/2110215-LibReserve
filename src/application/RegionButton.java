package application;

import javafx.scene.control.Button;

public class RegionButton extends Button {
	
	public RegionButton(String text, double sizeX, double sizeY, double posX, double posY, boolean isDisabled) {
		this.setText(text);
		this.setPrefSize(sizeX, sizeY);
		this.setLayoutX(posX);
		this.setLayoutY(posY);
		this.setDisable(isDisabled);
	}

	public RegionButton(String text, double sizeX, double sizeY, double posX, double posY) {
		this(text, sizeX, sizeY, posX, posY, false);
	}

}
