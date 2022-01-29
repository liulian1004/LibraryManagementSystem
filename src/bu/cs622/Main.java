package bu.cs622;

import bu.cs622.db.Database;
import bu.cs622.db.IPersistence;
import bu.cs622.user.Admin;
import bu.cs622.user.User;
import bu.cs622.user.UserType;

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
        while(true){
            System.out.println("Please type 1 for user login, type 2 for admin login, type 3 for new user register:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String input = reader.readLine();
                if(input.equals("1")){
                    userLogin();
                }else if(input.equals("2")){
                    adminLogin();
                }else if(input.equals("3")){
                    userRegister();
                }
                else{
                    System.out.println("Your input is not correct, please try again");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void adminLogin() {
        try {
            if(logIn("admin","admin.txt")){
                adminMenu();
            }else{
                System.out.println("Your input is not correct, please try again");
            }
        } catch (UserDefinedException e) {
            e.printStackTrace();
        }

    }
    boolean logIn(String role, String file) throws UserDefinedException {
        String name = null;
        String pw = null;
        System.out.println(String.format("Please type %s name: ",role));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            name = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Please type password: ");
        try {
            pw = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return db.verify(name, pw, file);
    }

    private void adminMenu(){
        people = new UserType(new Admin("Admin","123", db));
        System.out.println("Please type 1 for checking inventory, type 2 for adding new inventory, type 3 for exiting the system");
        while(true){
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String input = reader.readLine();
                if(input.equals("1")){
                    printInventory();
                }else if(input.equals("2")){
                    addInventory();
                }
                else if(input .equals("3")){
                    System.out.println("Thank you for using this system, GoodBye");
                    System.exit(1);
                }else{
                    System.out.println("Your input is not correct, please try again");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private  void addInventory() {
        while(true){
            System.out.println("Please type name: ");
            String name = null;
            String number = null;
            String type = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                name = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Please type number: ");

            try {
                number = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Please type book, ebook or magazine: ");
            try {
                type = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(type != null && name != null && number != null){
                try {
                    people.addInventory(name,number,type);
                    System.out.println("New inventory update success!");
                    printInventory();
                    quitSystem();
                } catch (UserDefinedException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("Your input is not correct, please try again");
            }
        }

    }

    private void userLogin() {
        connectDB();
        try {
            if(logIn("user","user.txt")){
                userMenu();
            }else{
                System.out.println("Your input is not correct, please try again");
            }
        } catch (UserDefinedException e) {
            e.printStackTrace();
        }


    }

    private void userMenu() {
        people = new UserType(new User("User1","123", db));
        while(true){
            System.out.println("Please type 1 for checking inventory, type 2 for exiting the system");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String input = reader.readLine();
                if(input.equals("1")){
                    printInventory();
                }else if(input .equals("2")){
                    quitSystem();
                }else{
                    System.out.println("Your input is not correct, please try again");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void userRegister() {
        String name = null;
        String pw = null;
        System.out.println("Please type user name: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            name = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Please type password: ");
        try {
            pw = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            db.signUp(name,pw);
        } catch (UserDefinedException e) {
            e.printStackTrace();
        }
        System.out.println("Your register is successful!");
        mainMenu();

    }
    public void connectDB() {
        try {
            db = new Database();
        } catch (UserDefinedException e) {
            e.printErrorMessage();
            e.printStackTrace();
        }
    }

    private void printInventory(){
        List<List<String>> inventories = null;
        inventories = people.checkInventory();
        if(inventories != null && inventories.size() > 0 && inventories.get(0).size() == 3){
            System.out.println("============Inventory List for Admin=============");
            System.out.printf("%-22s%-22s%-22s\n","Name","Type","Number");
            for(List<String> inventory: inventories){
                System.out.printf("%-22s%-22s%-22s\n",inventory.get(0),inventory.get(1),inventory.get(2));
            }
        }else if(inventories != null && inventories.size() > 0 && inventories.get(0).size() == 2){
            System.out.println("=========Inventory List for User==========");
            System.out.printf("%-22s%-22s\n","Name","Type");
            for(List<String> inventory: inventories){
                System.out.printf("%-22s%-22s\n",inventory.get(0),inventory.get(1));
            }
        }
        quitSystem();
    }

    private void quitSystem(){
        System.out.println("Thank you for using this system, GoodBye");
        System.exit(1);
    }


}
