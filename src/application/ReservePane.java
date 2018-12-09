package application;

import database.Database;
import database.Store;
import database.Table;
import event.LibReserveEvent;
import history.Log;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/*
 * ReservePane
 * 		have 2 minor components
 * 			- Button for reserving seat
 * 			- Information about current status
 * 
 * 		FireEvent
 * 			- UPDATE_LOG
 */
public class ReservePane extends HBox implements TimeIntervalUpdate {
	Button submitBtn;
	int s;
	int t;
	String seat;

	ReservePane(String seat, TimePicker time) {
		this.seat = seat;
		this.s = (int) time.lowTime;
		this.t = (int) time.highTime;
		initialize();
	}

	public void initialize() {
		this.getChildren().clear();
		Log log = new Log(Store.isLogin() ? Store.getUsername() : "[NOT LOGIN]", s, t, seat);
		this.setAlignment(Pos.CENTER);
		VBox vb = new VBox();
		vb.getChildren().addAll(new Label("username: " + log.username), new Label("position: " + log.getPosition()),
				new Label("time start: " + log.getStartTime()), new Label("time end: " + log.getEndTime()),
				new Label("duration: " + (log.endTime - log.startTime) + " minute"));
		this.setSpacing(19);
		this.getChildren().addAll(submitBtn = new Button("reserve"), vb);
		submitBtn.getStyleClass().add("reserve-btn");
		submitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (Store.isLogin()) {
				Database.add(log.username, log.startTime, log.endTime, log.position);
				fireEvent(new LibReserveEvent(LibReserveEvent.UPDATE_LOG));
			} else {
				Alert alrt = new Alert(AlertType.ERROR, "Please login", ButtonType.CLOSE);
				alrt.show();
			}
		});
		if (Table.isValidSeat(log.startTime, log.endTime, log.position)) {
			submitBtn.setDisable(false);
		} else {
			submitBtn.setDisable(true);
			submitBtn.getStyleClass().add("is-disabled");
		}
	}

	@Override
	public void intervalUpdate(long s, long t) {
		this.s = (int) s;
		this.t = (int) t;
		initialize();
	}

}
