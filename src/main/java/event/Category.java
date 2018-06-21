package event;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType("schema.org/Category")
public class Category {
    @JsonldId
    private Integer id;
    @JsonldProperty("http://schema.org/name")
    private String Name;
    @JsonldProperty("http://schema.org/url")
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
