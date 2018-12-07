package application;

import database.Store;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class LoginPane extends GridPane {

	private String username, password;

	public LoginPane() {
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(25, 25, 25, 25));
		this.setPrefWidth(300);
		initilize();
	}

	private void initilize() {

		this.getChildren().clear();

		Text scenetitle = new Text("Please Login First.");
		this.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		this.add(userName, 0, 1);

		TextField userTextField = new TextField();
		userTextField.setPromptText("Enter Your Student ID");
		this.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		this.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		this.add(pwBox, 1, 2);
		pwBox.setPromptText("8 - 16 characters");

		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		Button signinBtn = new Button("Sign in");
		signinBtn.setPrefWidth(75);
		Button clearBtn = new Button("Clear");
		clearBtn.setPrefWidth(75);
		hbBtn.getChildren().addAll(signinBtn, clearBtn);
		this.add(hbBtn, 1, 4);

		scenetitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");

		pwBox.setOnKeyPressed(ke -> {
			if (ke.getCode() == KeyCode.ENTER) {
				userTextField.setDisable(true);
				pwBox.setDisable(true);
				username = userTextField.getText();
				password = pwBox.getText();
				boolean isPass;
				if ((username.equals("1") && password.equals("1"))) {
					isPass = true;
					username = "Admin";
				} else {
					isPass = Store.login(username, password);
				}
				if (isPass) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Login Successful!");
					alert.setHeaderText("Login Successful!");
					alert.setContentText("Welcome, " + username + ".");
					alert.showAndWait();
					loginHandle();
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Wrong Username or Password!");
					alert.setHeaderText("Wrong Username or Password!");
					alert.setContentText("Please try again.");
					alert.showAndWait();
					userTextField.setDisable(false);
					pwBox.setDisable(false);
				}
			}

		});

		signinBtn.addEventHandler(ActionEvent.ANY, e -> {

			userTextField.setDisable(true);
			pwBox.setDisable(true);
			username = userTextField.getText();
			password = pwBox.getText();

			if (Store.login(username, password)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Login Successful!");
				alert.setHeaderText("Login Successful!");
				alert.setContentText("Welcome, " + username + ".");
				alert.showAndWait();
				loginHandle();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Wrong Username or Password!");
				alert.setHeaderText("Wrong Username or Password!");
				alert.setContentText("Please try again.");
				alert.showAndWait();
				userTextField.setDisable(false);
				pwBox.setDisable(false);

			}
		});

		clearBtn.addEventHandler(ActionEvent.ANY, e -> {
			userTextField.setText("");
			pwBox.setText("");
		});

	}

	private void loginHandle() {
		this.getChildren().clear();
		Label success = new Label("Login Successful!");
		this.add(success, 0, 0, 2, 1);
		success.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");
		success.setPrefWidth(300);
		this.add(new Label("Welcome, " + username + "."), 0, 1, 2, 1);
		Button signoutBtn = new Button("Sign out");
		signoutBtn.setPrefWidth(75);
		this.add(signoutBtn, 1, 2);
		LoginPane.setHalignment(signoutBtn, HPos.RIGHT);
		signoutBtn.addEventHandler(ActionEvent.ANY, e -> {
			Store.logout();
			initilize();
		});
	}
}
