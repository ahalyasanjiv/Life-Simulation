/* Tsz Yan Jamie Fung and Ahalya Sanjiv Final Group Project */

/**
 * Abstract class that represents a plant. Plants grow randomly and have a lifespan of around 20-25.
 */

import java.util.Random;

abstract public class Plant extends Entity {
    /**
     * Constructor for plant.
     * @param ID ID of plant.
     * @param X X coordinate of plant's location.
     * @param Y Y coordinate of plant's location.
     * @param home Earth on which the plant resides.
     */
    public Plant(int ID, int X, int Y, Earth home){
        super(ID, X, Y, randPlantMaxAge(), home);

    }

    /**
     * Updates the current state of the plant.
     */
    @Override
    public void update() {
        increaseAge();
        if (getAge() > getMaxAge())
            home.removeEntity(this);
    }

    /**
     * Generates a random maximum age for plant between 20 and 25.
     * @return Random maximum age for plant.
     */
    private static int randPlantMaxAge(){
        Random rand = new Random();
        return rand.nextInt(6) + 20; /// 20-25
    }


}
