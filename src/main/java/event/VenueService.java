package event;

import java.util.*;

public class VenueService {

    public static Map<Integer, Venue> venues = new HashMap<>();

    public static Venue add(Integer id, String name, String address, String city, String state, String country, String zipcode) {
        EventService eventService = new EventService();

        Venue newVenue = new Venue(id, name, address, city, state, country, zipcode);
        eventService.venueList.add(newVenue);

        return newVenue;
    }

    public static Venue findById(Integer id) {
        EventService eventService = new EventService();

        for(int i=0; i<eventService.venueList.size(); i++) {
            Venue searchedVenue = eventService.venueList.get(i);
            if (searchedVenue.getId() == id) {
                return searchedVenue;
            }
        }

        return null;
    }

    public List<Venue> findAll() {
        EventService eventService = new EventService();

        return eventService.venueList;
    }

}
