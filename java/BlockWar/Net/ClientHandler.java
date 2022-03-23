package BlockWar.Net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    public static int id = 0;

    public SendData sender;
    public ReceiveData receiver;
    public final BufferMessage msgBuffer;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.msgBuffer = new BufferMessage(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
        this.sender = new SendData(new PrintWriter(clientSocket.getOutputStream(), true));
        this.receiver = new ReceiveData();
    }

    @Override
    public void  run(){
        Thread msgBufferThread = new Thread(msgBuffer);
        msgBufferThread.start();
        try {
            sender.sendIdPlayerMsgOrTlMsg(assignId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (receiver.getPlayer() == null) {
            receiver.receiveMsg(msgBuffer.pollMsg());
        }

        ServerUtil.createdPlayer.release(1);

        while (!Thread.currentThread().isInterrupted()) {
            synchronized (ServerUtil.lockClientHandlerVsServerRutine){
                receiver.receiveMsg(msgBuffer.pollMsg()); // continua ad accettare il player
            }
        }

    }


    public int assignId() {
        return ++id;
    }
}