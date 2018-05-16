package event;

import java.util.ArrayList;
import java.util.List;

public class TicketService {

    public static Ticket bookTicket(Integer eventId, Integer amount, Integer userId) {

        EventService eventService = new EventService();
        Event event = eventService.findById(eventId);
        User user = eventService.userList.get(userId);

        PriceService priceService = new PriceService();
        List<Price> priceList = priceService.findEventPrice(eventId);
        Integer ticketPrice = 0, eventPrice=0;
        if(user != null) {
            for(Price price : priceList) {
                eventPrice = price.getPrice();
                if(price.getName().equals("Members Admission")) {
                    ticketPrice = eventPrice*amount;
                } else if(price.getName().equals("Free") || price.getPrice() == 0) {
                    ticketPrice = 0;
                }
            }
        } else {
            for(Price price : priceList) {
                eventPrice = price.getPrice();
                ticketPrice = eventPrice*amount;
            }
        }

        Ticket ticket = new Ticket(event, userId, amount, ticketPrice);

        if(user != null) { // add ticket to order list of user
            List<Order> orderList = user.getOrders();

            Order order = new Order(userId, ticket);

            if(orderList != null) {
                orderList.add(order);
            } else {
                List<Order> newOrderList = new ArrayList<>();
                newOrderList.add(order);
                user.setOrders(newOrderList);
            }
        }

        return ticket;
    }

}
