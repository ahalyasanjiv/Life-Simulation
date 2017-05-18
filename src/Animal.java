/**
 * Created by ahalyasanjiv on 5/16/17.
 */
import java.util.Random;
public abstract class Animal extends Entity {
    private final int COOLDOWN; //cycle COOLDOWN before next movement
    private final int ENERGY_COST; //energy lost per cycle
    private int energy;
    private final int MAX_ENERGY; //Animal will not eat when this is reached BUT animal's energy can exceed MAX_ENERGY
    private final int MIN_ENERGY; //Animal will die when reaching this energy
    private final int BIRTH_AGE; //age at which animal can give birth
    private final int BIRTH_ENERGY; //minimum energy at which animal can give birth

    public Animal(int ID, int X, int Y, int MaxAge, int Cooldown, Earth home){
        super(ID, X, Y, MaxAge,home);
        Random rand = new Random();
        ENERGY_COST = 1; //animals lose 1 energy per cycle
        energy = rand.nextInt(8) + 3; //3 thru 10
        MAX_ENERGY =  rand.nextInt(11) + 10; //10 thru 20
        MIN_ENERGY = 0;
        BIRTH_AGE = rand.nextInt(3) + 3; //3 thru 5
        BIRTH_ENERGY = 5;
        COOLDOWN = Cooldown;

    }

    public void move(){
        if (home.isSpaceAvailable(this)) {
            int[] newLoc;
            int[][] surrounding = home.getSurroundCoords(this);
            boolean foundNewSpot = false;
            // Keep looking at random locations surrounding animal until place to move to is found
            while (!foundNewSpot) {
                Random rand = new Random();
                int randLoc = rand.nextInt(8);
                newLoc = surrounding[randLoc];
                if (home.isValidCoordinate(newLoc[0],newLoc[1])){
                    if(home.isEmpty(newLoc[0],newLoc[1])) {
                        home.moveEntity(this, newLoc[0], newLoc[1]);
                        decreaseEnergy(1);
                        foundNewSpot = true;
                    }
                }
            }
        }
    }

    public void update() {
        increaseAge();
        double randomDecimal = Math.random();
        double decimalBoundary;
        if (this instanceof Carnivore)
            decimalBoundary = 0.1;
        else
            decimalBoundary = 0.2;
        if ((getAge() > getMaxAge() || getEnergy() < getMinEnergy()) && randomDecimal < decimalBoundary)
            home.removeEntity(this);
        else if (getAge()>getBirthAge() && getEnergy()>getBirthEnergy() && randomDecimal > 0.5 && home.isSpaceAvailable(this))
            giveBirth();
        else if (getEnergy()<getMaxEnergy() && home.isFoodAvailable(this)&&(getAge() % getCooldown() == 0))
            lookForFood();
        else if (home.isSpaceAvailable(this) && (getAge() % getCooldown() == 0))
            move();
        else
            decreaseEnergy(1);
    }

    public abstract void giveBirth();

    public boolean lookForFood(){
        if (home.isFoodAvailable(this)) {
            int[] newLoc;
            int[][] surrounding = home.getSurroundCoords(this);
            boolean foundFood = false;
            // Keep looking at random locations surrounding animal until place to move to is found
            while (!foundFood) {

                Random rand = new Random();
                int randLoc = rand.nextInt(8);
                newLoc = surrounding[randLoc];
                if (home.isValidCoordinate(newLoc[0],newLoc[1])) {
                    if (((this instanceof Carnivore) && home.isHerbivore(newLoc[0],newLoc[1])) || ((this instanceof Herbivore) && home.isPlant(newLoc[0],newLoc[1]))) {
                        home.removeEntity(home.getEntity(newLoc[0], newLoc[1]));
                        home.moveEntity(this, newLoc[0], newLoc[1]);
                        increaseEnergy(3);
                        foundFood = true;
                    }
                }
            }
            return true;
        }
        else
            return false;
    }

    public void decreaseEnergy(int amount){energy -= amount;}
    public void increaseEnergy(int amount){energy += amount;}
    public int getEnergy(){return energy;}
    public int getEnergyCost(){return ENERGY_COST;}
    public int getMaxEnergy(){return MAX_ENERGY;}
    public int getMinEnergy(){return MIN_ENERGY;}
    public int getBirthAge(){return BIRTH_AGE;}
    public int getBirthEnergy(){return BIRTH_ENERGY;}
    public int getCooldown(){return COOLDOWN;}

}
