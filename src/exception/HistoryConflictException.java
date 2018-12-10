package exception;

public class HistoryConflictException extends LibReserveException {
	private static final long serialVersionUID = 1L;
	public HistoryConflictException() {
		super("History Conflict is occur. One user can reserve 1 table at same time.");
	}
	public HistoryConflictException(String message) {
		super(message);
	}
	@Override
	public String tinyMessage() {
		return "Reserve time is conflict to previus record.\nPlease check your history.";
	}
}
