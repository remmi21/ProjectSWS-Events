package event;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType("http://schema.org/PriceSpecification")
public class Price {
    @JsonldProperty("http://schema.org/name")
    private String name;
    @JsonldProperty("http://schema.org/price")
    private int price;

    public Price(String name, Integer price) {
        this.price = price;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
