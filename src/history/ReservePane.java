package history;

import application.GroupLoginInput;
import application.LoginPane;
import application.TimeIntervalUpdate;
import database.Database;
import database.Store;
import database.Table;
import event.LibReserveEvent;
import exception.HistoryConflictException;
import exception.LibReserveException;
import exception.NotLoginException;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
	private GroupLoginInput currentGroupLoginInput;

	ReservePane() {
		this("-", 0, 0);
	}

	ReservePane(String seat, int s, int t) {
		this.seat = seat;
		this.s = s;
		this.t = t;
		this.setSpacing(21);
		this.setAlignment(Pos.CENTER);
		this.getStyleClass().add("ReservePane");
		this.getStyleClass().add("notification");
		initialize();
	}

	private Log getLog() {
		return new Log(Store.isLogin() ? Store.getUsername() : "-", s, t, seat);
	}

	public void initialize() {
		this.getChildren().clear();
		Log log = getLog();
		VBox vb = new VBox();

		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(getTable());

		Label submitLbl = new Label("* require " + (Table.getRequireNumber(seat) + " people."));
		if (!Store.isLogin()) {
			submitLbl.setText("* please login");
		}
		submitLbl.getStyleClass().addAll("reserve-label-require", "is-subtitle");
		submitBtn = new Button("reserve");
		submitBtn.setAlignment(Pos.CENTER);
		submitBtn.getStyleClass().addAll("reserve-btn", "is-title");

		submitBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			submitBtn.setDisable(true);
			if (Database.isHistoryConflict(log.username, log.startTime, log.endTime)) {
				new HistoryConflictException().alert();
				submitBtn.setDisable(false);
				return;
			}
			currentGroupLoginInput = new GroupLoginInput(Table.getRequireNumber(log.position), log.position) {
				@Override
				public void onsuccess() {
					try {
						Database.add(log.username, log.startTime, log.endTime, log.position);
						submitBtn.setDisable(false);
						fireEvent(new LibReserveEvent(LibReserveEvent.UPDATE_LOG));
						this.close();
					} catch (LibReserveException err) {
						submitBtn.setDisable(false);
						err.alert();
					}

				}

				@Override
				public void onclose() {
					submitBtn.setDisable(false);
				}
			};
		});
		if (Store.isLogin() && Table.isValidSeat((long) log.startTime, (long) log.endTime, log.position)
				&& log.startTime != log.endTime) {
			submitBtn.setDisable(false);
		} else {
			submitBtn.setDisable(true);
			submitBtn.getStyleClass().add("is-disabled");
		}

		VBox btns = new VBox();
		btns.setMaxHeight(100);
		btns.setAlignment(Pos.CENTER);
		btns.setSpacing(5);
		btns.getChildren().addAll(submitBtn, submitLbl);
		this.getChildren().addAll(btns, vb);
	}

	@Override
	public void intervalUpdate(long s, long t) {
		this.s = (int) s;
		this.t = (int) t;
		initialize();
	}

	public GridPane getTable() {
		Log log = getLog();
		GridPane res = new GridPane();
		res.getStyleClass().add("reserve-table");
		res.setHgap(5);
		res.setVgap(10);
		res.add(new Label("Username"), 1, 1);
		res.add(new Label("Position"), 1, 2);
		res.add(new Label("StartTime"), 1, 3);
		res.add(new Label("EndTime"), 1, 4);
		res.add(new Label(log.getUser()), 2, 1);
		res.add(new Label(log.getPosition()), 2, 2);
		res.add(new Label(log.getStartTime()), 2, 3);
		res.add(new Label(log.getEndTime()), 2, 4);
		return res;
	}

	public void setSeat(String position) {
		this.seat = position;
	}

}
