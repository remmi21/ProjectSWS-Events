package event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType("http://schema.org/Offer")
public class Offer {
    @JsonSerialize(using = CustomPriceSerializer.class)
    @JsonldProperty("http://schema.org/priceSpecification")
    private Price priceSpecification;

    public Offer(Price price) {
        this.priceSpecification = price;
    }

    public Price getEventPrice() {
        return priceSpecification;
    }

    public void setEventPrice(Price priceSpecification) {
        this.priceSpecification = priceSpecification;
    }
}
