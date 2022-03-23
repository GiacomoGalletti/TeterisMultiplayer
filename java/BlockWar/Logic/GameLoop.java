package BlockWar.Logic;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import BlockWar.GUI.ScreenDrawer;
import BlockWar.Net.Client;
import BlockWar.Tetramini.Tetramino;

import java.io.IOException;
import java.util.List;

public class GameLoop implements Runnable {

    private final Controller controller;
    private Tetramino tetramino;
    private int xOffset;
    private int yOffset;
    private final ScreenDrawer sd;
    private final BoardManager bm;

    public GameLoop(BoardManager bm) throws IOException {
        sd = new ScreenDrawer();
        controller = new Controller();
        tetramino = TetraminoGenerator.getRandomShape();
        this.bm = bm;
    }

    @Override
    public void run() {
        try {
            sd.draw(bm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        xOffset = 6;
        yOffset = 0;

        controller.drawShape(Client.myPlayer, tetramino, xOffset, yOffset);

        BrickFaller fallTimer = new BrickFaller();
        int brickDropDelay = 1000;
        fallTimer.setDelay(brickDropDelay);
        Thread fallThread = new Thread(fallTimer);
        fallThread.start();

        ControlGameLoop keyInput = new ControlGameLoop((TerminalScreen) sd.getScreen());
        Thread keyInputThread = new Thread(keyInput);
        keyInputThread.start();

        //System.out.println("starting game: xO=" + xOffset + " yO=" + yOffset);

        int fps = 1000/30;
        while(!Thread.currentThread().isInterrupted()) {

            synchronized (ScreenUtil.lock2){
                try {
                    ScreenUtil.lock2.wait(fps);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
// Pause for Thread keyInputThread
            while (Client.pause){
                keyInput.pause();
                synchronized (ScreenUtil.lock){
                    try {
                        ScreenUtil.lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                keyInput.resume();
            }

            boolean nextShape = false;

            List<KeyStroke> keyStrokes = keyInput.getKeyStrokes();

            for(KeyStroke key : keyStrokes) {
                controller.undrawShape(Client.myPlayer, tetramino, xOffset, yOffset);
                try {
                    processKeyInput(key);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
                //System.out.println("move: xO=" + xOffset + " yO=" + yOffset);
                if(!controller.canDropShape(Client.myPlayer, tetramino, xOffset, yOffset)
                        && !Client.myPlayer.isGameOver()) {
                    nextShape = true;
                }
                controller.drawShape(Client.myPlayer, tetramino, xOffset, yOffset);
            }

            if(fallTimer.getDropBrick() && !Client.myPlayer.isGameOver()) {
                //System.out.println("drop: xO=" + xOffset + " yO=" + yOffset);
                controller.undrawShape(Client.myPlayer, tetramino, xOffset, yOffset);
                if(controller.canDropShape(Client.myPlayer, tetramino, xOffset, yOffset)){
                    yOffset++;
                } else {
                    nextShape = true;
                }
                controller.drawShape(Client.myPlayer, tetramino, xOffset, yOffset);
            }

            if(nextShape && !Client.myPlayer.isGameOver()) {
                try {
                    ScreenUtil.checkCompleteLines(Client.myPlayer,bm);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ScreenUtil.drawJunkLine(Client.myPlayer, Client.receiver);

               xOffset = 6;
               yOffset = 0;
               tetramino = TetraminoGenerator.getRandomShape();
            }

            if(Client.myPlayer.isGameOver()){
                Client.myPlayer.setBoard(Client.myPlayer.getDefaultEndGameBoard());
            }

            bm.updateMyBoard();
            Client.sender.sendPlayerMsg(Client.myPlayer);
            try {
                sd.draw(bm);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Drawing error.");
            }
        }
    }

    private void processKeyInput(KeyStroke key) throws InterruptedException, IOException {
        if(Character.valueOf(' ').equals(key.getCharacter())) {
            while(controller.canDropShape(Client.myPlayer, tetramino, xOffset, yOffset)){
                yOffset++;
            }
        }

        // down
        if(key.getKeyType().equals(KeyType.ArrowDown)) {
            if(controller.canDropShape(Client.myPlayer, tetramino, xOffset, yOffset)) {
                yOffset++;
            }
            return;
        }

        // left
        if(key.getKeyType().equals(KeyType.ArrowLeft)) {
            if(controller.canGoLeft(Client.myPlayer, tetramino, xOffset, yOffset)) {
                xOffset--;
            }
            return;
        }

        // right
        if(key.getKeyType().equals(KeyType.ArrowRight)) {
            if(controller.canGoRight(Client.myPlayer, tetramino, xOffset, yOffset)) {
                xOffset++;
            }
            return;
        }

        // rotate left
        if(Character.valueOf('z').equals(key.getCharacter())) {
            if(controller.canRotateLeft(Client.myPlayer, tetramino, xOffset, yOffset)) {
                tetramino.rotateLeft();
            }
            return;
        }

        // rotate right
        if(Character.valueOf('x').equals(key.getCharacter())) {
            if(controller.canRotateRight(Client.myPlayer, tetramino, xOffset, yOffset)) {
                tetramino.rotateRight();
            }
            return;
        }

        // send trash line to player 1
        if (Character.valueOf('1').equals(key.getCharacter()) && Client.myPlayer.getId() != 1) {
            Client.myPlayer.setTrashTarget(1);
            System.out.println("[TARGET IMPOSTATO]: 1");
            return;
        }

        // send trash line to player 2
        if (Character.valueOf('2').equals(key.getCharacter()) && Client.myPlayer.getId() != 2) {
            Client.myPlayer.setTrashTarget(2);
            System.out.println("[TARGET IMPOSTATO]: 2");
            return;
        }

        // send trash line to player 3
        if (Character.valueOf('3').equals(key.getCharacter()) && Client.myPlayer.getId() != 3
                && (Client.receiver.getPlayersList().size() == 3 || Client.receiver.getPlayersList().size() == 4)) {
            Client.myPlayer.setTrashTarget(3);
            System.out.println("[TARGET IMPOSTATO]: 3");
            return;
        }

        // send trash line to player 4
        if (Character.valueOf('4').equals(key.getCharacter()) && Client.myPlayer.getId() != 4
                && Client.receiver.getPlayersList().size() == 4) {
            Client.myPlayer.setTrashTarget(4);
            System.out.println("[TARGET IMPOSTATO]: 4");
            return;
        }

        if (key.getKeyType().equals(KeyType.EOF)){
            System.out.println("closing game...");
            Client.myPlayer.setGameOver();
            Client.myPlayer.setBoard(Client.myPlayer.getDefaultEndGameBoard());
            Client.sender.sendPlayerMsg(Client.myPlayer);

            for (Thread t : Thread.getAllStackTraces().keySet()){
                if (t.getState() == Thread.State.RUNNABLE){
                    t.interrupt();
                }
            }
            System.exit(0);
        }
    }

}