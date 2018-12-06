package application;

import database.Database;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Map extends HBox {
	public Map() {
		Image img = new Image("file://" + Database.__dirname() + "/src/image/91w2TAiBWIL.jpg");
		ImageView imgview = new ImageView(img);
		imgview.setFitHeight(200);
		imgview.setFitWidth(300);
		this.getChildren().add(imgview);
	}
}
