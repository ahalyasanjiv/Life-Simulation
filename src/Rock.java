/* Tsz Yan Jamie Fung and Ahalya Sanjiv Final Group Project */

/**
 * Represents a rock.
 * A rock cannot move from its position.
 * Other entities cannot step on a rock, they can only go around it.
 */
public class Rock extends Entity {
    /**
     * Constructor for a rock.
     * @param ID ID of the rock.
     * @param X X coordinate of the rock's location.
     * @param Y Y coordinate of the rock's location.
     * @param home Earth on which the rock resides.
     */
    public Rock(int ID, int X, int Y, Earth home){
        super(ID, X, Y, 0, home);
    }

    /**
     * Updates the current state of the rock.
     */
    @Override
    public void update(){
        increaseAge();
    }
}
