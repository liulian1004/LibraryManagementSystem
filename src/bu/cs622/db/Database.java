package bu.cs622.db;

import bu.cs622.UserDefinedException;
import bu.cs622.inventory.*;
import bu.cs622.user.Admin;
import bu.cs622.user.People;
import bu.cs622.user.User;
import bu.cs622.user.UserType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database implements IPersistence {
    private List<Inventory> inventoryList;


    public Database() throws UserDefinedException {
        inventoryList = new ArrayList<>();
        readInventory();
    }

    private void readInventory() throws UserDefinedException {
        try{
            File file = new File("inventory.txt");
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                String[] cur = reader.nextLine().split(",");
                Inventory inv = null;
                if(cur.length >= 3){
                    String name = cur[0];
                    int number = Integer.valueOf(cur[1]);
                    Type t = Type.valueOf(cur[2]);

                    if(t == Type.BOOK){
                        inv = new Book(name, number,t);
                    }else if (t == Type.EBOOK){
                        inv = new Ebook(name, number,t);
                    }else {
                        inv = new Magazine(name, number, t);
                    }
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


    @Override
    public List<Inventory> getInventoryList() {
        return inventoryList;
    }

    @Override
    public void addInventory(Inventory inv) throws UserDefinedException {
        inventoryList.add(inv);
        updateFile(inv);
    }

    @Override
    public void signUp(String name, String pw) throws UserDefinedException {
        try{
            File file = new File("user.txt");
            FileWriter writer = new FileWriter(file,true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.newLine();
            bufferedWriter.append(name +","+pw);
            bufferedWriter.close();
        }catch (Exception e){
            throw new UserDefinedException("File does not exist");
        }
    }

    private void updateFile (Inventory inv) throws UserDefinedException{
        try{
            File file = new File("inventory.txt");
            FileWriter writer = new FileWriter(file,true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            String line = inv.getName()+","+inv.getNumber()+","+inv.getType().toString();
            bufferedWriter.newLine();
            bufferedWriter.append(line);
            bufferedWriter.close();
        }catch (Exception e){
            throw new UserDefinedException("File does not exist");
        }
    }


    @Override
    public boolean verify(String name, String pw, String txtFile) throws UserDefinedException {
        try{
            File file = new File(txtFile);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                String[] cur = reader.nextLine().split(",");
                if(name.equals(cur[0]) && pw.equals(cur[1])){
                    return true;
                }
            }
            reader.close();
            return false;
        }catch (Exception e){
            throw new UserDefinedException("File does not exist");
        }
    }

}
