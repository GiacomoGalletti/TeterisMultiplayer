package BlockWar.Net;

import BlockWar.Logic.Players;

import java.util.ArrayList;

public class GameWinner {

    private static int looserCounter = 0;
    static public boolean isGameWin(ArrayList<Players> players){
        for (Players p: players) {
            if (p.isGameOver()) {
                looserCounter++;
            }
        }
        if (looserCounter == players.size()-1) {
            looserCounter = 0;
            return true;
        }
        looserCounter = 0;
        return false;
    }
}
