package bu.cs622.user;

import bu.cs622.inventory.*;

import java.awt.*;

import static bu.cs622.Main.db;

public class Admin extends People {

    // Admin can't register by system, here is the template method
    public Admin(String userName, String password) {
        super(userName, password);
    }

    public void  getInventory() {
        System.out.println("============Inventory List for Admin=============");
        System.out.printf("%-22s%-22s%-22s\n","Name","Type","Number");
        for (Inventory in : db.getInventoryList()){
           System.out.printf("%-22s%-22s%-22s\n",in.getName(),in.getType().name(),in.getNumber());
        }
    }
    public void addInventory(String name,String num, String type){
        int number = Integer.valueOf(num);
        //TODO: error hanlder for wrong type
        Type t = Type.valueOf(type);
        Inventory inv;
        if(t == Type.Book){
           inv = new Book(name, number,t);
        }else if (t == Type.Ebook){
            inv = new Ebook(name, number,t);
        }else {
            inv = new Magazin(name, number, t);
        }
        db.addInventory(inv);
        getInventory();
    }

}
