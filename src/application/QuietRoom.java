package application;

import java.util.ArrayList;
import java.util.HashMap;

import database.Table;
import history.HistoryWrapper;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

class ButtonMatrix {
	static public Button[][] toButtonMatrix(final String[][] str) {
		int n1 = str.length;
		int n2 = n1 == 0 ? 0 : str[0].length;
		Button[][] res = new Button[n1][n2];
		for (int i = 0; i != n1; ++i) {
			for (int j = 0; j != n2; ++j) {
				res[i][j] = new Button(str[i][j]);
			}
		}
		return res;
	}

	static public void paintOnGrid(GridPane grid, Button[][] btn, int startcol, int startrow, int szcol, int szrow) {
		int n1 = btn.length;
		int n2 = n1 == 0 ? 0 : btn[0].length;
		for (int i = 0; i != n1; ++i) {
			for (int j = 0; j != n2; ++j) {
				grid.add(btn[i][j], startcol + j * szcol, startrow + i * szrow, szcol, szrow);
			}
		}
	}

	static public <T> ArrayList<T> toArrayList(T[][] rs) {
		ArrayList<T> res = new ArrayList<T>();
		for (T[] ls : rs) {
			for (T val : ls) {
				res.add(val);
			}
		}
		return res;
	}
};

public class QuietRoom extends VBox {
	
	private GridPane grid;
	private Map<String, Button> btns;
	private HistoryWrapper activePopup;

	public QuietRoom() {
		this("B");
	}
	public QuietRoom(String zone) {
		super();
		this.updateZone(zone);
	}

	public void updateZone(String zone) {
		
		grid = new GridPane();		
		btns = new HashMap<String, Button>();
		
		grid.setPadding(new Insets(10));
		
		try {
			JSONObject detail = Table.getZone(zone);

			int sr = detail.getInt("startrow");
			int sc = detail.getInt("startcol");
			int zr = detail.getInt("sizerow");
			int zc = detail.getInt("sizecol");
			
//			String nm = detail.getString("name");
			
			ArrayList<ArrayList<String>> mat = Table.getSeats(zone);
			
			for (int i = 0; i < mat.size(); ++i) {
				for (int j = 0; j < mat.get(i).size(); ++j) {
					String str = mat.get(i).get(j);
					if (str.equals("")) continue;
					Button btn = new Button(str);
					btn.setPrefSize(45, 30);
					grid.add(btn, sr + j * zr, sc + i * zc, zr, zc);
					btns.put(str, btn);
					btn.setOnAction(e -> {
						if (activePopup != null) {
							activePopup.close();
						}
						activePopup = new HistoryWrapper(btn.getText());
					});
				}
			}
		} catch (JSONException e) {}
		
		this.getChildren().clear();
		this.getChildren().addAll(new Label("Hello"), grid);
	}

}
