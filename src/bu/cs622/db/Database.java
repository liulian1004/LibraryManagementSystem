package bu.cs622.db;

import bu.cs622.inventory.Book;
import bu.cs622.inventory.Inventory;
import bu.cs622.inventory.Type;
import bu.cs622.user.People;
import bu.cs622.user.User;

import java.util.List;

public class Database {
    private List<Inventory> inventoryList;
    private List<User> users;

    public Database() {
        inventoryList.add(new Book("Little Prince",5, Type.Book));
        inventoryList.add(new Book("Fashion World",5, Type.Magazine));
    }

    public List<Inventory> getInventoryList() {
        return inventoryList;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setInventoryList(List<Inventory> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
