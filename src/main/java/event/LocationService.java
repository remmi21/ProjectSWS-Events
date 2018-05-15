package event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocationService {

    public static Location add(String address, String city, String state, String country, String zipcode) {
        EventService eventService = new EventService();

        Location newLoc = new Location(address,city,state,country,zipcode);
        eventService.locationList.put(eventService.loc_count,newLoc);
        eventService.loc_count=eventService.loc_count+1;
        return newLoc;
    }

    public static Location addTo(Integer vid,String address, String city, String state, String country, String zipcode) {
        EventService eventService = new EventService();
        Venue v = eventService.venueList.get(vid);

        if(v != null) {
            Location newLoc = new Location(address, city, state, country, zipcode);
            v.setLocation(newLoc);
            eventService.venueList.replace(v.getId(), v);
            eventService.locationList.put(eventService.loc_count, newLoc);
            eventService.loc_count = eventService.loc_count + 1;
            return newLoc;
        } else {
            return null;
        }
    }

    public static ArrayList<Event> findByZip(String zip) {
        EventService eventService = new EventService();
        ArrayList<Event> resultingEvents = new ArrayList<>();

        Map<Integer, Venue> venues = eventService.venueList;

        Map<Integer, Event> eventList = eventService.events;

        for (Venue v : venues.values()){
            if(v.getLocation().getZipcode().equals(zip)) {
                for(Event e : eventList.values()) {
                    if(e.getVenueList().contains(v)) {
                        resultingEvents.add(e);
                    }
                }
            }
        }
        return resultingEvents;
    }
    public static Venue remove(Integer vid, Integer lid, String zipCode) {
        EventService eventService = new EventService();
        Venue venue = eventService.venueList.get(vid);

        if(venue != null) {
            Location location = venue.getLocation();

            if(location.getZipcode().equals(zipCode)) {
                venue.setLocation(null);
                eventService.locationList.remove(lid);

                return venue;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List findAll() {
        EventService eventService = new EventService();

        return new ArrayList<>(eventService.locationList.values());
    }

}
