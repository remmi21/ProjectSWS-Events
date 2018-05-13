package event;

import java.util.*;

public class VenueService {

    public static Map<Integer, Venue> venues = new HashMap<>();

    public static Venue add(Integer id, String name,Location loc) {
        EventService eventService = new EventService();
        Event e= eventService.events.get(id);
        Venue newVenue = new Venue(id, name, loc);
        eventService.venueList.put(id,newVenue);
        List<Venue> list=e.getVenueList();
        list.add(newVenue);
        e.setVenueList(list);;
        eventService.events.replace(e.getId(),e);
        return newVenue;
    }

    public static Venue findById(Integer id) {
        EventService eventService = new EventService();
        Venue searchedVenue;
        if((searchedVenue=eventService.venueList.get(id))!=null) {
            return searchedVenue;
        }
        return null;
    }

    public List findAll() {
        EventService eventService = new EventService();

        return new ArrayList<>(eventService.venueList.values());
    }

}
