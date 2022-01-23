package bu.cs622;

import bu.cs622.db.Database;
import bu.cs622.user.Admin;
import bu.cs622.user.People;
import bu.cs622.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static Database db;
    public static People people;
    public static void main(String[] args) {

	    // main menu
        while(true){
            System.out.println("Welcome to use Library Management System ");
            System.out.println("Please type 1 for user login, type 2 for admin login:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String input = reader.readLine();
                if(input.equals("1")){
                    userLogin();
                }else if(input .equals("2")){
                    adminLogin();
                }else{
                    System.out.println("Your input is not correct, please try again");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void adminLogin() {
        connectDB();
        people = new Admin("Admin","123");
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

    private static void addInventory() {
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
                    ((Admin)people).addInventory(name,number,type);
                    System.out.println("New inventory update success!");
                    quitSystem();
                } catch (UserDefinedException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("Your input is not correct, please try again");
            }
        }

    }

    private static void userLogin() {
        connectDB();
        people = new  User("User1","123");
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

    public static void connectDB() {
        try {
            db = new Database();
        } catch (UserDefinedException e) {
            e.printErrorMessage();
            e.printStackTrace();
        }
    }

    private static void printInventory(){
        List<List<String>> inventories = null;
        if(people instanceof User){
            inventories = ((User)people).getInventory();
        }else if(people instanceof Admin){
            inventories = ((Admin)people).getInventory();
        }
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

    private static void quitSystem(){
        System.out.println("Thank you for using this system, GoodBye");
        System.exit(1);
    }



}
