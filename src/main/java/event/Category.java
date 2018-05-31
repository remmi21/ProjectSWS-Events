package event;

public class Category {
    private Integer id;
    private String Name;
    private  String uri;

    public Category() {

    }

    public Category(Integer id,String uri, String name) {
        this.id = id;
        this.uri= uri;
        Name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
