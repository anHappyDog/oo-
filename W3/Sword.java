
public class Sword extends Equipment {
    private double sharpness;

    public double getSharpness() { return sharpness; }

    public void setSharpness(double sharpness) { this.sharpness = sharpness; }

    @Override
    public String toString() {
        return "The sword's id is " + getId() + ", name is " + getName()
                + ", sharpness is " + getSharpness() + ".";
    }

    @Override
    public void userBy(Adventurer userby) {
        userby.setHealth(userby.getHealth() - 10.0);
        userby.setExp(userby.getExp() + 10.0);
        userby.setMoney(userby.getMoney() + getSharpness());
        System.out.println(userby.getName() + " used " + getName() +
                " and earned " + getSharpness() + "."
        );
    }

    public Sword() {}

    public Sword(int id, String name, long price, double sharpness) {
        super(id,name,price);
        setSharpness(sharpness);
    }
}
