package event;

import java.util.Date;
import java.util.List;

public class Event {
    public Integer id;
    public String name;
    public String describtion;
    public String status;
    public Integer limit;
    public Integer tickets_left;
    public Venue venue;
    public Date date;
    public List<Category> categories;

}
