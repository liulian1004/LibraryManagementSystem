package bu.cs622.user;

import bu.cs622.db.Database;
import bu.cs622.inventory.Inventory;

import java.util.List;

public abstract class People {
    private String userName;
    private String password;

    public People(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void  getInventory() { }


}

