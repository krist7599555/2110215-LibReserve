package event;


import javafx.event.Event;
import javafx.event.EventType;

public class LibReserveEvent extends Event {
	
	Object param;
	
	public LibReserveEvent() {
		super(CUSTOM_EVENT_TYPE);
	}
	public LibReserveEvent(Object param) {
		super(CUSTOM_EVENT_TYPE);
        this.param = param;
    }
	public LibReserveEvent(EventType<? extends Event> typ, Object param) {
        super(typ);
        this.param = param;
    }

	public static final long serialVersionUID = 1L;
	public static final EventType<LibReserveEvent> CUSTOM_EVENT_TYPE = new EventType<LibReserveEvent>(Event.ANY);
	public static final EventType<LibReserveEvent> DELETE_LOG = new EventType<LibReserveEvent>(CUSTOM_EVENT_TYPE, "DeleteLog");
	
	public Object getParam() {
		return param;
	}
}
