package bu.cs622.user;

import bu.cs622.UserDefinedException;
import bu.cs622.inventory.Inventory;

import java.util.List;

public class UserType<P extends People> {
    private P people;

    public UserType(P people) {
        this.people = people;
    }

    public P getPeople() {
        return people;
    }

    public List<List<String>> checkInventory() throws UserDefinedException {
        return getPeople().getInventory();
    }

    public void addInventory(String name, String num, String type) throws UserDefinedException {
        getPeople().addInventory(name,num, type);
    }

}
