package bu.cs622.db;

import bu.cs622.UserDefinedException;
import bu.cs622.inventory.Inventory;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;


public class DatabaseTest {

    private IPersistence db;

    @BeforeEach
    void setUp() throws UserDefinedException {
        db= new Database();
    }
   // @Test
//    void updateInventoryTest() throws UserDefinedException {
//        for(int i = 0; i < 10; i++){
//            db.updateInventory(1,1);
//            db.updateInventory(1,-1);
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            List<Inventory> list = db.getInventoryList();
//            Assert.assertEquals(10, list.get(0).getNumber());
//            resetDB();
//        }
//    }

//    private void resetDB() throws UserDefinedException {
//        try{
//            File file = new File();
//            Gson gson = new Gson();
//                StringBuffer buffer = new StringBuffer();
//                Scanner reader = new Scanner(file);
//                while(reader.hasNextLine()){
//                    String oldLine = reader.nextLine();
//                    Inventory inv = gson.fromJson(oldLine, Inventory.class);
//                    if(inv != null && inv.getId() == 1) {
//                        inv.setNumber(10);
//                        String newLine = gson.toJson(inv);
//                        buffer.append(newLine);
//                    }else{
//                        buffer.append(oldLine);
//                    }
//                    buffer.append('\n');
//                }
//                reader.close();
//                FileWriter writer = new FileWriter("inventory_copy.txt");
//                writer.write(buffer.toString());
//                writer.close();
//
//        }catch (Exception e){
//            throw new UserDefinedException("File does not exist");
//        }
//    }
}
