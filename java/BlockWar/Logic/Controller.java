package BlockWar.Logic;

import BlockWar.Tetramini.Tetramino;

public class Controller {

    private boolean stepOver = false;

    public boolean canDrawAtPosition(Players p, Tetramino teramino, int xOffset,
                                     int yOffset) {

        for(Coordinates c : teramino.getCoordinates()) {

            int xCoord = xOffset + c.getX();
            int yCoord = yOffset + c.getY();

            // controlla che x sia sul campo
            if(p.getPoint(yCoord, xCoord) == 9 || p.getPoint(yCoord, xCoord) == 8) {
                System.err.println("Board Limit");
                return false;
            }
            // se c'è un blocco dove vorrebbe andare parte un errore // controllo a cella diversa da 0
            if(p.getPoint(yCoord,xCoord) != 0) {
                if (yOffset == 1 && (xOffset >= 5 && xOffset <= 6)){
                    if (stepOver) {
                        p.setGameOver();
                        System.err.println("GAME OVER");
                    }else {
                        stepOver = !stepOver;
                    }
                }
                System.err.println("Block located at :" + xCoord +","+ yCoord);
                return false;
            }
        }

        return true;
    }

    public boolean canDropShape(Players p, Tetramino shape, int xOffset,
                                int yOffset) {
        return this.canDrawAtPosition(p, shape, xOffset, yOffset + 1);
    }

    public boolean canGoRight(Players p, Tetramino shape, int xOffset,
                              int yOffset) {
        return this.canDrawAtPosition(p, shape, xOffset + 1, yOffset);
    }

    public boolean canGoLeft(Players p, Tetramino shape, int xOffset,
                             int yOffset) {
        return this.canDrawAtPosition(p, shape, xOffset - 1, yOffset);
    }

    public boolean canRotateRight(Players p, Tetramino shape, int xOffset,
                                  int yOffset) {
        shape.rotateRight();
        boolean canRotate = canDrawAtPosition(p, shape, xOffset, yOffset);
        shape.rotateLeft();
        return canRotate;
    }

    public boolean canRotateLeft(Players p, Tetramino shape, int xOffset,
                                 int yOffset) {
        shape.rotateLeft();
        boolean canRotate = canDrawAtPosition(p, shape, xOffset, yOffset);
        shape.rotateRight();
        return canRotate;
    }

    public void drawShape( Players p, Tetramino shape, int xOffset, int yOffset) {
        for(Coordinates c : shape.getCoordinates()) {
            int xCoord = c.getX() + xOffset;
            int yCoord = c.getY() + yOffset;
            int id = shape.getID();
            p.setPoint(yCoord, xCoord, id);
        }
    }

    public void undrawShape(Players p,Tetramino shape, int xOffset, int yOffset) {
        for(Coordinates c : shape.getCoordinates()) {
            int xCoord = c.getX() + xOffset;
            int yCoord = c.getY() + yOffset;
            p.setPoint(yCoord,xCoord,0);
        }
    }
}