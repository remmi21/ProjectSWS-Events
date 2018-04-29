package event;

import java.util.List;

public class Order {
    private User user;
    private List<Ticket> tickets;

    public Order(User user, List<Ticket> tickets) {
        this.user = user;
        this.tickets = tickets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
