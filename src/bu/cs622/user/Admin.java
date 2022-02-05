package bu.cs622.user;

import bu.cs622.UserDefinedException;
import bu.cs622.db.IPersistence;
import bu.cs622.inventory.*;

import java.util.*;
import java.util.stream.Collectors;

public class Admin extends People {

    private int curId = 0;
    public Admin(String userName, String password, IPersistence idb, PeopleType type) {
        super(userName, password, idb, type);
    }

    @Override
    public List<List<String>> getInventory() throws UserDefinedException {
        List<List<String>> inventory = db.getInventoryList().
                stream().
                sorted(Comparator.comparing(Inventory::getId)).
                map(inv ->Arrays.asList(new String[]{String.valueOf(inv.getId()), inv.getName(),inv.getInventoryType().name(), String.valueOf(inv.getNumber())})).
                collect(Collectors.toList());
        curId = inventory.size();
        return inventory;
    }


    @Override
    public void addInventory(String name,String num, String type) throws UserDefinedException {
        InventoryType t;
        try{
            t = InventoryType.valueOf(type.toUpperCase());
        } catch (Exception e){
            throw new UserDefinedException(String.format("The inventory type '%s' does not exist", type));
        }
        System.out.println(db.getCurId()+1);
        db.addInventory(new Inventory(db.getInventoryList().size()+1, name, Integer.valueOf(num), t));
    }


}
