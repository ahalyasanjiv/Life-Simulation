/**
 * Created by ahalyasanjiv on 5/16/17.
 */
import java.util.Random;

public class Herbivore extends Animal {

    public Herbivore(int ID, int X, int Y, int cooldown, Earth home){
        super(ID, X, Y, randHerbivoreMaxAge(), cooldown,home);
    }

    private static int randHerbivoreMaxAge(){
        Random rand = new Random();
        return rand.nextInt(6) + 10; // 10-15;
    }

    public static int randCooldown() {
        Random rand = new Random();
        return rand.nextInt(4) + 2; //2 thru 5;
    }

    @Override
    public void giveBirth() {
        if (home.isSpaceAvailable(this)) {
            int[] birthLoc;
            int[][] surrounding = home.getSurroundCoords(this);
            boolean foundBirthSpot = false;
            // Keep looking at random locations surrounding animal until place to move to is found
            while (!foundBirthSpot) {
                Random rand = new Random();
                int randLoc = rand.nextInt(8);
                birthLoc = surrounding[randLoc];
                if (home.isValidCoordinate(birthLoc[0], birthLoc[1])){
                    if(home.isEmpty(birthLoc[0],birthLoc[1])) {
                        home.incrementHerbivoreCount();
                        int newID = home.getCarnivoreCount();
                        Herbivore newHerbivore = new Herbivore(newID, birthLoc[0], birthLoc[1], Carnivore.randCooldown(), home);
                        home.addEntity(newHerbivore);
                        decreaseEnergy(getBirthEnergy());
                        foundBirthSpot = true;
                    }
                }
            }
        }
    }
}

