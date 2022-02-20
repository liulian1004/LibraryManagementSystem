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




}
