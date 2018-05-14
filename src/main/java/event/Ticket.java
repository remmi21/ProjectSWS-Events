package event;

public class Ticket {
    private Event event;
    private Integer userId;
    private Integer amount;
    private Integer price;

    public Ticket(Event event, Integer userId, Integer amount, Integer price) {
        this.event = event;
        this.userId = userId;
        this.amount = amount;
        this.price = price;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Integer getUser() {
        return userId;
    }

    public void setUser(Integer id) {
        this.userId = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() { return price; }

    public void setPrice(Integer price) { this.price = price; }
}
