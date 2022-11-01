class  Bottle extends Equipment {
    private  double capacity;
    private boolean filled;

    @Override public String toString() {
        return "The bottle's id is " + this.getId() +
                ", name is " + this.getName() + ", capacity is "
                + this.capacity + ", filled is " + this.filled + ".";
    }

    public boolean isFilled() {
        return this.filled;
    }

    public double getCapacity() {
        return  this.capacity;
    }

    public void setFilled(boolean filed) {
        this.filled = filed;
    }

    @Override
    public void userBy(Adventurer userby) throws Exception {
        if (!isFilled()) {
            throw new Exception("Failed to use " + getName() + " because it is empty.");
        }
        userby.setHealth(userby.getHealth() + capacity / 10);
        setFilled(false);
        setPrice((long)Math.floor(getPrice() / 10.0));
        System.out.println(userby.getName() +
                " drank " + getName() +
                " and recovered " + capacity / 10 +
                ".");
    }

    Bottle() {}

    Bottle(int id,String name,long price,double capacity) {
        super(id,name,price);
        this.capacity = capacity;
        this.filled = true;
    }
}