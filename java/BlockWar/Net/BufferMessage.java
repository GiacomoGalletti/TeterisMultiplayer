package BlockWar.Net;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class BufferMessage implements Runnable{

    public BufferMessage(BufferedReader in) {
        this.msgQueue = new LinkedList<>();
        this.in = in;
    }

    public Queue<String> getMsgQueue() {
        return msgQueue;
    }

    public boolean isEmpty(){
        return this.msgQueue.isEmpty();
    }

    @Override
    public synchronized String toString() {
      StringBuilder out = new StringBuilder();
        for (String s:
             msgQueue) {
            out.append("\n").append(s);
        }
        return out.toString();
    }

    private final Queue<String> msgQueue;

    private final BufferedReader in;

    public String pollMsg(){
        String msg = null;
        synchronized (bufferLock){
            if (!msgQueue.isEmpty()) {
                msg = msgQueue.poll();
            }
        }
        return Objects.requireNonNullElse(msg, "NnoMessages");
    }

    private final Object bufferLock = new Object();

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){

            try {
                try {
                    String msg = in.readLine();
                    synchronized (bufferLock){
                        msgQueue.add(msg);
                        //System.out.println("RICEVUTO: " + msg);
                    }
                }catch (SocketException e){
                    System.err.println("Buffer closed.");
                    System.exit(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
