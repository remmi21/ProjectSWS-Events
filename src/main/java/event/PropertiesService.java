package event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PropertiesService {

        public static Properties add(Integer eid,boolean group_registrations_allowed, Integer groupe_size, boolean active, boolean members_only) {
            EventService eventService = new EventService();
            Event e= eventService.events.get(eid);
            Properties newprop = new Properties(group_registrations_allowed,groupe_size,active,members_only);
            e.setProp(newprop);
            eventService.events.replace(e.getId(),e);

            return newprop;
        }
        public static Properties remove(Integer eid) {
            EventService eventService = new EventService();
            Event e= eventService.events.get(eid);
            Properties newprop = new Properties(false,0,false,false);
            e.setProp(newprop);
            eventService.events.replace(e.getId(),e);
            return newprop;
        }

    public static List<Event> searchEventbyProp(boolean group_registrations_allowed, Integer groupe_size, boolean active, boolean members_only){
            EventService eventService= new EventService();
            Map<Integer, Event> ev =eventService.events;
            List<Event> resultEvents=new ArrayList<>();
            Event e;
            Properties p;
            for (Map.Entry<Integer, Event> entry : ev.entrySet()) {
                e=entry.getValue();
                p=e.getProp();
                if(p.isGroup_registrations_allowed()==group_registrations_allowed && p.getGroupe_size()==groupe_size&&
                        p.isActive()==active && p.isMembers_only()==members_only){
                        resultEvents.add(e);
                }
            }
            return resultEvents;
    }

}
