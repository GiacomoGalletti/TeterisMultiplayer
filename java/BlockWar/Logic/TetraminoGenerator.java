package BlockWar.Logic;

import BlockWar.Tetramini.*;

public class TetraminoGenerator {

    public static Tetramino getRandomShape() {

		double rand = Math.random() * 8;

		return switch ((int) rand) {
			case 0 -> new O();
			case 1 -> new S();
			case 2 -> new Z();
			case 3 -> new L();
			case 4 -> new J();
			case 5 -> new T();
			case 6 -> new C();
			default -> new I();
		};

    }

}