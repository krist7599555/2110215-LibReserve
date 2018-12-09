package history;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/*
 * HistoryLog = record list selector
 */
class HistoryLog extends HBox {
	Log log;
	public HistoryLog(final Log log) {
		super();
		this.log = log;
		final Label nm = new Label(log.getUser());
		final Label tm1 = new Label(log.getPosition() + "    " + log.getStartTime());
		final Label tm2 = new Label(log.getEndTime());
		final VBox vb = new VBox(tm1, tm2);
		vb.setAlignment(Pos.CENTER_RIGHT);
		this.setAlignment(Pos.CENTER);
		final Region rg = new Region();
		HBox.setHgrow(rg, Priority.ALWAYS);
		this.getChildren().addAll(nm, rg, vb);
	}
	Log getLog() {
		return log;
	}
}
