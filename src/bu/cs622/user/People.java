package bu.cs622.user;

import bu.cs622.UserDefinedException;
import bu.cs622.db.IPersistence;
import com.google.gson.annotations.Expose;

import java.util.*;


public abstract class People {
    @Expose
    private String userName;
    @Expose
    private String password;
    protected IPersistence db = null;
    private PeopleType type = null;

    public People(String userName, String password, IPersistence idb, PeopleType type) {
        this.userName = userName;
        this.password = password;
        this.db = idb;
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public abstract List<List<String>> getInventory() throws UserDefinedException;
    public abstract String getSum() throws UserDefinedException;
    public abstract String borrowedBooks() throws UserDefinedException;

    public void borrowBook(int id, int number) throws UserDefinedException {
        db.updateInventory(id, number, getUserName());
    };

    public void returnBook(int id, int number) throws UserDefinedException {
        db.updateInventory(id, number, getUserName());
    };
}

