package exception;

import application.LoginPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public abstract class LibReserveException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;
	public LibReserveException() {
		this("LibReserve Exception is occer");
	}
	public LibReserveException(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	@Override
	public String toString() {
		return getMessage();
	}
	
	public void alert() {
		System.err.println(this);
		Alert alrt = new Alert(AlertType.ERROR, tinyMessage(), ButtonType.CLOSE);
		alrt.setTitle("Error");
		alrt.setHeaderText("Cannot Reserve");
		alrt.show();
		LoginPane.GlobalLatestUserTextField.requestFocus();
	}
	
	abstract public String tinyMessage();
	
}
