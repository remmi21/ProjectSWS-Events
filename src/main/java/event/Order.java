package event;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType("http://schema.org/Order")
public class Order {
    private Integer userId;
    private Ticket ticket;

    public Order(Integer userId, Ticket ticket) {
        this.userId = userId;
        this.ticket = ticket;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer id) {
        this.userId = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
