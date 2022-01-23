package bu.cs622.mock;

import bu.cs622.UserDefinedException;
import bu.cs622.db.IPersistence;
import bu.cs622.inventory.Book;
import bu.cs622.inventory.Inventory;
import bu.cs622.inventory.Type;

import java.util.ArrayList;
import java.util.List;

public class MockDB implements IPersistence {

    private List<Inventory> repo;

    public MockDB() {
        repo = new ArrayList<>();
        Inventory inv1 = new Book("test book1",5, Type.BOOK);
        Inventory inv2 = new Book("test book2",0, Type.BOOK);
        repo.add(inv1);
        repo.add(inv2);
    }

    @Override
    public List<Inventory> getInventoryList() {
        return repo;
    }

    @Override
    public void addInventory(Inventory inv) throws UserDefinedException {
        repo.add(inv);
    }
}
