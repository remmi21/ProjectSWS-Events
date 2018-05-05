package event;

import java.util.List;

public class PricingService {

    public static void addPrice(Integer eventId, String name, Integer priceValue) {
        Pricing newPrice = new Pricing(name, priceValue);
        Event event = EventService.findById(eventId);

        event.setPrice(newPrice);
    }

    public static List<Pricing> findEventPrice(Integer eventId) {
        Event event = EventService.findById(eventId);

        List<Pricing> eventPrices = event.getPrice();

        return eventPrices;
    }
}
