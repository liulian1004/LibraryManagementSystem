package bu.cs622.user;

import bu.cs622.UserDefinedException;
import bu.cs622.db.Database;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static bu.cs622.Main.connectDB;
import static bu.cs622.Main.db;


class AdminTest {

    private Admin admin;

    @BeforeEach
    void setUp() {
        connectDB();
        admin = new Admin("admin","123");
    }

    @AfterEach
    void teardown(){
        restDB();
    }

    @Test
    void testGetInventory() {
        List<List<String>> inventories = admin.getInventory();
        List<List<String>> expected = new ArrayList<>();
        expected.add(Arrays.asList("Little Prince", "BOOK","5"));
        expected.add(Arrays.asList("Fashion World", "MAGAZINE","5"));
        expected.add(Arrays.asList("Clean code", "EBOOK","5"));
        expected.add(Arrays.asList("Effective Java", "BOOK","0"));
        Assert.assertEquals(expected, inventories);
    }

    @Test
    void testAddInventory() throws UserDefinedException {
        admin.addInventory("test book","5","EBOOK");
        Assert.assertEquals(5, db.getInventoryList().size());
        List<List<String>> inventories = admin.getInventory();
        List<List<String>> expected = new ArrayList<>();
        expected.add(Arrays.asList("Little Prince", "BOOK","5"));
        expected.add(Arrays.asList("Fashion World", "MAGAZINE","5"));
        expected.add(Arrays.asList("Clean code", "EBOOK","5"));
        expected.add(Arrays.asList("Effective Java", "BOOK","0"));
        expected.add(Arrays.asList("test book", "EBOOK","5"));
        Assert.assertEquals(expected, inventories);
    }

    private void restDB(){

    }
}