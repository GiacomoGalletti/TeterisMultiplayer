package BlockWar.Logic;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControlGameLoop implements Runnable  {

    private static final int POLL_TIME = 1000/60;

    private TerminalScreen screen;
    private List<KeyStroke> keyStrokes;

    public ControlGameLoop(TerminalScreen screen) {
        this.screen = screen;
        this.keyStrokes = new ArrayList<>();
    }

    public List<KeyStroke> getKeyStrokes() {
        List<KeyStroke> tmp = new ArrayList<>();
        tmp.addAll(keyStrokes);
        this.keyStrokes.clear();
        return tmp;
    }

    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                KeyStroke keyStroke = screen.pollInput();
                if(keyStroke != null) {
                    this.keyStrokes.add(keyStroke);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(POLL_TIME);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            while (check){
                synchronized (ScreenUtil.lock) {
                    try {
                        System.out.println("ENTRATO WAIT");
                        ScreenUtil.lock.wait();
                        System.out.println("USCITO WAIT");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private boolean check = false;
    public void pause() {
        check = true;
    }

    public void resume() {
        check = false;
    }
}
