package bu.cs622.db;

import bu.cs622.UserDefinedException;
import bu.cs622.inventory.Inventory;

import java.util.List;

public interface IPersistence {
    List<Inventory> getInventoryList();

    void addInventory(Inventory inv) throws UserDefinedException;

    void signUp(String name, String pw) throws UserDefinedException;
    boolean verify(String name, String pw, String file) throws UserDefinedException;


}
