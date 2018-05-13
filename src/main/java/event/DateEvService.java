package event;

import java.util.ArrayList;
import java.util.Date;

public class DateEvService {

    public static void addDate(Integer EventId, Date event_start, Date event_end, Date registration_start, Date registration_end) {
        DateEv date = new DateEv(event_start, event_end, registration_start, registration_end);
        Event event = EventService.findById(EventId);

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
}
