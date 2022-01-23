package bu.cs622.db;

import bu.cs622.UserDefinedException;
import bu.cs622.inventory.Inventory;

import java.util.List;

public interface IPersistence {
    List<Inventory> getInventoryList();

    void addInventory(Inventory inv) throws UserDefinedException;
}
