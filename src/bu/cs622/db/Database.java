package bu.cs622.db;

import bu.cs622.inventory.Book;
import bu.cs622.inventory.Inventory;
import bu.cs622.inventory.Magazin;
import bu.cs622.inventory.Type;
import bu.cs622.user.People;
import bu.cs622.user.User;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<Inventory> inventoryList;
    private List<User> users;

    public Database() {
        inventoryList = new ArrayList<>();
        inventoryList.add(new Book("Little Prince",5, Type.Book));
        inventoryList.add(new Magazin("Fashion World",5, Type.Magazine));
        inventoryList.add(new Magazin("Clean code",5, Type.Ebook));
        inventoryList.add(new Magazin("Effective Java",0, Type.Book));


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

    public void addInventory(Inventory inv){
        inventoryList.add(inv);
    }

}
