/**
 * Created by ahalyasanjiv on 5/16/17.
 */
import java.util.Random;
public abstract class Animal extends Entity {
    private int cooldown; // number of cycles before next movement
    private final int ENERGY_COST; // energy lost per cycle
    private int energy;
    private final int MAX_ENERGY; // Animal will not eat when this is reached BUT animal's energy can exceed MAX_ENERGY
    private final int MIN_ENERGY; // Animal will die when reaching this energy
    private final int BIRTH_AGE; // age at which animal can give birth
    private final int BIRTH_ENERGY; // minimum energy at which animal can give birth

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

    public void decreaseEnergy(int amount){energy -= amount;}
    public void increaseEnergy(int amount){energy += amount;}
    public int getEnergy(){return energy;}
    public int getEnergyCost(){return ENERGY_COST;}
    public int getMaxEnergy(){return MAX_ENERGY;}
    public int getMinEnergy(){return MIN_ENERGY;}
    public int getBirthAge(){return BIRTH_AGE;}
    public int getBirthEnergy(){return BIRTH_ENERGY;}
    public int getCooldown(){return cooldown;}
    public void setCooldown(int cooldown){this.cooldown = cooldown;}



}
