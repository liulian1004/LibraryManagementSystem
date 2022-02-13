package bu.cs622;

import bu.cs622.db.Database;
import bu.cs622.db.IPersistence;
import bu.cs622.inventory.Inventory;
import bu.cs622.user.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public IPersistence db;
    public  UserType people;


    public static void main(String[] args) {
        Main main = new Main();
        main.init();
    }

    public void init() {
        connectDB();
        System.out.println("Welcome to use Library Management System ");
        mainMenu();

    }


    private void mainMenu(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                while(true){
                    System.out.println("Please type 1 for user login, type 2 for admin login, type 3 for new user register, type 4 for quit system:");
                    String input = reader.readLine();
                    if(("1").equals(input)){
                        userLogin(reader);
                    }else if(("2").equals(input)){
                        adminLogin(reader);
                    }else if(("3").equals(input)){
                        userRegister(reader,db);
                    }else if(("4").equals(input)) {
                        System.out.println("Thank you for using this system, GoodBye");
                        break;
                    }else {
                        System.out.println("Your input is not correct, please try again");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }
    private void adminLogin(BufferedReader reader) {
        try {
            if(logIn("admin","admin.txt",db, reader)){
                adminMenu(reader);
            }else{
                System.out.println("Your input is not correct, please try again");
            }
        } catch (UserDefinedException e) {
            e.printStackTrace();
        }

    }
    boolean logIn(String role, String file,IPersistence db, BufferedReader reader) throws UserDefinedException {
        String name = null;
        String pw = null;

        try {
            System.out.println(String.format("Please type %s name: ",role));
            name = reader.readLine();
            System.out.println("Please type password: ");
            pw = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(db.verify(name, pw, file)) {
            people = new UserType(new User(name, pw, db, PeopleType.USER));
            return true;
        }
        return false;
    }

    private void adminMenu(BufferedReader reader){
        people = new UserType(new Admin("Admin","123", db, PeopleType.ADMIN));
        while(true){
            try {
                System.out.println("Please type 1 for checking inventory, type 2 for adding new inventory, type 3 for backing to main menu");
                String input = reader.readLine();
                if(input.equals("1")){
                    printInventory();
                }else if(input.equals("2")){
                    addInventory(reader);
                }
                else if(input .equals("3")){
                    break;
                }else{
                    System.out.println("Your input is not correct, please try again");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private  void addInventory(BufferedReader reader) {
        while(true){
            String name = null;
            String number = null;
            String type = null;
            reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                System.out.println("Please type name: ");
                name = reader.readLine();
                System.out.println("Please type number: ");
                number = reader.readLine();
                System.out.println("Please type book, ebook or magazine: ");
                type = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if(type != null && name != null && number != null){
                try {
                    people.addInventory(name,number,type);
                    System.out.println("New inventory update success!");
                    printInventory();
                    break;
                } catch (UserDefinedException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("Your input is not correct, please try again");
            }
        }

    }

    private void userLogin(BufferedReader reader) {
        connectDB();
        try {
            if(logIn("user","user.txt",db, reader)){
                userMenu(reader);
            }else{
                System.out.println("Your input is not correct, please try again");
            }
        } catch (UserDefinedException e) {
            e.printStackTrace();
        }

    }

    private void userMenu(BufferedReader reader) {
        try {
            while (true) {
                System.out.println("Please type 1 for checking inventory, borrowing book or returning book, type 2 for exiting the system");
                String input = reader.readLine();
                if (input.equals("1")) {
                    printInventory();
                    borrowReturnBookMenu(reader);
                } else if (input.equals("2")) {
                    break;
                } else {
                    System.out.println("Your input is not correct, please try again");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void borrowReturnBookMenu(BufferedReader reader){
        try {
            while (true) {
                System.out.println("Please type 1 for borrowing book, type 2 for return book, and type 3 for go back");
                String input = reader.readLine();
                if (input.equals("1")) {
                    borrowBook(reader, db);
                } else if(input.equals("2")){
                    returnBook(reader, db);
                }
                else if (input.equals("3")) {
                    break;
                } else {
                    System.out.println("Your input is not correct, please try again");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void borrowBook(BufferedReader reader, IPersistence db){
        try {
            while (true) {
                System.out.println("Please type book id listed from below");
                printInventory();
                System.out.println("Please type book id for borrowing book, type 0 for go back");
                String input = reader.readLine();
                if (bookAvailable(input)) {
                    people.borrowBook(Integer.valueOf(input),-1);
                    System.out.println("You borrow this book succesfully");
                } else if (input.equals("0")) {
                    break;
                } else {
                    System.out.println("Your input id is not correct, please try again");
                }
            }

        } catch (IOException | UserDefinedException e) {
            e.printStackTrace();
        }
    }

    void returnBook(BufferedReader reader, IPersistence db){
        try {
            while (true) {
                System.out.println("Please type book id for returning, type 0 for go back");
                String input = reader.readLine();
                if (bookExist(input)) {
                    people.returnBook(Integer.valueOf(input),1);
                    System.out.println("You have return borrow successfully");
                } else if (input.equals("0")) {
                    break;
                } else {
                    System.out.println("Your input id is not correct, please try again");
                }
            }

        } catch (IOException | UserDefinedException e) {
            e.printStackTrace();
        }
    }
    private boolean bookExist(String id) throws UserDefinedException {
        List<Inventory> inventories = db.getInventoryList();
        for(Inventory inv: inventories ){
            if(id.equals(String.valueOf(inv.getId()))){
                return true;
            }
        }
        return false;
    }

    private boolean bookAvailable(String id) throws UserDefinedException {
        List<List<String>> inventories = people.checkInventory();
        for(List<String> inv: inventories ){
            if(id.equals(String.valueOf(inv.get(0)))){
                return true;
            }
        }
        return false;
    }


    void userRegister(BufferedReader reader,IPersistence db){
            String name = null;
            String pw = null;

            try {
                System.out.println("Please type user name: ");
                name = reader.readLine();
                System.out.println("Please type password: ");
                pw = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                db.signUp(new User(name, pw, db, PeopleType.USER));
            } catch (UserDefinedException e) {
                e.printStackTrace();
            }
            System.out.println("Your register is successful!");
    }

    public void connectDB() {
        try {
            db = new Database("inventory.txt");
        } catch (UserDefinedException e) {
            e.printErrorMessage();
            e.printStackTrace();
        }
    }

    private void printInventory(){
        List<List<String>> inventories = null;
        try {
            inventories = people.checkInventory();
        } catch (UserDefinedException e) {
            e.printStackTrace();
        }
        if(inventories != null && inventories.size() > 0 && inventories.get(0).size() == 4){
            System.out.println("============Inventory List for Admin=============");
            System.out.printf("%-22s%-22s%-22s%-22s\n","Id","Name","Type","Number");
            for(List<String> inventory: inventories){
                System.out.printf("%-22s%-22s%-22s%-22s\n",inventory.get(0),inventory.get(1),inventory.get(2), inventory.get(3));
            }
        }else if(inventories != null && inventories.size() > 0 && inventories.get(0).size() == 3){
            System.out.println("=========Inventory List for User==========");
            System.out.printf("%-22s%-22s%-22s\n","Id","Name","Type");
            for(List<String> inventory: inventories){
                System.out.printf("%-22s%-22s%-22s\n",inventory.get(0),inventory.get(1), inventory.get(2));
            }
        }

    }


}
