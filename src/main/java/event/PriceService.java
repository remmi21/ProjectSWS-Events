package event;

import java.util.List;

public class PriceService {

    public static void addPrice(Integer eventId, String name, Integer priceValue) {
        Price newPrice = new Price(name, priceValue);
        Offer newEventPrice = new Offer(newPrice);

        Event event = EventService.findById(eventId);

        event.setPrice(newEventPrice);
    }

    public static List<Offer> findEventPrice(Integer eventId) {
        Event event = EventService.findById(eventId);

        List<Offer> eventPrices = null;
        if(event != null) {
            eventPrices = event.getPrice();
        }

        return eventPrices;
    }
    public static void removePrice(Integer eventId, String name) {      //set price to 0
        Price newPrice = new Price(name, 0);
        Offer newEventPrice = new Offer(newPrice);

        Event event = EventService.findById(eventId);

        event.setPrice(newEventPrice);
    }
}
