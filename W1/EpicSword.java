public class EpicSword extends Sword {
    private double evolveRatio;

    public void setEvolveRatio(double evolveRatio) { this.evolveRatio = evolveRatio; }

    public double getEvolveRatio() { return evolveRatio; }

    @Override
    public String toString() {
        return "The epicSword's id is " + getId() + ", name is " + getName() +
                ", sharpness is " + getSharpness() + ", evolveRatio is " + getEvolveRatio() + ".";
    }

    @Override
    public void userBy(Adventurer userby) {
        System.out.println(userby.getName() + " used " + getName() +
                " and earned " + getSharpness() + ".\n" +
                getName() + "'s sharpness became " + getSharpness() * getEvolveRatio() + ".");
        userby.setHealth(userby.getHealth() - 10.0);
        userby.setExp(userby.getExp() + 10.0);
        userby.setMoney(userby.getMoney() + getSharpness());
        setSharpness(getSharpness() * getEvolveRatio());

    }

    public EpicSword() {}

    public EpicSword(int id,String name,long price, double sharpness,double evolveRatio) {
        super(id,name,price,sharpness);
        setEvolveRatio(evolveRatio);
    }
}
