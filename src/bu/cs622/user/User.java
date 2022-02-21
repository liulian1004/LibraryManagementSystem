package bu.cs622.user;

import bu.cs622.Main;
import bu.cs622.UserDefinedException;
import bu.cs622.db.IPersistence;
import bu.cs622.inventory.Inventory;

import java.util.*;
import java.util.stream.Collectors;


public class User extends People {


    public User(String userName, String password, IPersistence idb, PeopleType type) {
        super(userName, password, idb, type);
    }

    @Override
    public List<List<String>> getInventory() throws UserDefinedException {
    return db.getInventoryList().
                stream().filter(inv -> inv.getNumber()>0).
                sorted(Comparator.comparing(Inventory::getId)).
                map(inv ->Arrays.asList(new String[]{String.valueOf(inv.getId()),inv.getName(),inv.getInventoryType().name()})).
                collect(Collectors.toList());
    }

    @Override
    public String getSum() {
        return "User has no right to this operation!";
    }

    @Override
    public String borrowedBooks() throws UserDefinedException {
        List<String> list = db.borrowedBookLists(getUserName());
        String res = "You have borrowed following books: \n";
        for(String s: list){
            res = res + s + ",";
        }
        return res.substring(0, res.length()-1);
    }


}
