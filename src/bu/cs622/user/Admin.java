package bu.cs622.user;

import bu.cs622.UserDefinedException;
import bu.cs622.inventory.*;

import java.util.*;

import static bu.cs622.Main.db;

public class Admin extends People {

    // Admin can't register by system, here is the template method
    public Admin(String userName, String password) {
        super(userName, password);
    }

    public List<List<String>> getInventory() {
        List<List<String>> inventories = new ArrayList<>();
        for(Inventory in: db.getInventoryList()){
            List<String> inventory =  new ArrayList<>();
            inventory.add(in.getName());
            inventory.add(in.getType().name());
            inventory.add(String.valueOf(in.getNumber()));
            inventories.add(inventory);
        }
        return inventories;
    }
    public void addInventory(String name,String num, String type) throws UserDefinedException {
        int number = Integer.valueOf(num);
        Type t;
        try{
            t = Type.valueOf(type.toUpperCase());
        } catch (Exception e){
            throw new UserDefinedException(String.format("The inventory type '%s' does not exist", type));
        }
        Inventory inv;
        if(t == Type.BOOK){
           inv = new Book(name, number,t);
        }else if (t == Type.EBOOK){
            inv = new Ebook(name, number,t);
        }else {
            inv = new Magazine(name, number, t);
        }
        db.addInventory(inv);
    }

}
