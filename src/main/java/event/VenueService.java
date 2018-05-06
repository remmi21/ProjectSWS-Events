package event;

import java.util.*;

public class VenueService {

    public static Map<Integer, Venue> venues = new HashMap<>();

    public static Venue add(Integer id, String name, String address, String city, String state, String country, String zipcode) {
        EventService eventService = new EventService();

        Venue newVenue = new Venue(id, name, address, city, state, country, zipcode);
        eventService.venueList.put(id,newVenue);

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
