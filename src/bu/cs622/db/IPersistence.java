package bu.cs622.db;

import bu.cs622.UserDefinedException;
import bu.cs622.inventory.Inventory;
import bu.cs622.user.User;

import java.util.List;

public interface IPersistence {
    List<Inventory> getInventoryList() throws UserDefinedException;

    void addInventory(Inventory inv) throws UserDefinedException;

    void signUp(User newUser) throws UserDefinedException;
    boolean verify(String name, String pw, String file) throws UserDefinedException;
    int getCurId() throws UserDefinedException;
    void updateInventory(int id) throws UserDefinedException;
}
