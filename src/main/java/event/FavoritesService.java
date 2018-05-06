package event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoritesService {

    public static List<Event> getFavorites(){
        EventService eventService =new EventService();
        Map<Integer, Event> ev =eventService.events;
        List<Event> resultEvents=new ArrayList<>();
        Event e;
        for (Map.Entry<Integer, Event> entry : ev.entrySet()) {
            e=entry.getValue();
            if(e.getTickets_left()<20){
                resultEvents.add(e);
            }
        }
        return resultEvents;
    }
}
