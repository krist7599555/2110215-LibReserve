package history;

import database.Store;
import event.LibReserveEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class RightDetail extends VBox {
	private Button cancelReserve;
	private Log log;
	
	RightDetail() {
		this(Log.NONE());
		this.getChildren().add(new Label("No Data to Display"));
		
	}
	RightDetail(Log log) {
		this.log = log;

		this.setAlignment(Pos.CENTER);
		this.setPrefWidth(500);
		this.setMinHeight(500);
		this.getStyleClass().addAll("zone-region");
		this.getChildren().add(new Label(log.getUser() + " (" + log.getPosition() + ")"));
		this.getChildren().add(new Label(log.getStartTime() + "-" + log.getEndTime().substring(log.getEndTime().length() - 5)));
		this.getChildren().add(new Label("reserve at " + log.getReserveTime()));

		final HBox btns = new HBox(5);
		if (allowcancel()) {
			cancelReserve = new Button("cancel reserve");
			cancelReserve.setOnAction(e -> {
				this.fireEvent(new LibReserveEvent(LibReserveEvent.DELETE_LOG, this));
			});
			btns.getChildren().add(cancelReserve);
		}

		btns.setAlignment(Pos.CENTER);
		this.getChildren().add(btns);
		
	}
	private boolean allowcancel() {
		System.out.println(Store.isLogin());
		System.out.println(log.getUser() + " " + Store.getUsername());
		return Store.isLogin() && log.getUser().equals(Store.getUsername());
	}
};