package history;

import database.Database;
import database.Store;
import event.LibReserveEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/*
 * RightDetail = Right panel pane 
 * 
 * 		- show information of selected item
 * 		- cancel reverse on selected item
 * 
 * 		Action
 * 			- /remove => delete selected record
 * 
 * 		FireEvent
 * 			- LibReserveEvent.UPDATE_LOG
 */
class RightDetail extends VBox {
	private Button cancelReserve;
	private Log log;
	
	RightDetail() {
		super();
		this.setSpacing(10);
		this.setAlignment(Pos.CENTER);
		this.getStyleClass().addAll("RightDetail");
		set();
	}
	RightDetail(Log log) {
		this();
		set(log);
	}
	
	public void set() {
		this.log = null;
		this.cancelReserve = null;
		this.getChildren().clear();
		this.getChildren().add(new Label("No Data to Display"));
	}
	
	public void set(Log log) {
		this.log = log;
		this.getChildren().clear();
		
		this.getChildren().add(new Label(log.getUser() + " (" + log.getPosition() + ")"));
		this.getChildren().add(new Label(log.getStartTime() + " - " + log.getEndTime()));

		final HBox btns = new HBox(5);
		
		cancelReserve = new Button("Cancel Reserve");
		btns.getChildren().add(cancelReserve);
		cancelReserve.getStyleClass().add("button");
		cancelReserve.getStyleClass().add("cancelReserve-btn");
		if (allowcancel()) {
			cancelReserve.setOnAction(e -> {
				Database.remove(Store.getUsername(), log.startTime, log.endTime, log.position);
				fireEvent(new LibReserveEvent(LibReserveEvent.DELETE_LOG, log));
				set();
			});
		} else {
			cancelReserve.setDisable(true);
			cancelReserve.getStyleClass().add("is-disabled");
		}

		btns.setAlignment(Pos.CENTER);
		this.getChildren().add(btns);
	}
	
	private boolean allowcancel() {
		return Store.isLogin() && log.getUser().equals(Store.getUsername());
	}
	public void focus(Log param) {
		set(param);
	}
};

