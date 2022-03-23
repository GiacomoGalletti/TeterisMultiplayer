package BlockWar.GUI;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import BlockWar.Logic.BoardManager;
import BlockWar.Net.Client;

import java.io.IOException;

public class ScreenDrawer {


    Terminal terminal = new DefaultTerminalFactory().createTerminal();
    private final Screen screen = new TerminalScreen(terminal);
    private final TextGraphics graphics = screen.newTextGraphics();

    public ScreenDrawer() throws IOException {
        this.screen.startScreen();
        this.terminal.setCursorVisible(false);
    }

    public Screen getScreen(){return screen;}


    private void putPlayerIDonScreen() {
        final int numberOfPlayers = Client.receiver.getPlayersList().size();
        final int myID = Client.myPlayer.getId();
        int myTarget = Client.myPlayer.getTrashTarget();

        switch (myID) {
            case 1:
                graphics.setCharacter(4, 2, '1').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.GREEN_BRIGHT);
                graphics.setCharacter(4, 2, '1');
                graphics.setCharacter(3, 2, 'P');

                graphics.setCharacter(23, 2, '2').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.WHITE);
                graphics.setCharacter(23, 2, '2');

                if(numberOfPlayers > 2) {
                    graphics.setCharacter(42, 2, '3').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.WHITE);
                    graphics.setCharacter(42, 2, '3');
                }
                if(numberOfPlayers > 3) {
                    graphics.setCharacter(61, 2, '4').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.WHITE);
                    graphics.setCharacter(61, 2, '4');

                }

                switch (myTarget){
                    case 2:
                        graphics.setCharacter(23, 2, '2').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                        graphics.setCharacter(23, 2, '2');
                        break;
                    case 3:
                        if(numberOfPlayers > 2){
                            graphics.setCharacter(42, 2, '3').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                            graphics.setCharacter(42, 2, '3');
                        }
                        break;
                    case 4:
                        if(numberOfPlayers > 3){
                            graphics.setCharacter(61, 2, '4').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                            graphics.setCharacter(61, 2, '4');
                        }
                        break;
                }

                break;

            case 2:
                graphics.setCharacter(23, 2, '2').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.GREEN_BRIGHT);
                graphics.setCharacter(23, 2, '2');
                graphics.setCharacter(22, 2, 'P');

                graphics.setCharacter(4, 2, '1').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.WHITE);
                graphics.setCharacter(4, 2, '1');
                if(numberOfPlayers > 2) {
                    graphics.setCharacter(42, 2, '3').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.WHITE);
                    graphics.setCharacter(42, 2, '3');
                }
                if(numberOfPlayers > 3) {
                    graphics.setCharacter(61, 2, '4').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.WHITE);
                    graphics.setCharacter(61, 2, '4');
                }


                switch (myTarget){
                    case 1:
                        graphics.setCharacter(4, 2, '1').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                        graphics.setCharacter(4, 2, '1');
                        break;
                    case 3:
                        if(numberOfPlayers > 2) {
                            graphics.setCharacter(42, 2, '3').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                            graphics.setCharacter(42, 2, '3');
                        }
                        break;
                    case 4:
                        if(numberOfPlayers > 3) {
                            graphics.setCharacter(61, 2, '4').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                            graphics.setCharacter(61, 2, '4');
                        }
                        break;
                }

                break;



            case 3:
                graphics.setCharacter(42, 2, '3').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.GREEN_BRIGHT);
                graphics.setCharacter(42, 2, '3');
                graphics.setCharacter(41, 2, 'P');

                graphics.setCharacter(4, 2, '1').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.WHITE);
                graphics.setCharacter(4, 2, '1');
                graphics.setCharacter(23, 2, '2').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.WHITE);
                graphics.setCharacter(23, 2, '2');

                if(numberOfPlayers > 3) {
                    graphics.setCharacter(61, 2, '4').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.WHITE);
                    graphics.setCharacter(61, 2, '4');
                }

                switch (myTarget){
                    case 1:
                        graphics.setCharacter(4, 2, '1').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                        graphics.setCharacter(4, 2, '1');
                        break;
                    case 2:
                        graphics.setCharacter(23, 2, '2').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                        graphics.setCharacter(23, 2, '2');
                        break;
                    case 4:
                        if(numberOfPlayers > 3) {
                            graphics.setCharacter(61, 2, '4').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                            graphics.setCharacter(61, 2, '4');
                        }
                        break;
                }

                break;

            case 4:
                graphics.setCharacter(61, 2, '4').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.GREEN_BRIGHT);
                graphics.setCharacter(61, 2, '4');
                graphics.setCharacter(60, 2, 'P');

                graphics.setCharacter(4, 2, '1').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.WHITE);
                graphics.setCharacter(4, 2, '1');
                graphics.setCharacter(23, 2, '2').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.WHITE);
                graphics.setCharacter(23, 2, '2');
                graphics.setCharacter(42, 2, '3').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.WHITE);
                graphics.setCharacter(42, 2, '3');

                switch (myTarget){
                    case 1:
                        graphics.setCharacter(4, 2, '1').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                        graphics.setCharacter(4, 2, '1');
                        break;
                    case 2:
                        graphics.setCharacter(23, 2, '2').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                        graphics.setCharacter(23, 2, '2');
                        break;
                    case 3:
                        graphics.setCharacter(42, 2, '3').setForegroundColor(TextColor.ANSI.BLACK).setBackgroundColor(TextColor.ANSI.RED_BRIGHT);
                        graphics.setCharacter(42, 2, '3');
                        break;
                }

                break;
        }



    }

    public void draw(BoardManager bm) throws IOException {

        int[][] boardToRender = bm.getCompleteBoard();

        for (int i = 0; i<boardToRender.length; i++){
            for (int j = 0; j<boardToRender[0].length; j++){
                switch (boardToRender[i][j]){
                    default -> {
                        this.graphics.setCharacter(j,i, ' ').setBackgroundColor(TextColor.ANSI.WHITE);
                    }
                    // RENDER DEL CAMPO DA GIOCO
                    case 9 -> {
                        this.graphics.setCharacter(j,i, ' ').setBackgroundColor(TextColor.ANSI.BLACK);
                    }
                    case 10 -> {
                        this.graphics.setCharacter(j,i, ' ').setBackgroundColor(TextColor.ANSI.BLACK);
                    }
                    case 8 -> {
                        this.graphics.setCharacter(j,i, ' ').setBackgroundColor(TextColor.ANSI.BLACK_BRIGHT);
                    }
                    // RENDER DEI TETRAMINI

                    //Z
                    case 1 -> {
                        this.graphics.setCharacter(j,i, ' ').setBackgroundColor(TextColor.ANSI.RED);
                    }
                    //S
                    case 2 -> {
                        this.graphics.setCharacter(j,i, ' ').setBackgroundColor(TextColor.ANSI.GREEN_BRIGHT);
                    }
                    //J
                    case 3 -> {
                        this.graphics.setCharacter(j,i, ' ').setBackgroundColor(TextColor.ANSI.YELLOW);
                    }
                    //L
                    case 4 -> {
                        this.graphics.setCharacter(j,i, ' ').setBackgroundColor(TextColor.ANSI.BLUE_BRIGHT);
                    }
                    //I
                    case 5 -> {
                        this.graphics.setCharacter(j,i, ' ').setBackgroundColor(TextColor.ANSI.CYAN);
                    }
                    //O
                    case 6 -> {
                        this.graphics.setCharacter(j,i, ' ').setBackgroundColor(TextColor.ANSI.MAGENTA);
                    }
                    //T
                    case 7 -> {
                        this.graphics.setCharacter(j,i, ' ').setBackgroundColor(TextColor.ANSI.YELLOW_BRIGHT);
                    }

                    case 11 ->{
                        this.graphics.setCharacter(j,i,' ').setBackgroundColor(TextColor.ANSI.GREEN);
                    }
                }
            }
        }
        putPlayerIDonScreen();
        this.screen.refresh();
    }

}
