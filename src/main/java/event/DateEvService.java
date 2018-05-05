package event;

import java.util.*;

public class DateEvService {

    public static Event addDate(Integer EventId, Date event_start, Date event_end, Date registration_start, Date registration_end) {
        DateEv date = new DateEv(event_start, event_end, registration_start, registration_end);
        Event event = EventService.findById(EventId);

        event.setDate(date);

        return event;
    }

    public static Event searchEventsByDate(Date startDate) {
        EventService eventService = new EventService();

        for(int i=0; i<eventService.events.size(); i++) {
            if(eventService.events.get(i).getDate().getEventStart() == startDate) {
                return eventService.events.get(i);
            }
        }

        return null;
    }
}
