package bu.cs622;

import bu.cs622.db.Database;
import bu.cs622.user.Admin;
import bu.cs622.user.User;

public class Main {
    public static Database db;
    public static void main(String[] args) {
	// write your code here
        initDB();
        Admin admin = new Admin("abc","123");
        admin.getInventory();
        User user = new User("abc","123");
        user.getInventory();
        admin.addInventory("new book","5","Ebook");
        user.getInventory();
    }
    // a template method to create db locally
    public static void initDB() {
        db = new Database();
    }
}
