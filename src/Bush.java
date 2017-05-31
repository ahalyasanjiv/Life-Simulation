/* Tsz Yan Jamie Fung and Ahalya Sanjiv Final Group Project */

/**
 * Represents a bush, a species of plant.
 */
public class Bush extends Plant {
    /**
     * Constructor for bush.
     * @param ID ID of bush.
     * @param X X coordinate of bush's location.
     * @param Y Y coordinate of bush's location.
     * @param home Earth on which the bush resides.
     */
    public Bush(int ID, int X, int Y, Earth home) {
        super(ID,X,Y,home);
    }
}
