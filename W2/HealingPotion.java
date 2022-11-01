public class HealingPotion extends Bottle {
    private double efficiency;

    @Override
    public void userBy(Adventurer userby) throws Exception {
        if (!isFilled()) {
            throw new Exception("Failed to use " + getName() + " because it is empty.");
        }
        setFilled(false);
        userby.setHealth(userby.getHealth() + getCapacity() / 10.0 +
                getCapacity() * getEfficiency());
        setPrice((long)(getPrice() / 10.0));
        System.out.println(userby.getName() + " drank " + getName() +
                " and recovered " + getCapacity() / 10.0 + ".\n" +
                userby.getName() + " recovered extra " + getCapacity() * getEfficiency() + ".");
    }

    @Override
    public String toString() {
        return "The healingPotion's id is " + getId() + ", name is " + getName() +
                ", capacity is " + getCapacity() + ", filled is " + isFilled() + ", efficiency is "
                + getEfficiency() + ".";
    }

    public  double getEfficiency() { return efficiency; }

    public void setEfficiency(double efficiency) { this.efficiency = efficiency; }

    public HealingPotion() {}

    public HealingPotion(int id,String name,long price,double capacity,double efficiency) {
        super(id,name,price,capacity);
        setEfficiency(efficiency);

    }
}
