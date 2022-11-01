import java.math.BigInteger;

public class ExpBottle extends Bottle {
    private double expRatio;

    public double getExpRatio() { return  expRatio; }

    public void setExpRatio(double expRatio) { this.expRatio = expRatio; }

    @Override
    public String toString() {
        return "The expBottle's id is " + getId() + ", name is " + getName() +
                ", capacity is " + getCapacity() + ", filled is " + isFilled() + ", expRatio is "
                + getExpRatio() + ".";
    }

    @Override
    public void userBy(Adventurer userby) {
        if (isFilled()) {
            setFilled(false);
            userby.setHealth(userby.getHealth() + getCapacity() / 10.0);
            setPrice(getPrice().divide(BigInteger.valueOf(10)));
            userby.setExp(userby.getExp() * getExpRatio());
            System.out.println(userby.getName() + " drank " + getName() +
                    " and recovered " + getCapacity() / 10 + ".\n" +
                    userby.getName() + "'s exp became " + userby.getExp() + ".");
        }
        else {
            System.out.println("Failed to use " + getName() + " because it is empty.");
        }
    }

    public  ExpBottle(int id,String name,long price,double capacity,double expRatio) {
        super(id,name,price,capacity);
        setExpRatio(expRatio);
    }
}
