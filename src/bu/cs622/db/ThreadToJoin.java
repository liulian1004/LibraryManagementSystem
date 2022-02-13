package bu.cs622.db;

public class ThreadToJoin extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println("one thread of modifying file creates");
    }
}
