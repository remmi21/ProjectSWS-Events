package event;

public class Pricing {
    private String name;
    private int normalPrice;

    public Pricing(String name, Integer normalPrice) {
        this.normalPrice = normalPrice;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(int normalPrice) {
        this.normalPrice = normalPrice;
    }

}
