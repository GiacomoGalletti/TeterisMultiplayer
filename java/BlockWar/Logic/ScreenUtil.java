package BlockWar.Logic;

import BlockWar.Net.Client;
import BlockWar.Net.ReceiveData;

import java.util.concurrent.Semaphore;

public class ScreenUtil {

    public static void checkTargetValidity(Players p){
        int idMyTarget = p.getTrashTarget();
        Players targetPlayer = Client.receiver.getPlayersList().get(idMyTarget-1);
        if (targetPlayer.isGameOver()){
           if (p.getId() == Client.receiver.getPlayersList().size()) {
               for (int i = p.getId()-1; i>0; i--){
                   if (!Client.receiver.getPlayersList().get(i-1).isGameOver()){
                       p.setTrashTarget(i);
                       System.out.println("[SET NEW TARGET]" + p.getTrashTarget());
                       break;
                   }
               }
           } else {
               for (int i = 1; i<Client.receiver.getPlayersList().size(); i++){
                   if (!Client.receiver.getPlayersList().get(i-1).isGameOver() && i != p.getId()){
                       p.setTrashTarget(i);
                       System.out.println("[SET NEW TARGET]" + p.getTrashTarget());
                       break;
                   }
               }
           }
        }
    }

    public static void checkCompleteLines(Players p, BoardManager bm) throws Exception {
        int lineCompleted = 0;
        boolean lineFilled = true;
        for (int r = 23; r >= 0; r--) {
            for (int c = 1; c < 13; c++) {
                if (p.getPoint(r, c) == 0 || p.getPoint(r, c) == 10) {
                    lineFilled = false;
                    break;
                }
                lineFilled = true;
            }
            if (lineFilled) {
                ++lineCompleted;
                clearLine(p,r);
                shiftDown(p,r);
                r++;
            }
        }

        switch (lineCompleted) {
            default -> {
                System.out.println("Generated 4 trash Line");
                String msgTrashLine = p.getTrashTarget() + Integer.toString(p.getId()) + "4";
                System.err.println("Message sent: " + msgTrashLine);
                Client.sender.sendIdPlayerMsgOrTlMsg(Integer.parseInt(msgTrashLine));
            }
            case 0,1 -> {
                System.out.println("Generated 0 trash Line");
            }
            case 2 -> {
                System.out.println("Generated 1 trash Line");
                String msgTrashLine = p.getTrashTarget() + Integer.toString(p.getId()) + "1";
                System.err.println("Message sent: : " + msgTrashLine);
                Client.sender.sendIdPlayerMsgOrTlMsg(Integer.parseInt(msgTrashLine));
            }
            case 3 -> {
                System.out.println("Generated 2 trash Line");
                String msgTrashLine = p.getTrashTarget() + Integer.toString(p.getId()) + "2";
                System.err.println("Message sent: : " + msgTrashLine);
                Client.sender.sendIdPlayerMsgOrTlMsg(Integer.parseInt(msgTrashLine));
            }
        }
    }

    public static void clearLine(Players p, int r) {
        for (int c = 1; c < 13 ; c++) {
            p.setPoint(r,c,0);
        }

    }

    public static void shiftDown(Players p, int row) {
        for (int r = row; r > 0; r--) {
            for (int c = 1; c < 13; c++) {
                p.setPoint(r,c,p.getPoint(r-1,c));
            }
        }
    }

    public static void shiftUp(Players p, int r) {
        for (int row = 0; row < r; row++) {
            for (int c = 1; c < 13; c++) {
                p.setPoint(row,c,p.getPoint(row+1,c));
            }
        }
    }

    //se tl = 1 check row 0
    //se tl = 2 check row 0,1
    //se tl = 4 check row 0,1,2,3

    public static boolean canShiftUp(Players p, int nTl) {
        for (int row = 0; row < 3; row++) {
            for (int c = 1; c < 13; c++) {
                if (nTl == 1 && p.getPoint(0, c) != 0) {
                    return false;
                } else if (nTl == 2 && (p.getPoint(0, c) != 0 || p.getPoint(1, c) != 0)) {
                    return false;
                } else if (nTl == 2 && (p.getPoint(0, c) != 0 || p.getPoint(1, c) != 0 || p.getPoint(2, c) != 0 || p.getPoint(3, c) != 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void drawJunkLine(Players p, ReceiveData receiver) {
        if (!p.isGameOver()){
            int treshTemp = receiver.getTlToElaborate();
            if (treshTemp != 0){
                if (!canShiftUp(p, treshTemp)) {
                    p.setGameOver();
                }
                System.out.println("RECEIVED " + treshTemp + " TRASH LINES.");
                for (int i = 0; i<treshTemp; i++) {
                    int row = 23 - (p.getTotalTrashLines());
                    shiftUp(p, row);
                    for (int col = 1; col < 13; col++) {
                        p.setPoint(row, col, 10);
                    }
                    p.addToTotalTrashLines();
                }
            }
        }
    }


    public static final Object lock = new Object();
    public static final Object lock2 = new Object();
    public static final Object lock3 = new Object();


    public static final Semaphore sem = new Semaphore(0);
}
