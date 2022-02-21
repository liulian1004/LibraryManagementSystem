package bu.cs622.user;

import bu.cs622.UserDefinedException;
import bu.cs622.db.IPersistence;
import bu.cs622.mock.MockDB;
import org.junit.Assert;
import org.junit.jupiter.api.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;

class AdminTest {

    private Admin admin;
    private IPersistence spyDb;


    @BeforeEach
    void setUp() {
        spyDb= new MockDB();
        spyDb = spy(spyDb);
        admin = new Admin("admin","123", spyDb, PeopleType.ADMIN);
    }

    @Test
    void testGetInventory() throws UserDefinedException {
        List<List<String>> inventories = admin.getInventory();
        List<List<String>> expected = new ArrayList<>();
        expected.add(Arrays.asList("1","test book1", "BOOK","5"));
        expected.add(Arrays.asList("2","test book2", "BOOK","0"));
        Assert.assertEquals(expected, inventories);
    }

    @Test
    void testGetSum() throws UserDefinedException {
        String expected = "There are 5 books in the inventory";
        Assert.assertEquals(expected, admin.getSum());
    }

}