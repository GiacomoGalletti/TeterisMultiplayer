package BlockWar.Net;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import BlockWar.Logic.Players;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ReceiveData {

    private volatile String cmd = "";
    private volatile int idPlayer;
    private volatile int idTarget;
    private volatile int idSender;
    private volatile int tlToSend;
    private volatile int tlToElaborate;
    private volatile Players player;
    private volatile ArrayList<Players> playersList;

    private final Type type = new TypeToken<ArrayList<Players>>() {}.getType();
    private final Gson gson = new Gson();

    public int getIdSender() {
        return idSender;
    }

    public int getTlToElaborate() {
        int tmp = tlToElaborate;
        tlToElaborate = 0;
        return tmp;
    }

    public int getTlToSend() {
        int tmp = tlToSend;
        tlToSend = 0;
        return tmp;
    }
    public String getCmd() {
        return cmd;
    }
    public int getIdPlayer() {
        return idPlayer;
    }
    public int getTarget() {
        return idTarget;
    }
    public Players getPlayer() {
        return player;
    }
    public ArrayList<Players> getPlayersList() {
        return playersList;
    }

    public void receiveMsg(String msgReceived) {
        char msgId = msgReceived.charAt(0);
        String msg = msgReceived.substring(1);

        if (msgId == '1') {
            this.cmd = msg;
        }
        if (msgId == '2') {
            this.idPlayer = Integer.parseInt(msg);
        }
        if (msgId == '3') {
            this.idTarget = Character.getNumericValue(msg.charAt(0));
            this.idSender = Character.getNumericValue(msg.charAt(1));
            this.tlToSend = Character.getNumericValue(msg.charAt(2));
        }
        if (msgId == '4') {
            this.player = this.gson.fromJson(msg, Players.class);
        }
        if (msgId == '5') {
            this.playersList = this.gson.fromJson(msg, type);
        }
        if (msgId == '6') {
            this.tlToElaborate = Integer.parseInt(msg);
        }
    }

    public String toString() {
        String string = "";

        for (int i = 0; i < player.getBoard().length; i++){
            for (int j = 0; j < player.getBoard()[0].length; j++){
                System.out.print(player.getBoard()[i][j]);
            }
            System.out.println();
        }
        return string;
    }
}
