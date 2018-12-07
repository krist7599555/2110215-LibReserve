package application;

import java.util.ArrayList;
import java.util.HashMap;

import database.Table;
import event.LibReserveEvent;
import history.HistoryWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class QuietRoom extends VBox {
	
	private String name = "-- None --";
	private JSONObject detail;
	
	private GridPane grid;
	private Map<String, Button> btns;
	private HistoryWrapper activePopup;
	private TimePicker timePicker;
	private Label label;

	public QuietRoom() {
		this("A");
	}
	public QuietRoom(String zone) {
		super(10);
		this.updateZone(zone);
	}

	public void updateZone(String zone) {
		
		grid = new GridPane();		
		btns = new HashMap<String, Button>();
		
		grid.setPadding(new Insets(10));
		grid.setHgap(1);
		grid.setVgap(3);
		grid.setMinHeight(500);
		grid.setMinWidth(300);
		grid.setAlignment(Pos.CENTER);
		
		try {
			
			detail = Table.getZone(zone);

			int sr = 0; // detail.getInt("startrow");
			int sc = 0; // detail.getInt("startcol");
			int zr = detail.getInt("sizerow");
			int zc = detail.getInt("sizecol");
			
			name = detail.getString("name");
	
			ArrayList<ArrayList<String>> mat = Table.getSeats(zone);
			System.out.println(mat);
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
						btn.setOnAction(e -> {
							if (activePopup != null) {
								activePopup.close();
							}
							activePopup = new HistoryWrapper(btn.getText());
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
		
		this.label = new Label(name);
		this.timePicker = new TimePicker();
		this.timePicker.addEventHandler(LibReserveEvent.INPUT_CHANGE, e -> {
			TimePicker tp = (TimePicker) e.getParam();
			this.label.setText(name + ": " + tp.highLabel.getText() + "-" + tp.lowLabel.getText());
		});
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().clear();
		this.getChildren().addAll(this.label, grid, this.timePicker);
	}

}
