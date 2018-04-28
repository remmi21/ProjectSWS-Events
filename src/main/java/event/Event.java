package event;

import java.util.Date;
import java.util.List;

public class Event {
    public Integer id;
    public String name;
    public String description;
    public String status;
    public Integer limit;
    public Integer tickets_left;
    public List<Venue> venueList;
    public Date date;
    public List<Category> categories;
    public Integer price;

    public Event(Integer id, String name, String description, String status, Integer limit, Integer tickets_left, List<Venue> venueList, Date date, /*List<Category> categories,*/ Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.limit = limit;
        this.tickets_left = tickets_left;
        this.venueList = venueList;
        this.date = date;
      //  this.categories = categories;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTickets_left() {
        return tickets_left;
    }

    public void setTickets_left(Integer tickets_left) {
        this.tickets_left = tickets_left;
    }

    public List<Venue> getVenueList() { return venueList; }

    public void setVenueList(List<Venue> venueList) { this.venueList = venueList; }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
