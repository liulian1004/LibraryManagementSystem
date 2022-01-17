package bu.cs622.user;

import bu.cs622.db.Database;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static bu.cs622.Main.db;
import static bu.cs622.Main.initDB;
import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private Admin admin;

    @BeforeEach
    public void setUp() {
        initDB();
        admin = new Admin("admin","123");
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void getInventory() {
        admin.getInventory();
        String expected = "============Inventory List for Admin=============\n" +
                "Name                  Type                  Number                \n" +
                "Little Prince         Book                  5                     \n" +
                "Fashion World         Magazine              5                     \n" +
                "Clean code            Ebook                 5                     \n" +
                "Effective Java        Book                  0";
        Assert.assertEquals(expected, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void addInventory() {
        admin.addInventory("test book","5","Ebook");
        Assert.assertEquals(5, db.getInventoryList().size());
        String expected = "============Inventory List for Admin=============\n" +
                "Name                  Type                  Number                \n" +
                "Little Prince         Book                  5                     \n" +
                "Fashion World         Magazine              5                     \n" +
                "Clean code            Ebook                 5                     \n" +
                "Effective Java        Book                  0                     \n" +
                "test book             Ebook                 5";
        Assert.assertEquals(expected, outputStreamCaptor.toString()
                .trim());
    }
}