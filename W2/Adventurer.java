import java.util.HashMap;

class Adventurer {
    private int id;
    private String name;
    private double health;
    private double exp;
    private double money;
    private HashMap<Integer,Equipment> equipments;

    public double getHealth() { return health; }

    public String getName() { return name; }

    public int getId() { return id; }

    public double getExp() { return exp; }

    public double getMoney() { return money; }

    public void setHealth(double health) { this.health = health; }

    public void setExp(double exp) { this.exp = exp; }

    public void setMoney(double money) { this.money = money; }

    public HashMap<Integer,Equipment> getEquipment() {
        return this.equipments;
    }

    public boolean isIdEqual(int id) {
        return this.id == id;
    }

    public void use(Equipment equipment) {
        try {
            equipment.userBy(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Adventurer() {}

    public Adventurer(int id,String name) {
        this.id = id;
        this.name = name;
        equipments = new HashMap<Integer,Equipment>();
        health = 100;
    }

    @Override public String toString() {
        return "The adventurer's id is " + id + ", name is " + name + ", health is "
                + health + ", exp is " + exp + ", money is " + money + ".";

    }
}
