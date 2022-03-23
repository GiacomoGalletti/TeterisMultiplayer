package BlockWar.Net;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import BlockWar.Logic.Players;

import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SendData {

    private String msg;      // messaggio con id

    private final Type type = new TypeToken<ArrayList<Players>>() {}.getType();
    private final Gson gson = new Gson();
    private final PrintWriter out;

    public SendData(PrintWriter out) {
        this.out = out;
    }

    public synchronized void sendCmdMsg(String cmd) {
        msg = "1" + cmd;
        out.println(msg);
        out.flush();
    }

    public synchronized void sendIdPlayerMsgOrTlMsg(int n) throws Exception {
        if (String.valueOf(n).length() == 1) {
            msg = "2" + n;
        } else if (String.valueOf(n).length() == 3) {
            msg = "3" + n;
        } else {
            throw new Exception("STRANGER MESSAGE.");
        }
        out.println(msg);
        out.flush();
    }

    public synchronized void sendPlayerMsg(Players player) {
        msg = "4" + gson.toJson(player, Players.class);
        out.println(msg);
        out.flush();
    }

    public synchronized void sendPlayersListMsg(ArrayList<Players> playersList) {
        msg = "5" + gson.toJson(playersList, type);
        //System.out.println(msg);
        out.println(msg);
        out.flush();
    }

    public synchronized void sendTlToElaborate(int n) {
        msg = "6" + n;
        //System.out.println(msg);
        out.println(msg);
        out.flush();
    }

}
