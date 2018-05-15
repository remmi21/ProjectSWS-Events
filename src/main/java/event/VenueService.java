package event;

import java.util.*;

public class VenueService {;

    public static Venue add(Integer eventId, Integer venueId, String name,Location loc) {
        EventService eventService = new EventService();
        Event e = eventService.events.get(eventId);

        if(e != null) {
            Venue newVenue = new Venue(venueId, name, loc);
            eventService.venueList.put(venueId, newVenue);

            List<Venue> list = e.getVenueList();
            list.add(newVenue);
            e.setVenueList(list);

            eventService.events.replace(e.getId(), e);

            return newVenue;
        } else {
            return null;
        }
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

    public Event remove(Integer eventId, Integer venueId) {
        EventService eventService = new EventService();
        Event event = eventService.findById(eventId);

        if(event != null) {
            List <Venue> venueList = event.getVenueList();

            for(Iterator<Venue> vit = venueList.iterator(); vit.hasNext();) { // for(Venue v : venueList)
                Venue v = vit.next();
                if(v.getId().equals(venueId)) {
                    vit.remove();
                    event.setVenueList(venueList);
                }
            }
        }

        return event;
    }

}
