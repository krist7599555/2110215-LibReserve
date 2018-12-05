package application;

import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

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

public class QuietRoom extends GridPane {

	private Label walkwayV, walkwayH, walkwayH2;
	
	private Button[][] zone1, zone2, zone3;
	private Button[][][] zones;
	
	public QuietRoom () {
		super();
		this.setPadding(new Insets(10));
		walkwayH = new Label("WalkWay");
		walkwayH.setAlignment(Pos.CENTER);
		walkwayH.setPrefHeight(30);
		GridPane.setHalignment(walkwayH, HPos.CENTER);
		GridPane.setValignment(walkwayH, VPos.CENTER);
		walkwayH2 = new Label("WalkWay");
		walkwayH2.setAlignment(Pos.CENTER);
		walkwayH2.setPrefHeight(30);
		GridPane.setHalignment(walkwayH2, HPos.CENTER);
		GridPane.setValignment(walkwayH2, VPos.CENTER);
		walkwayV = new Label("W\na\nl\nk\nW\na\ny");
		walkwayV.setPrefWidth(30);
		walkwayV.setAlignment(Pos.CENTER);
	
		GridPane.setHalignment(walkwayH, HPos.CENTER);
		GridPane.setValignment(walkwayH, VPos.CENTER);
		
		zone1 = ButtonMatrix.toButtonMatrix(new String[][]{
			{"A1", "A2", "A3", "A4"}
		});
		zone2 = ButtonMatrix.toButtonMatrix(new String[][]{
			{"2A", "2B"},
			{"3A", "3B"},
		});
		zone3 = ButtonMatrix.toButtonMatrix(new String[][]{
			{"41", "42", "43", "4D"}
		});
		zones = new Button[][][] {zone1, zone2, zone3};

		this.add(walkwayV, 0, 0, 1, 6);
		ButtonMatrix.paintOnGrid(this, zone1, 1, 0, 1, 1);
		this.add(walkwayH, 1, 1, 4, 1);
		ButtonMatrix.paintOnGrid(this, zone2, 1, 2, 1, 1);
		this.add(walkwayH2, 1, 4, 4, 1);
		ButtonMatrix.paintOnGrid(this, zone3, 1, 5, 1, 1);
		
		for (Button[][] zone : zones) {
			for (Button btn : ButtonMatrix.toArrayList(zone)) {
				btn.setOnAction(e -> {
					new HistoryWrapper(btn.getText());
//				Alert alert = new Alert(AlertType.INFORMATION);
//		        alert.setTitle("PRESS BTN");
//		        alert.setHeaderText("press");
//		        alert.setContentText(btn.getText());
//		        alert.showAndWait();
//				System.out.println(btn.getText());
				});
			}
		}

	}

}
