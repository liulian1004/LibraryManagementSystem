package bu.cs622;

import bu.cs622.db.IPersistence;
import bu.cs622.mock.MockDB;
import bu.cs622.user.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.spy;

public class MainTest {

    private Main main;
    private IPersistence spyDb;

    @BeforeEach
    void setUp() {
        main = new Main();

        spyDb= new MockDB();

    }



    @Test
    void testAdminLogin() {

    }
}
