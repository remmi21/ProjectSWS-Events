package event;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType("http://schema.org/PriceSpecification")
public class Price {
    @JsonldProperty("http://schema.org/name")
    private String name;
    @JsonldProperty("http://schema.org/price")
    private Integer price;

    public Price(String name, Integer price) {
        this.name=name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
