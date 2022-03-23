package BlockWar.Logic;

public class BrickFaller implements Runnable {

    private boolean dropBrick = false;
    private Integer delay = Integer.MAX_VALUE;

    public boolean getDropBrick() {
        if(this.dropBrick) {
            this.dropBrick = false;
            return true;
        }
        return this.dropBrick;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(this.delay);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            this.dropBrick = true;
        }
    }
}
