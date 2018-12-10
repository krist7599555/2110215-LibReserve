package exception;

public class NotLoginException extends LibReserveException {
	private static final long serialVersionUID = 1L;
	public NotLoginException() {
		super("NotLoginException is occur. Please login to process.");
	}
	public NotLoginException(String message) {
		super(message);
	}
	@Override
	public String tinyMessage() {
		return "You are not login.";
	}
}
