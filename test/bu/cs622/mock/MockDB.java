package bu.cs622.mock;

import bu.cs622.UserDefinedException;
import bu.cs622.db.IPersistence;
import bu.cs622.inventory.Book;
import bu.cs622.inventory.Inventory;
import bu.cs622.inventory.Type;

import java.util.ArrayList;
import java.util.List;

public class MockDB implements IPersistence {

    private List<Inventory> inventories;
    private List<String[]> users= new ArrayList<>();
    private List<String[]> admin = new ArrayList<>();
    public MockDB() {
        inventories = new ArrayList<>();
        Inventory inv1 = new Book("test book1",5, Type.BOOK);
        Inventory inv2 = new Book("test book2",0, Type.BOOK);
        inventories.add(inv1);
        inventories.add(inv2);
        users.add(new String[]{"user1","123"});
        admin.add(new String[]{"admin","123"});
    }

    @Override
    public List<Inventory> getInventoryList() {
        return inventories;
    }

    @Override
    public void addInventory(Inventory inv) throws UserDefinedException {
        inventories.add(inv);
    }

    @Override
    public void signUp(String name, String pw) throws UserDefinedException {
        users.add(new String[]{"user2","1234"});
    }

    @Override
    public boolean verify(String name, String pw, String file) throws UserDefinedException {
        if(file == "admin.txt"){
            return check(name, pw,admin);
        }else{
            return check(name, pw, users);
        }
    }

    private boolean check(String name, String pw,List<String[]> people) {
        for(String[] cur: people){
            if(name.equals(cur[0]) && pw.equals(cur[1])){
                return true;
            }
        }
        return false;
    }

    public List<String[]> getUsers() {
        return users;
    }
}
