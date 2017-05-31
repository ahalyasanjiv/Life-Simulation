/* Tsz Yan Jamie Fung and Ahalya Sanjiv Final Group Project */

import java.util.Random;

/**
 * Represents a carnivore. Carnivores eat Herbivores. They move every 1 to 2 turns. Their lifespan is around 14-19 years.
 */
public class Carnivore extends Animal {
    /**
     * Records the number of births the carnivore has had so far.
     */
    public int numberOfBirths = 0;

    /**
     * Constructor for carnivore.
     * @param ID ID of carnivore.
     * @param X X coordinate of carnivore's location.
     * @param Y Y coordinate of carnivore's location.
     * @param home Earth on which carnivore resides.
     */
    public Carnivore(int ID, int X, int Y, Earth home){
        super(ID, X, Y, randMaxAge(), randCooldown(), randMaxEnergy(), randBirthAge(), randBirthEnergy(), home);
    }

    /**
     * Generates a random maximum age for carnivore.
     * @return Random maximum age for carnivore.
     */
    private static int randMaxAge(){
        Random rand = new Random();
        return rand.nextInt(6) + 14; // 14-19;
    }

    /**
     * Generates a random cooldown for carnivore. This represents the number of cycles between moves.
     * @return Random cooldown for carnivore.
     */
    private static int randCooldown(){
        Random rand = new Random();
        return rand.nextInt(3) + 1; // 1-2
    }

    /**
     * Generates a random maximum energy that is sufficient for a carnivore to not need to eat.
     * @return Random maximum energy that is sufficient for a carnivore to not need to eat.
     */
    private static int randMaxEnergy(){
        Random rand = new Random();
        return rand.nextInt(3) + 5; // 5-7
    }

    /**
     * Generates a random minimum age for a carnivore to give birth.
     * @return Random minimum age for a carnivore to give birth.
     */
    private static int randBirthAge(){
        Random rand = new Random();
        return rand.nextInt(3) + 5; // 5-7
    }

    /**
     * Generates a random minimum energy required for a carnivore to give birth.
     * @return Random minimum energy required for a carnivore to give birth.
     */
    private static int randBirthEnergy(){
        Random rand = new Random();
        return rand.nextInt(3) + 2; // 4 - 7
    }

    /**
     * Updates the current state of the carnivore. During this update, the carnivore may
     * give birth, eat, move closer to food, simply move (not necessarily towards food), or die.
     */
    @Override
    public void update() {
        increaseAge();
        double randomDecimal = Math.random();
        if ((getAge() > getMaxAge() || getEnergy() < getMinEnergy()) && randomDecimal < 0.05)
            home.removeEntity(this);
        else if (getAge()>getBirthAge() && getEnergy()>getBirthEnergy() && home.isSpaceAvailable(this) && getNumberOfBirths()<5)
            giveBirth();
        else if (getEnergy()<getMaxEnergy() && home.isFoodInImmediateRadius(this) && (getAge() % getCooldown() == 0) && randomDecimal < 0.38) {
            eatFood();
            resetCooldown();
        }
        else if (home.isFoodInOuterRadius(this) && home.isSpaceAvailable(this) && (getAge() % getCooldown() == 0)) {
            moveTowardsFood();
            resetCooldown();
        }
        else if (home.isSpaceAvailable(this) && (getAge() % getCooldown() == 0)) {
            move();
            //System.out.println("carnID: " + getID() + " moved to" + " X:" + getX() + " Y:" + getY());
            resetCooldown();
        }
        else {
            decreaseEnergy(getEnergyCost());
            //System.out.println("carnID: " + getID() + " did not do anything");
        }
        //System.out.println("carnivoreID: " + getID()+ " X:" + getX() + " Y:" + getY());
    }

    /**
     * Carnivore eats food that is one space away from it. The carnivore will take the spot of the food and the food will die.
     */
    @Override
    public void eatFood() {
        int[] newLocation;
        newLocation = home.findFoodInImmediateRadius(this);
        home.removeEntity(home.getEntity(newLocation[0], newLocation[1]));
        home.moveEntity(this, newLocation[0], newLocation[1]);
        increaseEnergy(3);
        //System.out.println("carnID: "+getID()+" ate");
    }

    /**
     * Carnivore moves closer towards food in its outer radius. The carnivore is able to see six spaces away from itself.
     */
    @Override
    public void moveTowardsFood() {
        int[][] surrounding = home.getImmediateCoordinates(this);
        for (int[] newLocation: surrounding){
            // For each space in the immediate radius of the animal, check if it is empty and if there is food nearby. If the conditions are satisfied, move to that location.
            int x = newLocation[0];
            int y = newLocation[1];
            if (home.isEmpty(x,y) && home.isFoodInImmediateRadius(x,y,2)){
                home.moveEntity(this, newLocation[0], newLocation[1]);
                decreaseEnergy(getEnergyCost());
                //System.out.println("carnID: " + getID() + " moved toward food to" + " X:" + getX() + " Y:" + getY());
                break;
            }
        }
    }

    /**
     * Carnivore gives birth to another carnivore. The new carnivore will be located near to the carnivore that gave birth.
     */
    @Override
    public void giveBirth() {
        if (home.isSpaceAvailable(this)) {
            int[] birthLoc;
            int[][] surrounding = home.getImmediateCoordinates(this);
            boolean foundBirthSpot = false;
            // Keep looking at random locations surrounding animal until place to move to is found
            while (!foundBirthSpot) {
                Random rand = new Random();
                int randLoc = rand.nextInt(8);
                birthLoc = surrounding[randLoc];
                if (home.isValidCoordinate(birthLoc[0], birthLoc[1])){
                    if (home.isEmpty(birthLoc[0], birthLoc[1])) {
                        home.incrementCarnivoreCount();
                        int newID = home.getCarnivoreCount();
                        Carnivore newCarnivore = new Carnivore(newID, birthLoc[0], birthLoc[1], home);
                        home.addEntity(newCarnivore);
                        decreaseEnergy(3);
                        foundBirthSpot = true;
                        increaseNumberOfBirths();
                        //System.out.println("carnID: "+getID()+" gave birth to carnID" + newCarnivore.getID());
                    }
                }
            }
        }
    }

    /**
     * Increases the number of births for the carnivore.
     */
    public void increaseNumberOfBirths() {
        numberOfBirths++;
    }

    /**
     * Gets the number of births the carnivore has had so far.
     * @return The number of births the carnivore has had so far.
     */
    public int getNumberOfBirths() {
        return numberOfBirths;
    }

    /**
     * Resets the cooldown (which determines the number of cycles between moves).
     */
    public void resetCooldown() {
        super.setCooldown(randCooldown());
    }
}
