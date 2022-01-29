package bu.cs622.user;

import bu.cs622.UserDefinedException;
import bu.cs622.db.IPersistence;

import java.util.*;


public abstract class People {
    private String userName;
    private String password;
    protected final IPersistence db;

    public People(String userName, String password, IPersistence idb) {
        this.userName = userName;
        this.password = password;
        db = idb;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public abstract List<List<String>> getInventory();

    public abstract void addInventory(String name,String num, String type) throws UserDefinedException;

}

