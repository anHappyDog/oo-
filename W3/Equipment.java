import java.math.BigInteger;

public class Equipment implements  Commodity {
    private int id;
    private String name;
    private BigInteger price;

    @Override
    public void userBy(Adventurer userby) {}

    @Override
    public BigInteger getPrice() {
        return price;
    }

    @Override
    public void setPrice(BigInteger price) {
        this.price = price;
    }

    @Override
    public int getId() { return id; }

    @Override
    public String getName() { return name; }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public int compareTo(Commodity other) {
        if (this.price.compareTo(other.getPrice()) > 0) {
            return -1;
        }
        else if (this.price.compareTo(other.getPrice()) < 0) {
            return 1;
        }
        return this.id > other.getId() ? -1 : 1;
    }

    public Equipment() {
        setPrice(BigInteger.valueOf(0));
    }

    public Equipment(int id,String name,long price) {
        setId(id);
        setName(name);
        setPrice(BigInteger.valueOf(price));
    }
}
