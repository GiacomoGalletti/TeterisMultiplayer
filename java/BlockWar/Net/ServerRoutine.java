package BlockWar.Net;

import BlockWar.Logic.Players;

import java.io.IOException;
import java.util.ArrayList;

public class ServerRoutine implements Runnable{
    public static int PORT;
    public static boolean onetimeSocket = true;
    private ArrayList<ClientHandler> clientsHandlers = new ArrayList<>();
    private final CountDown countDown = new CountDown(30);
    public static boolean notStepOver = true;
    private boolean oneTime = false;


    @Override
    public void run() {
        CmdServer cmdS = new CmdServer();
        Thread cmdThread = new Thread(cmdS);
        cmdThread.start();

        System.out.println("Waiting For Connections...");
        try {
            while (notStepOver){
                if (CountDown.getInterval() <= 0){
                    System.err.println("Starting.");
                    notStepOver = false;
                    for (ClientHandler ch: clientsHandlers) {
                        Thread chThread = new Thread(ch);
                        chThread.start();
                    }
                }else {
                    ServerUtil.t.acquire(1);

                    cmdS.checkCmd(cmdS.getCmd(),clientsHandlers);

                    SocketConnection sc = new SocketConnection(clientsHandlers);
                    Thread scThread = new Thread(sc);
                    scThread.start();
                    clientsHandlers = sc.getClientsHandlers();

                    if (clientsHandlers.size() > 1 && !oneTime){
                        oneTime = true;
                        ServerUtil.startCountDown(countDown);
                    }
                    ServerUtil.s.release(1);
                }
            }

            ArrayList<Players> playersList = new ArrayList<>(clientsHandlers.size());

            ServerUtil.createdPlayer.acquire(clientsHandlers.size());
            //int a = 0;
            synchronized (ServerUtil.lockClientHandlerVsServerRutine) {
                for (int i = 0; i<clientsHandlers.size(); i++) {
                    playersList.add(i, clientsHandlers.get(i).receiver.getPlayer());
                }
            }

            int target;
            int trashTemp;
            String command;


            while (!GameWinner.isGameWin(playersList)){
                synchronized (ServerUtil.lockClientHandlerVsServerRutine) {
                    for (ClientHandler ch : clientsHandlers) {
                        command = cmdS.getCmd();

                        cmdS.checkCmd(command, clientsHandlers);

                        playersList.set(ch.receiver.getPlayer().getId() - 1, ch.receiver.getPlayer());
                        trashTemp = ch.receiver.getTlToSend();
                        if (trashTemp != 0) {
                            target = ch.receiver.getTarget();
                            System.err.println("Linee Spazzatura DA MANDARE: " + trashTemp +
                                    " AL PLAYER: " + target + " DAL PLAYER: " + ch.receiver.getIdSender());
                            clientsHandlers.get(target-1).sender.sendTlToElaborate(trashTemp);
                        }
                    }

                    for (ClientHandler ch : clientsHandlers) {
                        ch.sender.sendPlayersListMsg(playersList);
                    }
                }
            }


            System.err.println("END GAME. Need to close by server command.");

            while (!Thread.currentThread().isInterrupted()) {
                command = cmdS.getCmd();

                for (ClientHandler ch : clientsHandlers) {
                    playersList.set(ch.receiver.getPlayer().getId() - 1, ch.receiver.getPlayer());
                }

                for (ClientHandler ch : clientsHandlers) {
                    ch.sender.sendPlayersListMsg(playersList);
                }

                if (command.equals("close")) {
                    for (ClientHandler ch : clientsHandlers) {
                        ch.sender.sendCmdMsg(command);
                    }

                    for (Thread t : Thread.getAllStackTraces().keySet()){
                        if (t.getState() == Thread.State.RUNNABLE){
                            t.interrupt();
                        }
                    }
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
