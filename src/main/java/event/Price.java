package event;

public class Price {
    private String name;
    private int price;

    public Price(String name, Integer price) {
        this.price = price;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
