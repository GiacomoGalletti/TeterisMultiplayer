package BlockWar.Net;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerUtil {

    public static Semaphore createdPlayer = new Semaphore(0);
    public static final Lock lockClientHandlerVsServerRutine = new ReentrantLock();

    public static Semaphore s = new Semaphore(0);
    public static Semaphore t = new Semaphore(1);
    public static boolean cmdRestart = true;
    static public void startCountDown(CountDown countDown) {
        Thread countThread = new Thread(countDown);
        countThread.start();
    }
}
