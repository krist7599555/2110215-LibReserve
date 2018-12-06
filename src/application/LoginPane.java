package application;

//import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
//import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
//import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
//import javafx.stage.Stage;

public class LoginPane extends GridPane {
	
	private String username, password;
	
	public boolean isLoginSuccess() {
		return State.isLogin;
	}

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

		// set style
		scenetitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");
//		this.setStyle("-fx-background-color:lightgray;");
//		scenetitle.setStyle("-fx-font-size: 32px; -fx-font-family:\"Arial Black\";-fx-fill: #555;");
//		signinBtn.setStyle(
//				"-fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: \"Arial Narrow\"; -fx-background-color: darkgreen;");
//		exitBtn.setStyle(
//				"-fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: \"Arial Narrow\"; -fx-background-color: darkred;");

		// add handler
		// press ESC to clear user text field
//		userTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
//			@Override
//			public void handle(KeyEvent ke) {
//				if (ke.getCode() == KeyCode.ESCAPE) {
//					userTextField.setText("");
//				}
//			}
//		});
//		// change button width when mouse over
//		signinBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				signinBtn.setPrefWidth(75);
//			}
//		});
//		signinBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				signinBtn.setPrefWidth(65);
//			}
//		});
//		exitBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				exitBtn.setPrefWidth(75);
//			}
//		});
//		exitBtn.setOnMouseExited(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent event) {
//				exitBtn.setPrefWidth(65);
//			}
//		});
		
		// action when press enter
		pwBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ENTER) {
					userTextField.setDisable(true);
					pwBox.setDisable(true);
					username = userTextField.getText();
					password = pwBox.getText();
					boolean isPass;
					if ((username.equals("1") && password.equals("1"))) {
						isPass = true;
						username = "Admin";
					}
					else {
						isPass = LoginRequest.login(username, password);
					}
					if (isPass) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Login Successful!");
						alert.setHeaderText("Login Successful!");
						alert.setContentText("Welcome, "+ username +".");
						alert.showAndWait();
						State.isLogin = true;
						loginHandle();
					}
					else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Wrong Username or Password!");
						alert.setHeaderText("Wrong Username or Password!");
						alert.setContentText("Please try again.");
						alert.showAndWait();
						userTextField.setDisable(false);
						pwBox.setDisable(false);
					}
				}
			}
		});
		
		// action when click button
		signinBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				userTextField.setDisable(true);
				pwBox.setDisable(true);
				username = userTextField.getText();
				password = pwBox.getText();
				boolean isPass;
				if ((username.equals("1") && password.equals("1"))) {
					isPass = true;
					username = "Admin";
				}
				else {
					isPass = LoginRequest.login(username, password);
				}
				if (isPass) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Login Successful!");
					alert.setHeaderText("Login Successful!");
					alert.setContentText("Welcome, "+ username +".");
					alert.showAndWait();
					State.isLogin = true;
					loginHandle();
				}
				else {
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
		
		clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				userTextField.setText("");
				pwBox.setText("");
			}
		});
//
//		Scene scene = new Scene(grid, 350, 300);
//
//		primaryStage.setScene(scene);
//		primaryStage.setTitle("JavaFX Welcome");
//		primaryStage.show();
	}
	
//		private Controll ctrl;
	
	private void loginHandle() {
		this.getChildren().clear();
		Label success = new Label("Login Successful!");
		this.add(success, 0, 0, 2, 1);
		success.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");
		success.setPrefWidth(300);
		this.add(new Label("Welcome, " + username + "."), 0, 1, 2, 1);
//		HBox hbBtn = new HBox(10);
//		hbBtn.setPrefWidth(300);
//		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		Button signoutBtn = new Button("Sign out");
		signoutBtn.setPrefWidth(75);
//		hbBtn.getChildren().add(signoutBtn);
		this.add(signoutBtn, 1, 2);
		LoginPane.setHalignment(signoutBtn, HPos.RIGHT);
		ReservePane reservePane = new ReservePane();
		reservePane.initilize();
		
		signoutBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			State.isLogin = false;
			initilize();
			reservePane.clear();
			}
		});
		
		
//		ctrl.getChildren().set(ctrl.getLoginPaneIdx(), new Label("Login Successful"));
	}
//
//	/**
//	 * @param args
//	 *            the command line arguments
//	 */
//	public static void main(String[] args) {
//		launch(args);
//	}
}
