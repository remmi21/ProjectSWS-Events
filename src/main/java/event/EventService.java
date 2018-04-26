package event;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class EventService {
    public static Map events = new HashMap();
    private static final AtomicInteger count = new AtomicInteger(0);

    public Event findById(String id) {
        return (Event) events.get(id);
    }
}
