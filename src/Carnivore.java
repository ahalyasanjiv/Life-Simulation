/**
 * Created by ahalyasanjiv on 5/16/17.
 */
import java.util.Random;

public class Carnivore extends Animal {
    public int numberOfBirths = 0;

    public Carnivore(int ID, int X, int Y, Earth home){
        super(ID, X, Y, randMaxAge(), randCooldown(), randMaxEnergy(), randBirthAge(), randBirthEnergy(), home);
    }

    private static int randMaxAge(){
        Random rand = new Random();
        return rand.nextInt(6) + 14; // 14-19;
    }

    private static int randCooldown(){
        Random rand = new Random();
        return rand.nextInt(3) + 1; // 1-2
    }

    private static int randMaxEnergy(){
        Random rand = new Random();
        return rand.nextInt(5) + 5; // 5-9
    }

    private static int randBirthAge(){
        Random rand = new Random();
        return rand.nextInt(3) + 5; // 5-7
    }

    private static int randBirthEnergy(){
        Random rand = new Random();
        return rand.nextInt(3) + 2; // 4 - 7
    }

    @Override
    public void update() {
        increaseAge();
        double randomDecimal = Math.random();
        if ((getAge() > getMaxAge() || getEnergy() < getMinEnergy()) && randomDecimal < 0.1)
            home.removeEntity(this);
        else if (getAge()>getBirthAge() && getEnergy()>getBirthEnergy() && home.isSpaceAvailable(this) && getNumberOfBirths()<4)
            giveBirth();
        else if (getEnergy()<getMaxEnergy() && home.isFoodInImmediateRadius(this) && (getAge() % getCooldown() == 0) && randomDecimal < 0.5) {
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

    @Override
    public void eatFood() {
        int[] newLocation;
        newLocation = home.findFoodInImmediateRadius(this);
        home.removeEntity(home.getEntity(newLocation[0], newLocation[1]));
        home.moveEntity(this, newLocation[0], newLocation[1]);
        increaseEnergy(3);
        //System.out.println("carnID: "+getID()+" ate");
    }

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
