public class Equipment {
    private int id;
    private String name;
    private long price;

    public void userBy(Adventurer userby) throws Exception {}

    public long getPrice() {
        return this.price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void setId(int id) { this.id = id; }

    public Equipment() {}

    public Equipment(int id,String name,long price) {
        setId(id);
        setName(name);
        setPrice(price);
    }
}
