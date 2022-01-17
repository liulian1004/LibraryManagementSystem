package bu.cs622.user;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static bu.cs622.Main.initDB;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private User user;

    @BeforeEach
    public void setUp() {
        initDB();
        user = new User("user","123");
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void getInventory() {
        user.getInventory();
        String expected = "=========Inventory List for User==========\n" +
                "Name                  Type                  \n" +
                "Little Prince         Book                  \n" +
                "Fashion World         Magazine              \n" +
                "Clean code            Ebook";
        Assert.assertEquals(expected,outputStreamCaptor.toString()
                .trim());
    }
}