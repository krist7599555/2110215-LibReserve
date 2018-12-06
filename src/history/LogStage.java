package history;

import event.LibReserveEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class LogStage extends Stage {
	
	Log log;
	Button exit;
	Button cancelReserve;

	LogStage(final Log log) {
		this(log, false);
	}

	LogStage(final Log log, final boolean allowcancel) {
		this.log = log;
		this.setTitle(log.getTitle());

		final VBox root = new VBox(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().add(new Label(log.getUser() + " (" + log.getPosition() + ")"));
		root.getChildren()
				.add(new Label(log.getStartTime() + "-" + log.getEndTime().substring(log.getEndTime().length() - 5)));
		root.getChildren().add(new Label("reserve at " + log.getReserveTime()));

		final HBox btns = new HBox(5);
		if (allowcancel) {
			btns.getChildren().add(this.cancelReserve = new Button("cancel reserve"));
			this.cancelReserve.setOnAction(e -> {
				this.fireEvent(new LibReserveEvent(LibReserveEvent.DELETE_LOG, this));
			});
		}
		btns.getChildren().add(this.exit = new Button("exit"));
		this.exit.setOnAction(e -> {
			System.out.println("exit");
			this.close();
		});

		btns.setAlignment(Pos.CENTER);
		root.getChildren().add(btns);

		final Scene scene = new Scene(root, 300, 200);
		this.setScene(scene);
		this.show();
	}
};