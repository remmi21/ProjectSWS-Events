package event;

import java.util.*;

public class DateEvService {

    public static void addDate(Integer EventId, Date event_start, Date event_end, Date registration_start, Date registration_end) {
        DateEv date = new DateEv(event_start, event_end, registration_start, registration_end);
        Event event = EventService.findById(EventId);

        event.setDate(date);
    }

    public static Event searchEventsByDate(Date startDate) {
        for(int i=0; i<EventService.events.size(); i++) {
            if(EventService.events.get(i).getDate().getEventStart() == startDate) {
                return EventService.events.get(i);
            }
        }

        return null;
    }
}
