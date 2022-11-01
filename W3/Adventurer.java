import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

class Adventurer implements Commodity {
    private int id;
    private String name;
    private double health;
    private double exp;
    private double money;
    //private BigInteger price;
    private ArrayList<Commodity> commodities;

    //待完成
    @Override
    public void userBy(Adventurer user) {
        Collections.sort(this.getCommodities());
        for (Commodity iter : this.getCommodities()) {
            user.use(iter);
        }
    }

    @Override
    public void setPrice(BigInteger price) { /*this.price = price;*/ }

    @Override
    public BigInteger getPrice() {
        BigInteger tmp = new BigInteger("0");
        for (Commodity iter : commodities) {
            tmp = tmp.add(iter.getPrice());
        }
        return tmp;
    }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public String getName() { return name; }

    @Override
    public int getId() { return id; }

    @Override
    public void setId(int id) { this.id = id; }

    public double getHealth() { return health; }

    public double getExp() { return exp; }

    public double getMoney() { return money; }

    public void setHealth(double health) { this.health = health; }

    public void setExp(double exp) { this.exp = exp; }

    public void setMoney(double money) { this.money = money; }

    public ArrayList<Commodity> getCommodities() {
        return this.commodities;
    }

    public boolean isIdEqual(int id) {
        return this.id == id;
    }

    public void use(Commodity commodities) {
        try {
            commodities.userBy(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Adventurer() {
        commodities = new ArrayList<>();
    }

    public Adventurer(int id,String name) {
        this.id = id;
        this.name = name;
        commodities = new ArrayList<>();
        health = 100;
        money = 0;
    }

    @Override public String toString() {
        return "The adventurer's id is " + id + ", name is " + name + ", health is "
                + health + ", exp is " + exp + ", money is " + money + ".";

    }

    @Override
    public int compareTo(Commodity other) {
        if (getPrice().compareTo(other.getPrice()) > 0) {
            return -1;
        }
        else if (getPrice().compareTo(other.getPrice()) < 0) {
            return 1;
        }
        return this.id > other.getId() ? -1 : 1;
    }
}
