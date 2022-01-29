package bu.cs622.user;

import bu.cs622.db.IPersistence;
import bu.cs622.mock.MockDB;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.spy;

class UserTest {

    private User user;
    private IPersistence spyDb;
    @BeforeEach
    public void setUp() {
        spyDb= new MockDB();
        spyDb = spy(spyDb);
        user = new User("user","123", spyDb);
    }

    @Test
    void testGetInventory() {
        List<List<String>> inventories = user.getInventory();
        List<List<String>> expected = new ArrayList<>();
        expected.add(Arrays.asList("test book1", "BOOK"));
        Assert.assertEquals(expected, inventories);

    }
}