package BlockWar.Tetramini;
import BlockWar.Logic.Coordinates;
import BlockWar.Logic.Directions;
import java.util.List;

public abstract class Tetramino {
    protected Directions direction;

    public Tetramino() {
        this.direction = Directions.UP;
    }

    public void rotateRight() {
        switch(this.direction) {
            case UP:
                this.direction = Directions.RIGHT;
                break;
            case RIGHT:
                this.direction = Directions.DOWN;
                break;
            case DOWN:
                this.direction = Directions.LEFT;
                break;
            case LEFT:
                this.direction = Directions.UP;
                break;
        }
    }

    public void rotateLeft() {
        switch(this.direction) {
            case UP:
                this.direction = Directions.LEFT;
                break;
            case LEFT:
                this.direction = Directions.DOWN;
                break;
            case DOWN:
                this.direction = Directions.RIGHT;
                break;
            case RIGHT:
                this.direction = Directions.UP;
                break;
        }
    }

    public abstract List<Coordinates> getCoordinates();

    public abstract int getID();

}
