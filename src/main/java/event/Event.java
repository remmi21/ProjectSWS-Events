package event;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

@JsonldType("http://schema.org/Event")
public class Event {
    @JsonldId
    private Integer id;
    @JsonldProperty("http://schema.org/name")
    private String name;
    @JsonldProperty("http://schema.org/description")
    private String description;
    @JsonldProperty("http://schema.org/eventStatus")
    private String status;
    @JsonldProperty("http://schema.org/maximumAttendeeCapacity")
    private Integer limit;
    @JsonldProperty("http://schema.org/remainingAttendeeCapacity")
    private Integer tickets_left;
    @JsonldProperty("http://schema.org/organizer")
    private List<Venue> venueList;
    //@JsonSerialize(using = CostumDateSerializer.class)
    @JsonldProperty("http://schema.org/startDate")
    private DateEv date;
    @JsonldProperty("schema.org/category")
    private List<Category> categories;
    private List<Price> price;
    private Properties prop;        //no matching in Schema.org
    @JsonldProperty("http://schema.org/url")
    private String uri;

    public Event(Integer id,String uri, String name, String description, String status, Integer limit, Integer tickets_left,
                 List<Venue> venueList, DateEv date, List<Category> categories, List<Price> price, Properties prop) {
        this.id = id;
        this.uri=uri;
        this.name = name;
        this.description = description;
        this.status = status;
        this.limit = limit;
        this.tickets_left = tickets_left;
        this.venueList = venueList;
        this.date = date;
        this.categories = categories;
        this.price = price;
        this.prop=prop;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrice(List<Price> price) {
        this.price = price;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public DateEv getDate() {
        return date;
    }

    public void setDate(DateEv date) {
        this.date = date;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Price> getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price.add(price);
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }
}
