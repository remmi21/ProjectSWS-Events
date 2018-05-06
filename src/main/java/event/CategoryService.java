package event;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {

    public static Category add(Integer id, String name) {
        EventService eventService = new EventService();

        Category newCat = new Category(id, name);
        eventService.catList.put(id,newCat);

        return newCat;
    }

    public static Category addTo(Integer eid,Integer id, String name) {
        EventService eventService = new EventService();
        Event e= eventService.events.get(eid);
        Category newCat = new Category(id, name);
        List<Category> cate= e.getCategories();
        cate.add(newCat);
        e.setCategories(cate);
        eventService.events.replace(e.getId(),e);
        eventService.catList.put(id,newCat);

        return newCat;
    }

    public static Category findById(Integer id) {
        EventService eventService = new EventService();
        return eventService.catList.get(id);
    }
    public static Category remove(Integer id) {
        EventService eventService = new EventService();
        return eventService.catList.remove(id);
    }

    public List findAll() {
        EventService eventService = new EventService();

        return new ArrayList<>(eventService.catList.values());
    }


}
