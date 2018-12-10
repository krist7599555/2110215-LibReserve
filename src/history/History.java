package history;

import application.TimeIntervalUpdate;
import database.Database;
import event.LibReserveEvent;
import javafx.scene.layout.GridPane;

/*
 * History 
 * 		- HistoryLog  	= showing record list from server
 * 		- RightDetail	= showing record information & action
 * 		- ReservePane	= reserving button
 */
public class History extends GridPane implements TimeIntervalUpdate {

	public static History latestHistory;
	
	String position;
	String username;
	boolean userlog;
	
	protected Log currentLog;
	protected Records recordsPane;
	protected RightDetail rightDetail;
	protected ReservePane reservePane;

	public History(String str) {
		this(str, false);
	}

	public History(String str, boolean userlog) {
		
		if (userlog) {
			this.username = str;
			this.position = "-";
		} else {
			this.username = "-";
			this.position = str;
		}
		
		this.setHgap(20);
		this.setVgap(20);
		
		this.userlog = userlog;
		this.recordsPane = new Records(() -> userlog ? Database.getHistory(username) : Database.getPositionRecordFULL(position));
		this.rightDetail = new RightDetail();
		this.reservePane = new ReservePane();
		
		reservePane.setSeat(position);
		
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
		this.add(rightDetail, 2, 1, 2, 1);
		this.add(reservePane, 2, 2, 2, 1);
		initialize();
		
		latestHistory = this;
		this.addEventHandler(LibReserveEvent.LOGIN, e -> initialize());
		this.addEventHandler(LibReserveEvent.LOGOUT, e -> initialize());
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