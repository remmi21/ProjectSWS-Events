package event;

import java.util.List;

public class OrderService {

    public static List getOrders(Integer id) {
        EventService eventService = new EventService();
        User user = eventService.userList.get(id);

        if(user != null) {
            List<Order> orders = user.getOrders();
            return orders;
        }

        return null;
    }

}
