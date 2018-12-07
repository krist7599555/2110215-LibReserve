package event;


import javafx.event.Event;
import javafx.event.EventType;

public class LibReserveEvent extends Event {
	
	Object param;
	
	public LibReserveEvent() {
		this(null);
	}
	public LibReserveEvent(EventType<? extends Event> typ) {
		this(typ, null);
    }
	public LibReserveEvent(Object param) {
		this(CUSTOM_EVENT_TYPE, param);
    }
	public LibReserveEvent(EventType<? extends Event> typ, Object param) {
        super(typ);
        this.param = param;
    }

	public static final long serialVersionUID = 1L;
	public static final EventType<LibReserveEvent> CUSTOM_EVENT_TYPE = new EventType<LibReserveEvent>(Event.ANY);
	public static final EventType<LibReserveEvent> DELETE_LOG = new EventType<LibReserveEvent>(CUSTOM_EVENT_TYPE, "DeleteLog");
	public static final EventType<LibReserveEvent> INPUT_CHANGE = new EventType<LibReserveEvent>(CUSTOM_EVENT_TYPE, "InputChange");
	
	public Object getParam() {
		return param;
	}
}
