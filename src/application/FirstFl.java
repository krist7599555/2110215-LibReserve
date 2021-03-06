package application;

import database.Table;
import event.LibReserveEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

/*
 * FirstFl = Button pane represent 1st floor
 * 		- RegionButton as helper class
 */
public class FirstFl extends Pane implements TimeIntervalUpdate {

	Map<String, RegionButton> btns;

	public FirstFl() {

		this.setPrefSize(700, 350);

		RegionButton x1 = new RegionButton("X", 31.5, 42, 378, 0, true);
		RegionButton x2 = new RegionButton("--X--", 157.5, 273, 542.5, 0, true);
		RegionButton ladder = new RegionButton("Ladder", 63, 80.5, 455, 269.5, true);
		RegionButton libr = new RegionButton("", 84, 234.5, 458.5, 0, true);
		RegionButton zoneA = new RegionButton("A", 276.5, 185.5, 133, 70);
		RegionButton zoneB = new RegionButton("B", 280, 42, 98, 0);
		RegionButton zoneC = new RegionButton("C", 280, 59.5, 175, 290.5);
		RegionButton zoneD = new RegionButton("D", 140, 70, 35, 280);
		RegionButton zoneE = new RegionButton("E", 182, 42, 518, 308);
		RegionButton zoneF = new RegionButton("F", 70, 210, 35, 70);

		ladder.setTextAlignment(TextAlignment.CENTER);
		Label label = new Label("Libraian Room");
		label.setRotate(-90);
		libr.setGraphic(new Group(label));

		Label EE = new Label("Enter");
		EE.setAlignment(Pos.CENTER);
		EE.setPrefSize(49, 42);
		EE.setLayoutX(409.5);
		EE.setLayoutY(0);

		Label enthbooks = new Label("English Books and Thai Books");
		enthbooks.setRotate(-90);
		enthbooks.setPrefSize(350, 35);
		enthbooks.setAlignment(Pos.CENTER);
		
		HBox labelPane = new HBox();
		labelPane.setPrefSize(35, 350);
		labelPane.setLayoutX(0);
		labelPane.setLayoutY(0);
		labelPane.setAlignment(Pos.CENTER);
		Group group = new Group(enthbooks);

		labelPane.getChildren().add(group);

		this.getChildren().addAll(x1, x2, ladder, libr, zoneA, zoneB, zoneC, zoneD, zoneE, zoneF, EE, labelPane);

		btns = new HashMap<>();
		btns.put("A", zoneA);
		btns.put("B", zoneB);
		btns.put("C", zoneC);
		btns.put("D", zoneD);
		btns.put("E", zoneE);
		btns.put("F", zoneF);
		for (Entry<String, RegionButton> i : btns.entrySet()) {
			Button btn = i.getValue();
			btn.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
				this.fireEvent(new LibReserveEvent(LibReserveEvent.SELECTED, i.getKey()));
			});
		}

	}

	@Override
	public void intervalUpdate(long s, long t) {
		StringBuilder cnt1 = new StringBuilder();
		for (String seat : Table.getValidSeat(s, t, "[A-F]")) {
			cnt1.append(seat.substring(0, 1));
		}
		String cnt = cnt1.toString();
		for (Entry<String, RegionButton> it : btns.entrySet()) {
			String key = it.getKey();
			RegionButton btn = it.getValue();
			int len = cnt.length() - cnt.replace(key, "").length();
//			var lim = Table.getAllSeats(key).size();
			btn.setText(key + " " + (len));
		}
	}

}
