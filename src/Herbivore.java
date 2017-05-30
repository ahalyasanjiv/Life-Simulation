/**
 * Created by ahalyasanjiv on 5/16/17.
 */
import java.util.Random;

public class Herbivore extends Animal {
    public int numberOfBirths = 0;

    public Herbivore(int ID, int X, int Y, Earth home){
        super(ID, X, Y, randMaxAge(), randCooldown(), randMaxEnergy(), randBirthAge(), randBirthEnergy(),home);
    }

    private static int randMaxAge(){
        Random rand = new Random();
        return rand.nextInt(6) + 11; // 12-17;
    }

    private static int randCooldown() {
        Random rand = new Random();
        return rand.nextInt(4) + 1; // 1-3;
    }

    private static int randMaxEnergy(){
        Random rand = new Random();
        return rand.nextInt(5) + 5; // 5-9
    }

    private static int randBirthAge(){
        Random rand = new Random();
        return rand.nextInt(3) + 3; // 3 - 5
    }

    private static int randBirthEnergy(){
        Random rand = new Random();
        return rand.nextInt(4) + 3; // 4 - 7
    }

    @Override
    public void update() {
        increaseAge();
        double randomDecimal = Math.random();
        if ((getAge() > getMaxAge() || getEnergy() < getMinEnergy()) && randomDecimal < 0.15) {
            home.removeEntity(this);
            //System.out.println("herbID " + getID() + " died");
        }
        else if (getAge()>=getBirthAge() && getEnergy()>=getBirthEnergy() && randomDecimal > 0.60 && home.isSpaceAvailable(this) && getNumberOfBirths()<7)
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

    @Override
    public void eatFood() {
        int[] newLocation;
        newLocation = home.findFoodInImmediateRadius(this);
        home.removeEntity(home.getEntity(newLocation[0], newLocation[1]));
        home.moveEntity(this, newLocation[0], newLocation[1]);
        increaseEnergy(4);
    }

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

    public void increaseNumberOfBirths() {
        numberOfBirths++;
    }

    public int getNumberOfBirths() {
        return numberOfBirths;
    }

    public void resetCooldown() {
        super.setCooldown(randCooldown());
    }
}

