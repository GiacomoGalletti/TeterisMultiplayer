package BlockWar.Net;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CmdServer implements Runnable{

    private final Scanner scan = new Scanner(System.in);

    public String getCmd() {
        String tmp = this.cmd;
        this.cmd = "";
        return tmp;
    }

    private String cmd = "";

    public void checkCmd(String command, ArrayList<ClientHandler> clientsHandlerList) throws IOException {
        if (!command.equals("")) {
            for (ClientHandler ch1 : clientsHandlerList) {
                ch1.sender.sendCmdMsg(command);
            }
            if (command.equals("close")){
                System.err.println("[CLOSE REQUEST]");
                for (Thread t : Thread.getAllStackTraces().keySet()){
                    if (t.getState() == Thread.State.RUNNABLE){
                        t.interrupt();
                    }
                }
                System.exit(0);
            }

            if (command.equals("start") && CountDown.getInterval() != 0){
                System.err.println("[START REQUEST]");
                for (Thread t : Thread.getAllStackTraces().keySet()){
                    if (t.getName().equals("countThread")){
                        t.interrupt();
                    }
                }
                CountDown.setInterval(0);
            }

            if (command.equals("restart")){
                System.err.println("[RESTART REQUEST]");
                ServerUtil.cmdRestart = !ServerUtil.cmdRestart;

                for (ClientHandler ch1 : clientsHandlerList) {
                    ch1.sender.sendCmdMsg("close");
                }

                //TODO: SISTEMARE LA DIRECTORY CORRETTA
                String basePath = new File("").getAbsolutePath();
                String path = basePath + "/out/artifacts/BlockWarServer_jar/Server.jar ";
                Process processNew = Runtime.getRuntime().exec("java -jar " + path + ServerRoutine.PORT );

                for (Thread t : Thread.getAllStackTraces().keySet()){
                    if (t.getState() == Thread.State.RUNNABLE){
                        t.interrupt();
                    }
                }

            }
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            System.out.println();
            String input = scan.nextLine();

            switch (input){
                default -> {
                    System.err.println("unknown command.");
                }
                case "pause", "resume", "restart", "close", "start" -> {
                    this.cmd = input;
                }
            }
        }
    }

}
