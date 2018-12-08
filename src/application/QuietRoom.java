package application;

import java.util.ArrayList;
import java.util.HashMap;

import database.Table;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class QuietRoom extends VBox {
	
	private JSONObject detail;
	
	private GridPane grid;
	private Map<String, Button> btns;

	public QuietRoom(String zone) {
		super(10);
		this.updateZone(zone);
	}

	public void updateZone(String zone) {

		grid = new GridPane();		
		btns = new HashMap<String, Button>();
		
		grid.setHgap(1);
		grid.setVgap(3);
		grid.setAlignment(Pos.CENTER);
		
		try {
			detail = Table.getZone(zone);

			int sr = 0;
			int sc = 0;
			int zr = detail.getInt("sizerow");
			int zc = detail.getInt("sizecol");
	
			ArrayList<ArrayList<String>> mat = Table.getSeats(zone);

			for (int i = 0; i < mat.size(); ++i) {
				for (int j = 0; j < mat.get(i).size(); ++j) {
					String str = mat.get(i).get(j);
					Region nd;
					if (str.equals("")) {
						nd = new HBox();
					} else {
						Button btn;
						nd = btn = new Button(str);
						btn.getStyleClass().addAll("desk-btn");
						btn.getStyleClass().addAll("is-danger");
						btn.setOnMouseClicked(e -> {
							this.handle(btn);
						});
						btns.put(str, btn);
					}
					nd.setPrefSize(45, 30);
					grid.add(nd, sr + j * zr, sc + i * zc, zr, zc);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().clear();
		this.getChildren().addAll(grid);
	}
	
	void handle(Button btn) {
		System.out.println("HANDEL: " + btn.getText());
	}

}
