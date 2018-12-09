package history;

import org.json.JSONObject;

import application.TimeIntervalUpdate;
import database.Database;
import event.LibReserveEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/*
 * History 
 * 		- HistoryLog  	= showing record list from server
 * 		- RightDetail	= showing record information & action
 * 		- ReservePane	= reserving button
 */
public class History extends GridPane implements TimeIntervalUpdate {

	String position;
	boolean userlog;
	
	protected Log currentLog;
	protected Records recordsPane;
	protected RightDetail rightDetail;
	protected Button navigateBack;
	protected ReservePane reservePane;

	public History(String position) {
		this(position, false);
	}

	public History(String position, boolean userlog) {
		
		this.position = position;
		this.userlog = userlog;
		this.recordsPane = new Records(() -> userlog ? Database.getHistory(position) : Database.getPositionRecordFULL(position));
		this.rightDetail = new RightDetail();
		this.reservePane = new ReservePane();
		reservePane.setSeat(position);
		navigateBack = new Button("Go Back");
		navigateBack.getStyleClass().add("back-btn");
		navigateBack.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			fireEvent(new LibReserveEvent(LibReserveEvent.NAVIGATE_BACK));
		});
		rightDetail.addEventHandler(LibReserveEvent.DELETE_LOG, e -> {
			recordsPane.remove((Log) e.getParam());
			fireEvent(e);
		});
		recordsPane.addEventHandler(LibReserveEvent.FOCUS_LOG, e -> {
			rightDetail.focus((Log) e.getParam());
			fireEvent(e);
		});
		reservePane.addEventHandler(LibReserveEvent.UPDATE_LOG, e -> {
			initialize();
		});


		this.add(recordsPane, 1, 1, 1, 2);
		this.add(rightDetail, 2, 1, 1, 1);
//		this.add(navigateBack, 3, 1, 1, 1);
		this.add(reservePane, 2, 2, 2, 1);
		initialize();
	}
	
	public void initialize() {
		reservePane.initialize();
		recordsPane.initialze();
	}

	@Override
	public void intervalUpdate(long s, long t) {
		reservePane.intervalUpdate(s, t);
	}
}