package bu.cs622.inventory;

import java.util.Objects;

public class Inventory {
    private int id;
    private String Name;
    private int Number;
    private InventoryType inventoryType;

    public Inventory(int id, String name, int number, InventoryType inventoryType) {
        this.Name = name;
        this.Number = number;
        this.inventoryType = inventoryType;
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public int getNumber() {
        return Number;
    }

    public int getId() {
        return id;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "Name='" + Name + '\'' +
                ", Number=" + Number +
                ", type=" + inventoryType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inventory)) return false;
        Inventory inventory = (Inventory) o;
        return getNumber() == inventory.getNumber() &&
                getName().equals(inventory.getName()) &&
                getInventoryType() == inventory.getInventoryType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, Number, inventoryType);
    }
}
