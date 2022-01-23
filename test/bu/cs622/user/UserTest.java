package bu.cs622.user;

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

class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        connectDB();
        user = new User("user","123");
    }

    @Test
    void testGetInventory() {
        List<List<String>> inventories = user.getInventory();
        List<List<String>> expected = new ArrayList<>();
        expected.add(Arrays.asList("Little Prince", "BOOK"));
        expected.add(Arrays.asList("Fashion World", "MAGAZINE"));
        expected.add(Arrays.asList("Clean code", "EBOOK"));
        Assert.assertEquals(expected, inventories);

    }
}