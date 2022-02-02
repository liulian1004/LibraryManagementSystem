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
            people = new UserType(new User(name, pw, db));
            return true;
        }
        return false;
    }

    private void adminMenu(BufferedReader reader){
        people = new UserType(new Admin("Admin","123", db));
        while(true){
            try {
                System.out.println("Please type 1 for checking inventory, type 2 for adding new inventory, type 3 for backing to previous menu");
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
                System.out.println("Please type 1 for checking inventory, type 2 for exiting the system");
                String input = reader.readLine();
                if (input.equals("1")) {
                    printInventory();
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
                db.signUp(name, pw);
            } catch (UserDefinedException e) {
                e.printStackTrace();
            }
            System.out.println("Your register is successful!");
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

    }


}
