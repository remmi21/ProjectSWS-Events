package event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

// TODO: class not used yet; think we need offer class in order to reference offers -> Offer -> priceSpecification -> PriceSpecification
@JsonldType("http://schema.org/Offer")
public class Offer {
    @JsonSerialize(using = CustomPriceSerializer.class)
    @JsonldProperty("http://schema.org/priceSpecification")
    private Price eventPrice;

    public Offer(Price price) {
        this.eventPrice = price;
    }

    public Price getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(Price eventPrice) {
        this.eventPrice = eventPrice;
    }
}
