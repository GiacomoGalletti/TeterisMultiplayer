package BlockWar.Tetramini;

import java.util.ArrayList;
import java.util.List;
import BlockWar.Logic.Coordinates;

public class Z extends Tetramino {

    private static final int id = 1;

    public List<Coordinates> getCoordinates() {

        List<Coordinates> coordinates = new ArrayList<>();
        switch(this.direction)  {
            case UP:
                coordinates.add(new Coordinates(1, 0));
                coordinates.add(new Coordinates(2, 0));
                coordinates.add(new Coordinates(0, 1));
                coordinates.add(new Coordinates(1, 1));
                break;
            case RIGHT:
                coordinates.add(new Coordinates(0, 0));
                coordinates.add(new Coordinates(0, 1));
                coordinates.add(new Coordinates(1, 1));
                coordinates.add(new Coordinates(1, 2));
                break;
            case DOWN:
                coordinates.add(new Coordinates(1, 0));
                coordinates.add(new Coordinates(2, 0));
                coordinates.add(new Coordinates(0, 1));
                coordinates.add(new Coordinates(1, 1));
                break;
            case LEFT:
                coordinates.add(new Coordinates(0, 0));
                coordinates.add(new Coordinates(0, 1));
                coordinates.add(new Coordinates(1, 1));
                coordinates.add(new Coordinates(1, 2));
                break;
        }

        return coordinates;
    }

    public int getID() {
        return id;
    }

}