package event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CategoryService {

    public static Category add(Integer id, String name) {
        EventService eventService = new EventService();

        Category newCat = new Category(id,"/kangarooEvents/category/"+id, name);
        eventService.catList.put(id,newCat);

        return newCat;
    }

    public static Category addTo(Integer eid,Integer id, String name) {
        EventService eventService = new EventService();
        Event e= eventService.events.get(eid);

        Category newCat = new Category(id,"/kangarooEvents/category/"+id, name);
        List<Category> cate= e.getCategories();

        cate.add(newCat);

        e.setCategories(cate);
        eventService.events.replace(e.getId(),e);

        // add to overall category list
        eventService.catList.put(id,newCat);

        return newCat;
    }

    public static Category findById(Integer id) {
        EventService eventService = new EventService();
        return eventService.catList.get(id);
    }

    public static Event remove(Integer eid, Integer cid) {
        EventService eventService = new EventService();
        Event event = eventService.events.get(eid);

        if(event != null) {
            List<Category> categoryList = event.getCategories();

            for(Iterator<Category> cit = categoryList.iterator(); cit.hasNext();) {
                Category cat = cit.next();
                if (cat.getId() == cid) {
                    cit.remove();
                    eventService.catList.remove(cid);

                    return event;
                }
            }
        }

        return null;
    }

    public List findAll() {
        EventService eventService = new EventService();

        return new ArrayList<>(eventService.catList.values());
    }


}
