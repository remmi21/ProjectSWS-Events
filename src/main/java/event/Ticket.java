package event;

public class Ticket {
    private Event event;
    private Integer amount;
    private User user;

    public Ticket(Event event, Integer amount, User user) {
        this.event = event;
        this.amount = amount;
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
