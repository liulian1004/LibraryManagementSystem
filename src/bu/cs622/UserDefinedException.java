package bu.cs622;

public class UserDefinedException extends Exception {
        private String message;

        public UserDefinedException(String message) {
            super(message);
            this.message = message;
        }

    public void printErrorMessage(){
            System.out.println(message);
        }
}
