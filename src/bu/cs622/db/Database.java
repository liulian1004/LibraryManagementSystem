package bu.cs622.db;

import bu.cs622.UserDefinedException;
import bu.cs622.inventory.*;
import bu.cs622.user.Admin;
import bu.cs622.user.People;
import bu.cs622.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database implements IPersistence {

    private Gson gson;
    public Database() throws UserDefinedException {
        gson = new Gson();
    }

    private List<Inventory> readInventory() throws UserDefinedException {
        List<Inventory> inventoryList = new ArrayList<>();
        try{
            File file = new File("inventory.txt");
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                Inventory inv = gson.fromJson(reader.nextLine(), Inventory.class);
                if(inv != null) {
                    inventoryList.add(inv);
                }
            }
            reader.close();
        }catch (Exception e){
            throw new UserDefinedException("File does not exist");
        }
        return inventoryList;
    }


    @Override
    public List<Inventory> getInventoryList() throws UserDefinedException {
        return readInventory();
    }

    @Override
    public void addInventory(Inventory inv) throws UserDefinedException {
        addFile(inv);
    }

    @Override
    public void signUp(User newUser) throws UserDefinedException {
        try{
            File file = new File("user.txt");
            FileWriter writer = new FileWriter(file,true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.newLine();
            Gson gsonIgnore = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            bufferedWriter.append(gsonIgnore.toJson(newUser));
            bufferedWriter.close();
        }catch (Exception e){
            throw new UserDefinedException("File does not exist");
        }
    }

    private void addFile (Inventory inv) throws UserDefinedException{
        try{
            File file = new File("inventory.txt");
            FileWriter writer = new FileWriter(file,true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            String line = gson.toJson(inv);
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
                People user = null;
                if(txtFile.equals("user.txt")) {
                    user = gson.fromJson(reader.nextLine(),User.class);
                } else{
                    user = gson.fromJson(reader.nextLine(), Admin.class);
                }
                if(name.equals(user.getUserName()) && pw.equals(user.getPassword())){
                    return true;
                }
            }
            reader.close();
            return false;
        }catch (Exception e){
            throw new UserDefinedException("File does not exist");
        }
    }

    @Override
    public int getCurId() throws UserDefinedException {
        return readInventory().size();
    }

    @Override
    public void updateInventory(int id) throws UserDefinedException {
        updateFile(id);
    }


    private void updateFile(int id) throws UserDefinedException {
        try{
            File file = new File("inventory.txt");
            StringBuffer buffer = new StringBuffer();
            Scanner reader = new Scanner(file);
            boolean findID =false;
            while(reader.hasNextLine()){
                String oldLine = reader.nextLine();
                Inventory inv = gson.fromJson(oldLine, Inventory.class);
                if(inv != null && inv.getId() == id) {
                    if(inv.getNumber() <= 0){
                        throw new UserDefinedException(String.format("%s is not available", inv.getName()));
                    }
                    inv.setNumber(inv.getNumber()-1);
                    String newLine = gson.toJson(inv);
                    buffer.append(newLine);
                    findID = true;
                }else{
                    buffer.append(oldLine);
                }
                buffer.append('\n');
            }
            reader.close();
            if(!findID){
                throw new UserDefinedException(String.format("%s is not available", id));
            }
            FileWriter writer = new FileWriter("inventory.txt");
            writer.write(buffer.toString());
            writer.close();
        }catch (Exception e){
            throw new UserDefinedException("File does not exist");
        }
    }


}
