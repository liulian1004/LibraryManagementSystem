package bu.cs622.db;

import bu.cs622.UserDefinedException;
import bu.cs622.inventory.*;
import bu.cs622.user.Admin;
import bu.cs622.user.People;
import bu.cs622.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Database implements IPersistence {

    //private Gson gson;
    //private File inventoryFile;
    private Connection con;
    private final String  url = "jdbc:sqlite:Library.db";

    public Database(String fileName) throws UserDefinedException {
        connectDB();
        //gson = new Gson();
        //inventoryFile = new File(fileName);;
    }

    private List<Inventory> readInventory() throws UserDefinedException {
        List<Inventory> inventoryList = new ArrayList<>();
        String sql = "SELECT * FROM INVENTORY ORDER BY ID";
        try {
            ResultSet results = con.createStatement().executeQuery(sql);
            while(results.next()){
                Inventory inv = new Inventory(results.getInt(1),results.getString(2), results.getInt(3), InventoryType.valueOf(results.getString(4)));
                inventoryList.add(inv);;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDefinedException("Read inventory from database is failure");
        }
        return inventoryList;
    }


    @Override
    public List<Inventory> getInventoryList() throws UserDefinedException {
        return readInventory();
    }

    @Override
    public void addInventory(String name, String number, String type) throws UserDefinedException {
        type = type.toUpperCase();
        try{
            InventoryType.valueOf(type);
        } catch(IllegalArgumentException ex){
            throw new UserDefinedException("Type is not correct");
        }
        String sql = "INSERT INTO INVENTORY (NAME, NUMBER, TYPE) VALUES ('" + name + "', '" + number + "', '" + type + "')";
        try {
            con.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDefinedException("Add new user into database is failure");
        }
    }

    @Override
    public void signUp(User newUser) throws UserDefinedException {
        String sql = "INSERT INTO USER (NAME, PASSWORD) VALUES ('" + newUser.getUserName() + "', '" + newUser.getPassword() +"')";
        try {
            con.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDefinedException("Add new user into database is failure");
        }
    }


    @Override
    public boolean verify(String name, String pw, String table) throws UserDefinedException {
        try {
            String sql = "SELECT NAME, PASSWORD FROM " + table;
            ResultSet results = con.createStatement().executeQuery(sql);
            while(results.next()){
                String userName = results.getString(1);
                String password = results.getString(2);
                if(name.equals(userName) && pw.equals(password)){
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDefinedException("Create statement is failure");
        }

    }


    @Override
    public void updateInventory(int id, int number,String userName) throws UserDefinedException {
        ThreadToJoin thread = new ThreadToJoin();
        try{
            thread.start();
            thread.join();
            updateFile(id, number, userName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateFile(int id, int number, String userName) throws UserDefinedException {
        synchronized (this.con) {
            String sql = "SELECT NUMBER FROM INVENTORY WHERE ID = " + id ;
            try {
                ResultSet results = con.createStatement().executeQuery(sql);
                int cur = -1;
                while(results.next()){
                    cur = results.getInt(1);
                }
                if(cur == -1){
                    throw new UserDefinedException("The ID does not exist");
                }
                int updateNumber = cur + number;
                String update = "UPDATE INVENTORY SET NUMBER = '" + updateNumber + "' WHERE ID = " + id;
                con.createStatement().executeUpdate(update);
                if(number > 0){
                    removeBookID(id, userName);
                }else{
                    addBookID(id, userName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new UserDefinedException("Create statement is failure");
            }
        }

    }

    private void removeBookID(int id, String userName) throws UserDefinedException {
        String sql = "DELETE FROM BOOKED WHERE BOOK_ID=" + "'" + id + "' AND USER_NAME='" + userName +"'";
        try {
            con.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDefinedException("Add new user into database is failure");
        }
    }

    private void addBookID(int id, String userName) throws UserDefinedException {
        String sql = "INSERT INTO BOOKED (BOOK_ID, USER_NAME) VALUES ('" + id + "', '" + userName +"')";
        try {
            con.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDefinedException("Add new user into database is failure");
        }
    }
    @Override
    public void disconnectDB() throws UserDefinedException {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDefinedException("Close the database is failure");
        }
    }

    @Override
    public int getSum() throws UserDefinedException {
        String sql = "SELECT SUM (NUMBER) FROM INVENTORY" ;
        try {
            ResultSet results = con.createStatement().executeQuery(sql);
            int cur = 0;
            while(results.next()){
                cur += results.getInt(1);
            }
            return cur;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDefinedException("Create statement is failure");
        }
    }


    @Override
    public List<String> borrowedBookLists(String userName) throws UserDefinedException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT INVENTORY.NAME FROM INVENTORY INNER JOIN BOOKED ON INVENTORY.ID = BOOKED.BOOK_ID WHERE BOOKED.USER_NAME = '" + userName +"'";
        try {
            ResultSet results = con.createStatement().executeQuery(sql);
            while(results.next()){
                list.add(results.getString(1));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDefinedException("Create statement is failure");
        }
    }

    private void connectDB() throws UserDefinedException {
        try {
            con = DriverManager.getConnection(url);
            System.out.println("connect DB successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDefinedException("Connection Database is failure");
        }
        createTable();

    }
    private void createTable() throws UserDefinedException {
        try {
            String createTableOne = "CREATE TABLE IF NOT EXISTS INVENTORY (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(255), NUMBER INTEGER, TYPE VARCHAR(255))";
            con.createStatement().executeUpdate(createTableOne);
            String createTableTwo = "CREATE TABLE IF NOT EXISTS ADMIN (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(255), PASSWORD VARCHAR(255))";
            con.createStatement().executeUpdate(createTableTwo);
            String createTableThree = "CREATE TABLE IF NOT EXISTS USER (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME VARCHAR(255), PASSWORD VARCHAR(255))";
            con.createStatement().executeUpdate(createTableThree);
            String createTableFour = "CREATE TABLE IF NOT EXISTS BOOKED (ID INTEGER PRIMARY KEY AUTOINCREMENT, BOOK_ID INTEGER, USER_NAME VARCHAR(255))";
            con.createStatement().executeUpdate(createTableFour);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDefinedException("Get data from Database is failure");
        }
    }

//    @Override
//    public boolean verify(String name, String pw, String txtFile) throws UserDefinedException {
//        try{
//            File file = new File(txtFile);
//            Scanner reader = new Scanner(file);
//            while(reader.hasNextLine()){
//                People user = null;
//                if(txtFile.equals("user.txt")) {
//                    user = gson.fromJson(reader.nextLine(),User.class);
//                } else{
//                    user = gson.fromJson(reader.nextLine(), Admin.class);
//                }
//                if(name.equals(user.getUserName()) && pw.equals(user.getPassword())){
//                    return true;
//                }
//            }
//            reader.close();
//            return false;
//        }catch (Exception e){
//            throw new UserDefinedException("File does not exist");
//        }
//    }

//    @Override
//    public void signUp(User newUser) throws UserDefinedException {
//        try{
//            File userFile = new File("user.txt");
//            FileWriter writer = new FileWriter(userFile,true);
//            BufferedWriter bufferedWriter = new BufferedWriter(writer);
//            bufferedWriter.newLine();
//            Gson gsonIgnore = new GsonBuilder()
//                    .excludeFieldsWithoutExposeAnnotation()
//                    .create();
//            bufferedWriter.append(gsonIgnore.toJson(newUser));
//            bufferedWriter.close();
//        }catch (Exception e){
//            throw new UserDefinedException("File does not exist");
//        }
//    }

//    private List<Inventory> readInventory() throws UserDefinedException {
//        List<Inventory> inventoryList = new ArrayList<>();
//        try{
//            Scanner reader = new Scanner(inventoryFile);
//            while(reader.hasNextLine()){
//                Inventory inv = gson.fromJson(reader.nextLine(), Inventory.class);
//                if(inv != null) {
//                    inventoryList.add(inv);
//                }
//            }
//            reader.close();
//        }catch (Exception e){
//            throw new UserDefinedException("File does not exist");
//        }
//        return inventoryList;
//    }

//    private void addFile (Inventory inv) throws UserDefinedException{
//        try{
//            FileWriter writer = new FileWriter(inventoryFile,true);
//            BufferedWriter bufferedWriter = new BufferedWriter(writer);
//            String line = gson.toJson(inv);
//            bufferedWriter.newLine();
//            bufferedWriter.append(line);
//            bufferedWriter.close();
//        }catch (Exception e){
//            throw new UserDefinedException("File does not exist");
//        }
//    }

//    private void updateFile(int id, int number) throws UserDefinedException {
//        try{
//            synchronized (this.inventoryFile){
//                StringBuffer buffer = new StringBuffer();
//                Scanner reader = new Scanner(inventoryFile);
//                boolean findID =false;
//                while(reader.hasNextLine()){
//                    String oldLine = reader.nextLine();
//                    Inventory inv = gson.fromJson(oldLine, Inventory.class);
//                    if(inv != null && inv.getId() == id) {
//                        if(inv.getNumber() <= 0){
//                            throw new UserDefinedException(String.format("%s is not available", inv.getName()));
//                        }
//                        inv.setNumber(inv.getNumber()+number);
//                        String newLine = gson.toJson(inv);
//                        buffer.append(newLine);
//                        findID = true;
//                    }else{
//                        buffer.append(oldLine);
//                    }
//                    buffer.append('\n');
//                }
//                reader.close();
//                if(!findID){
//                    throw new UserDefinedException(String.format("%s is not available", id));
//                }
//                FileWriter writer = new FileWriter("inventory.txt");
//                writer.write(buffer.toString());
//                writer.close();
//            }
//
//        }catch (Exception e){
//            throw new UserDefinedException("File does not exist");
//        }
//    }
}
