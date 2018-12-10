package application;

import database.Config;
import database.Store;
import event.LibReserveEvent;
import history.History;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
/*
 * LoginPane
 * 		handle
 * 			- login  request (Store.login)
 * 			- logout request (Store.logout)
 */

public class LoginPane extends GridPane {
	
	static public TextField GlobalLatestUserTextField;

	private String username, password;
	private TextField userTextField;
	private PasswordField pwBox;

	public LoginPane() {
		this.getStyleClass().add("LoginPane");
		this.setAlignment(Pos.TOP_CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		this.setPrefWidth(300);
		initilize();
		if (Config.AUTO_LOGIN) {
			userTextField.setText("1234567890");
			pwBox.setText("1234567890");
			tryLogin();
		}
	}

	private void initilize() {

		this.getChildren().clear();

		Label scenetitle = new Label("Please Login First.");
		this.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		this.add(userName, 0, 1);

		userTextField = new TextField();
		userTextField.setPromptText("Enter Your Student ID");
		this.add(userTextField, 1, 1);
		GlobalLatestUserTextField = userTextField;

		Label pw = new Label("Password:");
		this.add(pw, 0, 2);

		pwBox = new PasswordField();
		pwBox.setPromptText("8 - 16 characters");
		this.add(pwBox, 1, 2);
		
		userTextField.getStyleClass().add("input");
		pwBox.getStyleClass().add("input");

		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		Button signinBtn = new Button("Sign in");
		Button clearBtn = new Button("Clear");
		
		signinBtn.getStyleClass().add("button");
		hbBtn.getStyleClass().add("button");
		
		hbBtn.getChildren().addAll(signinBtn, clearBtn);
		this.add(hbBtn, 1, 4);

		scenetitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");

		pwBox.setOnKeyPressed(ke -> {
			if (ke.getCode() == KeyCode.ENTER) {
				tryLogin();
			}
		});
		signinBtn.addEventHandler(ActionEvent.ANY, e -> {
			tryLogin();
		});
		clearBtn.addEventHandler(ActionEvent.ANY, e -> {
			userTextField.setText("");
			pwBox.setText("");
		});

	}

	private boolean tryLogin() {
		userTextField.setDisable(true);
		pwBox.setDisable(true);
		username = userTextField.getText();
		password = pwBox.getText();

		if (Store.login(username, password)) {
			username = Store.getUsername();
			if (!Config.AUTO_LOGIN) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Login Successful!");
				alert.setHeaderText("Login Successful!");
				alert.setContentText("Welcome, " + username + ".");
				alert.showAndWait();
			}
			loginHandle();
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Wrong Username or Password!");
			alert.setHeaderText("Wrong Username or Password!");
			alert.setContentText("Please try again.");
			alert.showAndWait();
			userTextField.setDisable(false);
			pwBox.setDisable(false);
			return false;
		}
	}

	private void loginHandle() {
		this.getChildren().clear();
		Label success = new Label("Login Successful!");
		this.add(success, 0, 0, 2, 1);
		success.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");
		success.setPrefWidth(300);
		
		Label wellcome = new Label("Welcome, " + username + ".");
		wellcome.setPadding(new Insets(10));
		this.add(wellcome, 0, 1, 2, 1);
		
		
		
		HBox bottonPane = new HBox(10);
		Button signoutBtn = new Button("Sign out");
		signoutBtn.getStyleClass().addAll("button", "is-space");
		Button historyBtn = new Button("History");
		historyBtn.getStyleClass().addAll("button", "is-space");
		
		bottonPane.getChildren().addAll(historyBtn, signoutBtn);
		this.add(bottonPane, 1, 2);
		bottonPane.setAlignment(Pos.BOTTOM_RIGHT);
		historyBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			this.fireEvent(new LibReserveEvent(LibReserveEvent.UPDATE_ROUTE, "/root/history"));
		});
		signoutBtn.addEventHandler(ActionEvent.ANY, e -> {
			Store.logout();
			initilize();
			if (History.latestHistory != null)
				Event.fireEvent(History.latestHistory, new LibReserveEvent(LibReserveEvent.LOGOUT));
		});
		if (History.latestHistory != null)
			Event.fireEvent(History.latestHistory, new LibReserveEvent(LibReserveEvent.LOGIN));
	}
}
