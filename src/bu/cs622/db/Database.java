package bu.cs622.db;

import bu.cs622.UserDefinedException;
import bu.cs622.inventory.*;
import bu.cs622.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database {
    private List<Inventory> inventoryList;
    private List<User> users;

    public Database() throws UserDefinedException {
        inventoryList = new ArrayList<>();
        users = new ArrayList<>();
        ReadFile();

    }
    private void ReadFile() throws UserDefinedException {
        try{
            File file = new File("inventory.txt");
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                String[] cur = reader.nextLine().split(",");
                String name = cur[0];
                int number = Integer.valueOf(cur[1]);
                Type t = Type.valueOf(cur[2]);
                Inventory inv;
                if(t == Type.BOOK){
                    inv = new Book(name, number,t);
                }else if (t == Type.EBOOK){
                    inv = new Ebook(name, number,t);
                }else {
                    inv = new Magazine(name, number, t);
                }

                if(inv != null) {
                    inventoryList.add(inv);
                }
            }
            reader.close();
        }catch (Exception e){
            throw new UserDefinedException("File does not exist");
        }
    }
    public List<Inventory> getInventoryList() {
        return inventoryList;
    }

    public void addInventory(Inventory inv) throws UserDefinedException {
        inventoryList.add(inv);
        updateFile();
    }

    private void updateFile () throws UserDefinedException{
        try{
            File file = new File("inventory.txt");
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            for(Inventory inv: inventoryList){
                String line = inv.getName()+","+inv.getNumber()+","+inv.getType().toString();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }catch (Exception e){
            throw new UserDefinedException("File does not exist");
        }
    }


    public List<User> getUsers() {
        return users;
    }

    public void setInventoryList(List<Inventory> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }



}
