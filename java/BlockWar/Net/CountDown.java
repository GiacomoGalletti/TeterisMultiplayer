package BlockWar.Net;

public class CountDown implements Runnable{

    private final int delay = 1000;

    public static void setInterval(int interval) {
        CountDown.interval = interval;
    }

    private static int interval;

    public CountDown(int interval) {
        this.interval = interval;
    }

    public static int getInterval(){return interval;}

    @Override
    public void run() {
        while (interval > 0) {
            try {
                Thread.sleep(delay);
                if (interval % 5 == 0){
                System.out.println("count down: " + interval);
                }
                --interval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}