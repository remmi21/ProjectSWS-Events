package event;

import java.util.ArrayList;
import java.util.Date;

public class DateEvService {

    public static void addDate(Integer eventId, Date eventStart, Date eventEnd, Date registrationStart, Date registrationEnd) {
        DateEv date = new DateEv(eventStart, eventEnd, registrationStart, registrationEnd);
        Event event = EventService.findById(eventId);

        event.setDate(date);
    }

    public static ArrayList<Event> searchEventsByDate(Date startDate) {
        ArrayList<Event>e= new ArrayList<>(EventService.events.values());
        ArrayList<Event>result=new ArrayList<>();
        for(int i=0; i<e.size(); i++) {
            if(e.get(i).getDate().getEventStart().compareTo(startDate)==0) {
                result.add( e.get(i));
            }
        }
        if(result.size()>0){
            return result;
        }
        return null;
    }

    public static Event removeDate(Integer eventId) {
        EventService eventService = new EventService();
        Event event = eventService.events.get(eventId);

        DateEv resetDate = new DateEv(null, null, null, null);
        event.setDate(resetDate);

        eventService.events.replace(event.getId(),event);

        return event;
    }
}
