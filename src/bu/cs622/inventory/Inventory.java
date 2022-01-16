package bu.cs622.inventory;

public abstract class Inventory {
    private String Name;
    private int Number;
    private Type type;

    public Inventory(String name, int number, Type type) {
        Name = name;
        Number = number;
        this.type = type;
    }

    public String getName() {
        return Name;
    }

    public int getNumber() {
        return Number;
    }

    public Type getType() {
        return type;
    }

}
