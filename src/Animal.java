import java.util.Random;

/**
 * Represents an animal, which can be a carnivore or herbivore.
 */
public abstract class Animal extends Entity {

    /**
     * number of cycles before next movement
     */
    private int cooldown;

    /**
     * Energy lost per cycle
     */
    private final int ENERGY_COST;

    /**
     * Current energy of animal
     */
    private int energy;

    /**
     * Animal will not eat when this is reached BUT animal's energy can exceed MAX_ENERGY
     */
    private final int MAX_ENERGY;

    /**
     * Animal will die when reaching this energy
     */
    private final int MIN_ENERGY;

    /**
     * Age at which animal can give birth
     */
    private final int BIRTH_AGE;

    /**
     * Minimum energy at which animal can give birth
     */
    private final int BIRTH_ENERGY;

    /**
     * Constructor for animal.
     * @param ID Animal's ID.
     * @param X X coordinate of animal's location.
     * @param Y Y coordinate of animal's location.
     * @param maxAge Maximum age of animal.
     * @param cooldown Cooldown of animal (determines number of cycles between moves).
     * @param maxEnergy Animal will not eat when this number is reached BUT animal's energy can exceed this number.
     * @param birthAge Age at which animal can give birth
     * @param birthEnergy Minimum energy at which animal can give birth
     * @param home Earth on which the animal resides.
     */
    public Animal(int ID, int X, int Y, int maxAge, int cooldown, int maxEnergy, int birthAge, int birthEnergy, Earth home){
        super(ID, X, Y, maxAge,home);
        Random rand = new Random();
        ENERGY_COST = 1; // animals lose 1 energy per cycle
        energy = rand.nextInt(8) + 3;
        MAX_ENERGY =  maxEnergy;
        MIN_ENERGY = 0;
        BIRTH_AGE = birthAge;
        BIRTH_ENERGY = birthEnergy;
        this.cooldown = cooldown;

    }


    /** Animal gives birth to a new animal of the same species.
     * The new animal will be in the immediate surrounding area of the animal that gave birth. */
    public abstract void giveBirth();

    /** Animal eats food in its immediate radius (one space away from its initial location).
     * Once the animal eats, it has moved on the grid to the food's location and the food is no longer on the grid.*/
    public abstract void eatFood();

    /** Moves animals to an empty spot in its immediate radius that has food close by (if there is one)
     * NOTE: Animal will not have eaten after this method is executed, but it will be closer to its food. */
    public abstract void moveTowardsFood();

    /** Moves animal to an empty spot in its immediate surrounding area.*/
    public void move(){
        if (home.isSpaceAvailable(this)) {
            int[] newLoc;
            int[][] surrounding = home.getImmediateCoordinates(this);
            boolean foundNewSpot = false;
            // Keep looking at random locations surrounding animal until place to move to is found
            while (!foundNewSpot) {
                Random rand = new Random();
                int randLoc = rand.nextInt(8);
                newLoc = surrounding[randLoc];
                if (home.isValidCoordinate(newLoc[0],newLoc[1])){
                    if(home.isEmpty(newLoc[0],newLoc[1])) {
                        home.moveEntity(this, newLoc[0], newLoc[1]);
                        decreaseEnergy(getEnergyCost());
                        foundNewSpot = true;
                    }
                }
            }
        }
    }

    /**
     * Decreases energy of animal by specified amount.
     * @param amount Amount by which energy should be decreased.
     */
    public void decreaseEnergy(int amount){energy -= amount;}

    /**
     * Increases energy of animal by specified amount.
     * @param amount Amount by which energy should be increased.
     */
    public void increaseEnergy(int amount){energy += amount;}

    /**
     * Gets the current energy level of animal.
     * @return Current energy level of animal.
     */
    public int getEnergy(){return energy;}

    /**
     * Gets the amount of energy lost for the animal per cycle.
     * @return The amount of energy lost for the animal per cycle.
     */
    public int getEnergyCost(){return ENERGY_COST;}

    /**
     * Gets the energy level at which the animal does not need to eat.
     * @return The energy level at which the animal does not need to eat.
     */
    public int getMaxEnergy(){return MAX_ENERGY;}

    /**
     * Gets the minimum energy level the animals needs to survive.
     * @return The minimum energy level the animals needs to survive.
     */
    public int getMinEnergy(){return MIN_ENERGY;}

    /**
     * Gets the minimum age at which the animal can give birth.
     * @return The minimum age at which the animal can give birth.
     */
    public int getBirthAge(){return BIRTH_AGE;}

    /**
     * Gets the minimum amount of energy the animal needs to give birth.
     * @return The minimum amount of energy the animal needs to give birth.
     */
    public int getBirthEnergy(){return BIRTH_ENERGY;}

    /**
     * Gets the cooldown of the animal (which determines the number of cycles between moves).
     * @return The cooldown of the animal
     */
    public int getCooldown(){return cooldown;}

    /**
     * Sets the cooldown of the animal (which determines the number of cycles between moves).
     * @param cooldown The number to which the cooldown of the animal should be set to.
     */
    public void setCooldown(int cooldown){this.cooldown = cooldown;}



}
