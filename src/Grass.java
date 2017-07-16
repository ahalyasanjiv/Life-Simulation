/**
 * Represents grass, a species of plant.
 */
public class Grass extends Plant{
    /**
     * Constructor for grass.
     * @param ID ID of grass.
     * @param X X coordinate of grass's location.
     * @param Y Y coordinate of grass's location.
     * @param home Earth on which the grass resides.
     */
    public Grass(int ID, int X, int Y, Earth home) {
        super(ID,X,Y,home);
    }
}
