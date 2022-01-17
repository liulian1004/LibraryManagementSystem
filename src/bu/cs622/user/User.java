package bu.cs622.user;

import bu.cs622.Main;
import bu.cs622.inventory.Inventory;

import static bu.cs622.Main.db;

public class User extends People {
    public User(String userName, String password) {
        super(userName, password);
    }
    public void  getInventory() {
        System.out.println("=========Inventory List for User==========");
        System.out.printf("%-22s%-22s\n","Name","Type");
        for (Inventory in : db.getInventoryList()){
            if(in.getNumber() > 0){
                System.out.printf("%-22s%-22s\n",in.getName(),in.getType().name());
            }

        }
    }
}
