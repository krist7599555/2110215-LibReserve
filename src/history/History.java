package history;

import org.json.JSONObject;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/*
 * History 
 * 		- HistoryLog  	= showing record list from server
 * 		- RightDetail	= showing record information & action
 */
public class History extends HBox {

	String position;
	boolean userlog;
	
	protected Log currentLog;
	protected RightDetail rightDetail;
	protected Button navigateBack;
	
	private final ObservableList<HistoryLog> labelList = FXCollections.observableArrayList();

	public History(String position) {
		this(position, false);
	}

	public History(String position, boolean userlog) {
		this.position = position;
		this.userlog = userlog;
		initialize();
	}
	
	public void initialize() {
		
		labelList.clear();
		this.getChildren().clear();
		
		final VBox root = new VBox();
		root.setPadding(new Insets(10));
		root.setAlignment(Pos.CENTER);
		root.setSpacing(5);
		root.getChildren().addAll(new ListView<>(labelList));

		for (JSONObject jo : userlog ? Database.getHistory(position) : Database.getPositionRecordFULL(position)) {
			Log log = new Log(jo);
			HistoryLog nameLabel = new HistoryLog(log);

			nameLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				if (e.getButton().equals(MouseButton.PRIMARY)) {
					rightDetail.set(currentLog = log);
				}
			});
			labelList.add(nameLabel);
		}
		this.setAlignment(Pos.CENTER);
		this.setSpacing(10);
		this.getChildren().addAll(new HBox(root, rightDetail = new RightDetail()), navigateBack = new Button("Go Back"));
		
		rightDetail.addEventHandler(LibReserveEvent.UPDATE_LOG, e -> {
			Log log = (Log) e.getParam();
			for (int i = 0; i < labelList.size(); ++i) {
				if (labelList.get(i).getLog().equals(log)) {
					labelList.remove(i);
					break;
				}
			}
			fireEvent(e);
		});
		
		navigateBack.getStyleClass().add("back-btn");
		navigateBack.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			fireEvent(new LibReserveEvent(LibReserveEvent.NAVIGATE_BACK));
		});
	}

}

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
