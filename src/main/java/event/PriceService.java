package event;

import java.util.List;

public class PriceService {

    public static void addPrice(Integer eventId, String name, Integer priceValue) {
        Price newPrice = new Price(name, priceValue);
        Event event = EventService.findById(eventId);

        event.setPrice(newPrice);
    }

    public static List<Price> findEventPrice(Integer eventId) {
        Event event = EventService.findById(eventId);

        List<Price> eventPrices = event.getPrice();

        return eventPrices;
    }
    public static void removePrice(Integer eventId, String name) {      //set price to 0
        Price newPrice = new Price(name, 0);
        Event event = EventService.findById(eventId);

        event.setPrice(newPrice);
    }
}
