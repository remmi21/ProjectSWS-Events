package event;

import java.util.ArrayList;
import java.util.List;

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
            ArrayList<Event>ev= new ArrayList<>(EventService.events.values());
            List<Event> resultEvents=new ArrayList<>();
            Properties p;
            for(int i=0; i<ev.size(); i++) {
                p=ev.get(i).getProp();
                if(p.isGroup_registrations_allowed()==group_registrations_allowed && p.getGroupe_size()==groupe_size&&
                        p.isActive()==active && p.isMembers_only()==members_only){
                        resultEvents.add(ev.get(i));
                }
            }
            return resultEvents;
    }

}
