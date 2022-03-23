package BlockWar.Tetramini;

import java.util.ArrayList;
import java.util.List;

import BlockWar.Logic.Coordinates;

public class C extends Tetramino {

    private static final int id = 11;

    public List<Coordinates> getCoordinates() {
        List<Coordinates> coordinates = new ArrayList<>();
        switch(this.direction) {
            case UP:
                coordinates.add(new Coordinates(0, 0));
                coordinates.add(new Coordinates(0, 1));
                coordinates.add(new Coordinates(1, 0));
                coordinates.add(new Coordinates(2, 0));
                coordinates.add(new Coordinates(2, 1));
                break;
            case RIGHT:
                coordinates.add(new Coordinates(0, 0));
                coordinates.add(new Coordinates(0, 1));
                coordinates.add(new Coordinates(0, 2));
                coordinates.add(new Coordinates(1, 0));
                coordinates.add(new Coordinates(1, 2));
                break;
            case DOWN:
                coordinates.add(new Coordinates(0, 0));
                coordinates.add(new Coordinates(0, 1));
                coordinates.add(new Coordinates(1, 1));
                coordinates.add(new Coordinates(2, 0));
                coordinates.add(new Coordinates(2, 1));
                break;
            case LEFT:
                coordinates.add(new Coordinates(0, 0));
                coordinates.add(new Coordinates(0, 2));
                coordinates.add(new Coordinates(1, 0));
                coordinates.add(new Coordinates(1, 1));
                coordinates.add(new Coordinates(1, 2));
                break;
        }
        return coordinates;
    }

    @Override
    public int getID() {
        return id;
    }


}