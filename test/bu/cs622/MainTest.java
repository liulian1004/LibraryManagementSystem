package bu.cs622;

import bu.cs622.mock.MockDB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;


public class MainTest {

    private Main main;
    private MockDB spyDb;
    InputStream sysInBackup;


    @BeforeEach
    void setUp() {
        main = new Main();
        spyDb= new MockDB();
        sysInBackup= System.in;

    }

    @AfterEach
    void tearDown() {
        System.setIn(sysInBackup);
    }

    @Test
    void testAdminLoginSuccess() throws UserDefinedException {
        ByteArrayInputStream in = new ByteArrayInputStream("admin\n123\n".getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        boolean result = main.logIn("Admin","admin.txt", spyDb,reader);
        assertTrue(result);
    }

    @Test
    void testAdminLoginFail() throws UserDefinedException {
        ByteArrayInputStream in = new ByteArrayInputStream("wrong_admin\n123\n".getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        boolean result = main.logIn("Admin","admin.txt", spyDb,reader);
        assertFalse(result);
    }

    @Test
    void testUserLoginSuccess() throws UserDefinedException {
        ByteArrayInputStream in = new ByteArrayInputStream("user1\n123\n".getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        boolean result = main.logIn("User","user.txt", spyDb,reader);
        assertTrue(result);
    }

    @Test
    void testUserLoginFail() throws UserDefinedException {
        ByteArrayInputStream in = new ByteArrayInputStream("wrong_user1\n123\n".getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        boolean result = main.logIn("User","user.txt", spyDb,reader);
        assertFalse(result);
    }

    @Test
    void testUserRegisterSuccess() throws UserDefinedException {
        ByteArrayInputStream in = new ByteArrayInputStream("user2\n123\n4\n".getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        main.userRegister(reader,spyDb);
        assertEquals(2, spyDb.getUsers().size());
    }


}
