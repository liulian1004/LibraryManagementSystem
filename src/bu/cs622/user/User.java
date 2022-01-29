package bu.cs622.user;

import bu.cs622.Main;
import bu.cs622.UserDefinedException;
import bu.cs622.db.IPersistence;
import bu.cs622.inventory.Inventory;

import java.util.*;


public class User extends People {

    public User(String userName, String password, IPersistence idb) {
        super(userName, password, idb);
    }

    @Override
    public List<List<String>> getInventory() {
        List<List<String>> inventories = new ArrayList<>();
        for(Inventory in : db.getInventoryList()){
            if (in.getNumber() > 0){
                List<String> inventory =  new ArrayList<>();
                inventory.add(in.getName());
                inventory.add(in.getType().name());
                inventories.add(inventory);
            }
        }
        return inventories;
    }

    @Override
    public void addInventory(String name, String num, String type) throws UserDefinedException {
        throw new UserDefinedException("You have no right to change inventory");
    }

}
