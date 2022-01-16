package bu.cs622.user;

public class Admin extends People {

    // Admin can't register by system
    private Admin(String userName, String password) {
        super(userName, password);
    }


}
