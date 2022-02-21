package bu.cs622.db;

import bu.cs622.UserDefinedException;
import bu.cs622.inventory.Inventory;
import bu.cs622.user.User;

import java.util.List;

public interface IPersistence {
    List<Inventory> getInventoryList() throws UserDefinedException;

   void addInventory(String name, String number, String type) throws UserDefinedException;

    void signUp(User newUser) throws UserDefinedException;
    boolean verify(String name, String pw, String file) throws UserDefinedException;
    void updateInventory(int id, int number, String userName) throws UserDefinedException;
    void disconnectDB() throws UserDefinedException;
    int getSum() throws UserDefinedException;
    List<String> borrowedBookLists(String userName) throws UserDefinedException ;
}
