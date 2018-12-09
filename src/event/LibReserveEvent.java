package event;

import javafx.event.Event;
import javafx.event.EventType;

/*
 * LibReserveEvent = EventType to communicate between component
 * 
 */
public class LibReserveEvent extends Event {

	public static final long serialVersionUID = 1L;
	public static final EventType<LibReserveEvent> CUSTOM_EVENT_TYPE = new EventType<>(Event.ANY);
	public static final EventType<LibReserveEvent> UPDATE_LOG = new EventType<>(CUSTOM_EVENT_TYPE, "UpdateLog");
	public static final EventType<LibReserveEvent> DELETE_LOG = new EventType<>(UPDATE_LOG, "DeleteLog");
	public static final EventType<LibReserveEvent> FOCUS_LOG = new EventType<>(CUSTOM_EVENT_TYPE, "FocusLog");
	public static final EventType<LibReserveEvent> INPUT_CHANGE = new EventType<>(CUSTOM_EVENT_TYPE, "InputChange");
	public static final EventType<LibReserveEvent> SELECTED = new EventType<>(CUSTOM_EVENT_TYPE, "Selected");
	public static final EventType<LibReserveEvent> UPDATE_ROUTE = new EventType<>(CUSTOM_EVENT_TYPE, "updateRoute");
	public static final EventType<LibReserveEvent> NAVIGATE_BACK = new EventType<>(CUSTOM_EVENT_TYPE, "navigateBack");

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

	public Object getParam() {
		return param;
	}
}
