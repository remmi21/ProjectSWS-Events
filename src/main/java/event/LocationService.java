package event;

import java.util.ArrayList;
import java.util.HashMap;
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
        Venue v= eventService.venueList.get(vid);
        Location newLoc = new Location(address,city,state,country,zipcode);
        v.setLocation(newLoc);;
        eventService.venueList.replace(v.getId(),v);
        eventService.locationList.put(eventService.loc_count,newLoc);
        eventService.loc_count=eventService.loc_count+1;
        return newLoc;
    }

    public static ArrayList<Location> findByZip(String zip) {
        EventService eventService = new EventService();
        ArrayList<Location> result=null;
        Map<Integer,Location> locs= new HashMap();
        locs= eventService.locationList;
        for (Location l : locs.values()){
            if(l.getZipcode().equals(zip)){
                result.add(l);
            }
        }
        return result;
    }
    public static Location remove(Integer id) {
        EventService eventService = new EventService();
        return eventService.locationList.remove(id);
    }

    public List findAll() {
        EventService eventService = new EventService();

        return new ArrayList<>(eventService.locationList.values());
    }

}
