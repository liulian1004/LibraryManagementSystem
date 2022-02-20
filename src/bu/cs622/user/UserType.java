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


    public void borrowBook(int id, int number) throws UserDefinedException {
        getPeople().borrowBook(id, number);
    };

    public void returnBook(int id, int number) throws UserDefinedException {
        getPeople().returnBook(id, number);
    };

}
