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
		set();
	}
	RightDetail(Log log) {
		set(log);
		
	}
	
	public void set() {
		this.log = null;
		this.cancelReserve = null;
		this.setSpacing(10);
		this.setPrefWidth(150);
		this.setAlignment(Pos.CENTER);
		this.getChildren().clear();
		this.getChildren().add(new Label("No Data to Display"));
	}
	
	public void set(Log log) {
		this.log = log;
		this.getChildren().clear();
		this.setAlignment(Pos.CENTER);
		this.setPrefWidth(150);
		this.setSpacing(10);
		this.getStyleClass().addAll("zone-region");
		this.getChildren().add(new Label(log.getUser() + " (" + log.getPosition() + ")"));
		this.getChildren().add(new Label(log.getStartTime() + "-" + log.getEndTime()));
		this.getChildren().add(new Label("reserve at " + log.getReserveTime()));

		final HBox btns = new HBox(5);
		if (allowcancel()) {
			cancelReserve = new Button("Cancel Reserve");
			cancelReserve.getStyleClass().add("cancelReserve-btn");
			cancelReserve.setOnAction(e -> {
				Database.remove(Store.getUsername(), log.startTime, log.endTime, log.position);
				fireEvent(new LibReserveEvent(LibReserveEvent.UPDATE_LOG, log));
				set();
			});
			btns.getChildren().add(cancelReserve);
		}

		btns.setAlignment(Pos.CENTER);
		this.getChildren().add(btns);
	}
	
	private boolean allowcancel() {
		return Store.isLogin() && log.getUser().equals(Store.getUsername());
	}
};

