package BlockWar.Tetramini;

import BlockWar.Logic.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class I extends Tetramino {

    private static final int id = 5;

    public List<Coordinates> getCoordinates() {

        List<Coordinates> coordinates = new ArrayList<>();
        switch(this.direction) {
            case UP:
                coordinates.add(new Coordinates(0, 0));
                coordinates.add(new Coordinates(0, 1));
                coordinates.add(new Coordinates(0, 2));
                coordinates.add(new Coordinates(0, 3));
                break;
            case RIGHT:
                coordinates.add(new Coordinates(0, 0));
                coordinates.add(new Coordinates(1, 0));
                coordinates.add(new Coordinates(2, 0));
                coordinates.add(new Coordinates(3, 0));
                break;
            case DOWN:
                coordinates.add(new Coordinates(0, 0));
                coordinates.add(new Coordinates(0, 1));
                coordinates.add(new Coordinates(0, 2));
                coordinates.add(new Coordinates(0, 3));
                break;
            case LEFT:
                coordinates.add(new Coordinates(0, 0));
                coordinates.add(new Coordinates(1, 0));
                coordinates.add(new Coordinates(2, 0));
                coordinates.add(new Coordinates(3, 0));
                break;
        }

        return coordinates;

    }

    @Override
    public int getID() {
        return id;
    }
}