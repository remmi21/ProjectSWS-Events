package event;

public class Venue {
    private Integer id;
    private String uri;
    private String name;
    private Location location;

    public Venue(Integer id, String uri, String name, Location location) {
        this.id = id;
        this.uri = uri;
        this.name = name;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUri() { return uri; }

    public void setUri(String uri) { this.uri = uri; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
