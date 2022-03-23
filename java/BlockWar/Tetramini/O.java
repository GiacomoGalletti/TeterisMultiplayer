package BlockWar.Tetramini;

import BlockWar.Logic.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class O extends Tetramino {

    private static final int id = 6;

    public List<Coordinates> getCoordinates() {
        List<Coordinates> coordinates = new ArrayList<>();
        coordinates.add(new Coordinates(0, 0));
        coordinates.add(new Coordinates(0, 1));
        coordinates.add(new Coordinates(1, 0));
        coordinates.add(new Coordinates(1, 1));
        return coordinates;
    }

    @Override
    public int getID() {
        return id;
    }

}