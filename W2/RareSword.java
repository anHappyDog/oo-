public class RareSword extends Sword {
    private double extraExpBouns;

    public double getExtraExpBouns() {
        return extraExpBouns;
    }

    public void setExtraExpBouns(double extraExpBouns) { this.extraExpBouns = extraExpBouns; }

    @Override
    public void userBy(Adventurer userby) {
        userby.setHealth(userby.getHealth() - 10.0);
        userby.setExp(userby.getExp() + 10.0 + getExtraExpBouns());
        userby.setMoney(userby.getMoney() + getSharpness());
        System.out.println(userby.getName() + " used " + getName() +
                " and earned " + getSharpness() + ".\n" +
                userby.getName() + " got extra exp " + getExtraExpBouns() + ".");
    }

    @Override
    public String toString() {
        return "The rareSword's id is " + getId() + ", name is " + getName() +
                ", sharpness is " + getSharpness() + ", extraExpBonus is "
                + getExtraExpBouns() + ".";
    }

    public RareSword() {}

    public RareSword(int id,String name,long price, double sharpness,double extraExpBouns) {
        super(id,name,price,sharpness);
        setExtraExpBouns(extraExpBouns);
    }
}
