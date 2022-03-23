package BlockWar.Net;

import BlockWar.GUI.Screen_Start;
import BlockWar.Logic.BoardManager;
import BlockWar.Logic.GameLoop;
import BlockWar.Logic.Players;
import BlockWar.Logic.ScreenUtil;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{

    private  Screen_Start screenStart;
    public static Players myPlayer;
    public static int PORT;
    public static String IP;

    public Client(Screen_Start screenStart) throws UnknownHostException {

        try {
            this.screenStart = screenStart;
            Socket socket = new Socket(IP, PORT);
            PrintWriter toServer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            receiver = new ReceiveData();
            this.bufferMsg = new BufferMessage(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            sender = new SendData(toServer);
        } catch (ConnectException r){
            System.err.println("Server full.");
            System.exit(0);
        }catch (Exception e){
            System.err.println("Server not reach.");
            System.exit(0);
        }

    }

    private BufferMessage bufferMsg;
    public static ReceiveData receiver;
    public static SendData sender;
    public static boolean pause = false;

    @Override
    public void run() {

        BoardManager bm;
        Thread bufferMsgThread = new Thread(bufferMsg);
        bufferMsgThread.start();
        try {
            System.out.println("waiting for ID.");

            while (receiver.getIdPlayer() == 0) {
                receiver.receiveMsg(bufferMsg.pollMsg());
            }
            // SEND PLAYER TO SERVER

            myPlayer = new Players(receiver.getIdPlayer());

            sender.sendPlayerMsg(myPlayer);
            System.out.println("CREATED AND SENT PLAYER " + myPlayer.getId());

            // RICEVI LA LISTA DAL SERVER
            while (receiver.getPlayersList() == null) {
                receiver.receiveMsg(bufferMsg.pollMsg());
            }

            bm = new BoardManager();
            this.screenStart.getScreen().close();

            GameLoop loop = new GameLoop(bm);
            Thread glThread = new Thread(loop);
            glThread.start();

            // set trash target default
            if (receiver.getIdPlayer() != receiver.getPlayersList().size()) {
                myPlayer.setTrashTarget(myPlayer.getId() + 1);
            } else {
                myPlayer.setTrashTarget(1);
            }

            System.out.println("[DEFAULT TARGET]:" + myPlayer.getTrashTarget());

            while (!Thread.currentThread().isInterrupted()) {
                receiver.receiveMsg(bufferMsg.pollMsg());
                ScreenUtil.checkTargetValidity(myPlayer);
                bm.upDateBoard();
                checkCMD();
                if(GameWinner.isGameWin(receiver.getPlayersList()) && !myPlayer.isGameOver()){
                    myPlayer.setBoard(myPlayer.getDefaultWinnerBoard());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private boolean once = true;
    private boolean once2 = false;
    private void checkCMD(){
        if (receiver.getCmd().equals("pause")){
            if (!once2){
                System.err.println("[PAUSE REQUEST FROM SERVER]");
                pause = true;
                once2 = true;
                once = false;
            }
        }
        if (receiver.getCmd().equals("resume")){
            if (!once) {
                System.err.println("[RESUME REQUEST FROM SERVER]");
                pause = false;
                synchronized (ScreenUtil.lock){
                    ScreenUtil.lock.notifyAll();
                }
                once = true;
                once2 = false;
            }
        }
        if (receiver.getCmd().equals("close")){
            System.err.println("[CLOSE REQUEST FROM SERVER]");
            for (Thread t: Thread.getAllStackTraces().keySet()){
                if (t.getState() == Thread.State.RUNNABLE && !t.getName().equals("srThread")){
                    t.interrupt();
                }
            }
            System.exit(0);
        }

    }



}