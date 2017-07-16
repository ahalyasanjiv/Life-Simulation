import java.util.Random;

/**
 * Represents a herbivore. Herbivore eat plants. They move every 1 to 3 turns. Their lifespan is around 12-17 years.
 */
public class Herbivore extends Animal {
    /**
     * Records the number of births the herbivore has had so far.
     */
    public int numberOfBirths = 0;

    /**
     * Constructor for herbivore.
     * @param ID ID of herbivore.
     * @param X X coordinate of herbivore's location.
     * @param Y Y coordinate of herbivore's location.
     * @param home Earth on which herbivore resides.
     */
    public Herbivore(int ID, int X, int Y, Earth home){
        super(ID, X, Y, randMaxAge(), randCooldown(), randMaxEnergy(), randBirthAge(), randBirthEnergy(),home);
    }

    /**
     * Generates a random maximum age for herbivore.
     * @return Random maximum age for herbivore.
     */
    private static int randMaxAge(){
        Random rand = new Random();
        return rand.nextInt(6) + 11; // 12-17;
    }

    /**
     * Generates a random cooldown for herbivore. This represents the number of cycles between moves.
     * @return Random cooldown for herbivore.
     */
    private static int randCooldown() {
        Random rand = new Random();
        return rand.nextInt(4) + 1; // 1-3;
    }

    /**
     * Generates a random maximum energy that is sufficient for a herbivore to not need to eat.
     * @return Random maximum energy that is sufficient for a herbivore to not need to eat.
     */
    private static int randMaxEnergy(){
        Random rand = new Random();
        return rand.nextInt(5) + 5; // 5-9
    }

    /**
     * Generates a random minimum age for a herbivore to give birth.
     * @return Random minimum age for a herbivore to give birth.
     */
    private static int randBirthAge(){
        Random rand = new Random();
        return rand.nextInt(3) + 3; // 3 - 5
    }

    /**
     * Generates a random minimum energy required for a herbivore to give birth.
     * @return Random minimum energy required for a herbivore to give birth.
     */
    private static int randBirthEnergy(){
        Random rand = new Random();
        return rand.nextInt(4) + 3; // 4 - 7
    }

    /**
     * Updates the current state of the herbivore. During this update, the herbivore may
     * give birth, eat, move closer to food, simply move (not necessarily towards food), or die.
     */
    @Override
    public void update() {
        increaseAge();
        double randomDecimal = Math.random();
        if ((getAge() > getMaxAge() || getEnergy() < getMinEnergy()) && randomDecimal < 0.15) {
            home.removeEntity(this);
            //System.out.println("herbID " + getID() + " died");
        }
        else if (getAge()>=getBirthAge() && getEnergy()>=getBirthEnergy() && randomDecimal > 0.70 && home.isSpaceAvailable(this) && getNumberOfBirths()<7)
            giveBirth();
        else if (getEnergy()<getMaxEnergy() && home.isFoodInImmediateRadius(this)&&(getAge() % getCooldown() == 0)) {
            eatFood();
            //System.out.println("herbID: " + getID() + " ate");
            resetCooldown();
        }
        else if (home.isFoodInOuterRadius(this) && home.isSpaceAvailable(this) && (getAge() % getCooldown() == 0)){
            moveTowardsFood();
            //System.out.println("herbID: " + getID() + " moved to food");
            resetCooldown();
        }
        else if (home.isSpaceAvailable(this) && (getAge() % getCooldown() == 0)) {
            move();
            //System.out.println("herbID: " + getID() + " moved");
            resetCooldown();
        }
        else {
            decreaseEnergy(getEnergyCost());
            //System.out.println("herbID: " + getID() + " did not do anything");
        }
        //System.out.println("herbID:" + getID()+ " X:" + getX() + " Y:" + getY());
    }

    /**
     * Herbivore eats food that is one space away from it. The herbivore will take the spot of the food and the food will die.
     */
    @Override
    public void eatFood() {
        int[] newLocation;
        newLocation = home.findFoodInImmediateRadius(this);
        home.removeEntity(home.getEntity(newLocation[0], newLocation[1]));
        home.moveEntity(this, newLocation[0], newLocation[1]);
        increaseEnergy(4);
    }

    /**
     * Herbivore moves closer towards food in its outer radius. The herbivore is able to see six spaces away from itself.
     */
    @Override
    public void moveTowardsFood() {
        int[][] surrounding = home.getImmediateCoordinates(this);
        for (int[] newLocation: surrounding){
            // For each space in the immediate radius of the animal, check if it is empty and if there is food nearby. If the conditions are satisfied, move to that location.
            int x = newLocation[0];
            int y = newLocation[1];
            if (home.isEmpty(x,y) && home.isFoodInImmediateRadius(x,y,1)){
                home.moveEntity(this, newLocation[0], newLocation[1]);
                decreaseEnergy(getEnergyCost());
                break;
            }
        }
    }

    /**
     * Herbivore gives birth to another herbivore. The new herbivore will be located near to the herbivore that gave birth.
     */
    @Override
    public void giveBirth() {
        int[] birthLoc;
        int[][] surrounding = home.getImmediateCoordinates(this);
        boolean foundBirthSpot = false;
        // Keep looking at random locations surrounding animal until place to move to is found
        while (!foundBirthSpot) {
            Random rand = new Random();
            int randLoc = rand.nextInt(8);
            birthLoc = surrounding[randLoc];
            if (home.isValidCoordinate(birthLoc[0], birthLoc[1])){
                if(home.isEmpty(birthLoc[0],birthLoc[1])) {
                    home.incrementHerbivoreCount();
                    int newID = home.getHerbivoreCount();
                    Herbivore newHerbivore = new Herbivore(newID, birthLoc[0], birthLoc[1], home);
                    home.addEntity(newHerbivore);
                    decreaseEnergy(2);
                    foundBirthSpot = true;
                    increaseNumberOfBirths();
                    //System.out.println("herbID: "+getID()+" gave birth to herbID" + newHerbivore.getID());
                }
            }
        }

    }
    /**
     * Increases the number of births for the herbivore.
     */
    public void increaseNumberOfBirths() {
        numberOfBirths++;
    }

    /**
     * Gets the number of births the herbivore has had so far.
     * @return The number of births the herbivore has had so far.
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

