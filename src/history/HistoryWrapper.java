package history;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HistoryWrapper extends Stage {
	public HistoryWrapper(String position) {
		this.setTitle("history log : " + position);
		VBox root = new VBox(5);
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(new Label(position), new History(position));
		Scene scene = new Scene(root);
		this.setScene(scene);
		this.show();
	}
}
