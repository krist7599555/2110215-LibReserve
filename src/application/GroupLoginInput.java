package application;

import java.util.ArrayList;
import java.util.HashSet;

import database.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/*
 * groupLoginInput = Popup Map View
 */
public abstract class GroupLoginInput {
	private Stage stage = new Stage();
	private Label message = new Label();
	private Button submit = new Button("submit");
	private VBox middlePane = new VBox();

	private ObservableList<TextField> listInput = FXCollections.observableArrayList();

	private int require;
	private String position;

	public GroupLoginInput(int n, String pos) {
		require = n;
		position = pos;
		show();
		message.getStyleClass().add("help");
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				onclose();
			}
		});
	}

	// public GroupLoginInput() {
	// // TODO Auto-generated constructor stub
	// }

	public GroupLoginInput() {
		System.err.println("[Error] Empty Constructure in GroupLoginInput.java");
	}

	protected void show() {
		GridPane pane = new GridPane();
		pane.getStyleClass().add("GroupLoginInput");

		pane.setPadding(new Insets(30, 10, 30, 10));
		pane.setAlignment(Pos.CENTER);
		pane.setVgap(10);

		listInput.clear();
		for (int i = 1; i <= require; ++i) {
			var tf = new TextField();
			tf.setPromptText("user " + i);
			tf.getStyleClass().add("input");
			tf.textProperty().addListener((observable, oldValue, newValue) -> {
				message.setText("");
			});
			listInput.add(tf);
		}
		if (Store.isLogin()) {
			var tf = listInput.get(0);
			tf.setText(Store.getUsername());
			tf.setDisable(true);
		}

		setLoginPane();

		pane.add(new Label(position + " require " + require + " people."), 1, 1);
		pane.add(message, 1, 2, 2, 1);
		pane.add(middlePane, 1, 3, 2, 1);
		pane.add(submit, 2, 4);

		Scene scene = new Scene(pane);
		scene.getStylesheets().add(this.getClass().getResourceAsStream("/application/style.css").toString());

		stage.setScene(scene);
		stage.show();

	}

	private void setLoginPane() {
		middlePane.getChildren().clear();
		middlePane.setPrefSize(70, 100);
		if (Store.isLogin()) {
			VBox inps = middlePane;
			inps.setSpacing(10);
			inps.getChildren().addAll(listInput);

			submit.getStyleClass().add("button");
			submit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
				if (isValid()) {
					onsuccess();
				} else if (message.getText().length() == 0) {
					message.setText("invalid request");
				}
			});
		} else {
			message.setText("please login");
		}
	}

	public void refresh() {
		if (message.getText().length() != 0) {
			message.setText("");

		}
		if (Store.isLogin() && listInput.size() == 0) {
			setLoginPane();
		}
	}

	public boolean isValid() {
		HashSet<String> hh = new HashSet<>();
		for (var tf : listInput) {
			var txt = tf.getText();
			if (!txt.matches("[0-9]{10}")) {
				tf.setFocusTraversable(true);
				if (txt.length() == 0) {
					message.setText("empty student id");
				} else {
					message.setText("\"" + txt + "\" is not valid");
				}
				return false;
			} else {
				hh.add(txt);
			}
		}
		if (hh.size() != listInput.size()) {
			message.setText("duplicate element");
			return false;
		}
		return true;
	}

	public void close() {
		stage.close();
	}

	public ArrayList<String> getTeam() {
		ArrayList<String> res = new ArrayList<>();
		for (var tf : listInput) {
			if (!tf.getText().matches("[0-9]{10}")) {
				res.add(tf.getText());
			} else {
				System.out.println("[Error] record " + tf.getText() + " in GroupLoginInput.java is not valid.");
			}
		}
		return res;
	}

	abstract public void onsuccess();

	abstract public void onclose();
}
