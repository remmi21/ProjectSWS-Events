package event;

public class Pricing {
    private int normalPrice;
    private int memberPrice;

    public Pricing(int normalPrice, int memberPrice) {
        this.normalPrice = normalPrice;
        this.memberPrice = memberPrice;
    }

    public int getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(int normalPrice) {
        this.normalPrice = normalPrice;
    }

    public int getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(int memberPrice) {
        this.memberPrice = memberPrice;
    }
}
