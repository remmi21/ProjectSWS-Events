package event;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType("http://schema.org/PostalAddress")
public class Location {
    @JsonldProperty("http://schema.org/streetAddress")
    private String address;
    @JsonldProperty("http://schema.org/addressLocality")
    private String city;
    @JsonldProperty("http://schema.org/addressRegion")
    private String state;
    @JsonldProperty("http://schema.org/addressRegion")
    private String country;
    @JsonldProperty("http://schema.org/postalCode")
    private String zipcode;

    public Location(String address, String city, String state, String country, String zipcode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
