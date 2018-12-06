package history;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

class HistoryLog extends HBox {
	public HistoryLog(final Log log) {
		super();
		final Label nm = new Label(log.getUser());
		final Label tm = new Label(log.getReserveTime());
		final Region rg = new Region();
		HBox.setHgrow(rg, Priority.ALWAYS);
		this.getChildren().addAll(nm, rg, tm);
	}
}