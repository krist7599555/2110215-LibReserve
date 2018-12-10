package exception;

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
	abstract public String tinyMessage();
	
}
