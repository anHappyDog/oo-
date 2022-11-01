public class ExpBottle extends Bottle {
    private double expRatio;

    public double getExpRatio() { return  expRatio; }

    public void setExpRatio(double expRatio) { this.expRatio = expRatio; }

    @Override
    public String toString() {
        return "The healingPotion's id is " + getId() + ", name is " + getName() +
                ", capacity is " + getCapacity() + ", filled is " + isFilled() + ", expRatio is "
                + getExpRatio() + ".";
    }

    @Override
    public void userBy(Adventurer userby) throws Exception {
        if (!isFilled()) {
            throw  new Exception("Failed to use " + getName() + " because it is empty.");
        }
        setFilled(false);
        userby.setHealth(userby.getHealth() + getCapacity() / 10.0);
        setPrice((long)Math.floor(getPrice() / 10.0));
        userby.setExp(userby.getExp() * getExpRatio());
        System.out.println(userby.getName() + " drank " + getName() +
                " and recovered " + getCapacity() + ".\n" +
                userby.getName() + "'s exp became " + userby.getExp() + ".");
    }

    public  ExpBottle(int id,String name,long price,double capacity,double expRatio) {
        super(id,name,price,capacity);
        setExpRatio(expRatio);
    }
}
