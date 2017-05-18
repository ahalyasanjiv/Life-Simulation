/**
 * Created by ahalyasanjiv on 5/16/17.
 */
import java.util.Random;

public class Carnivore extends Animal {

    public Carnivore(int ID, int X, int Y,int cooldown, Earth home){
        super(ID, X, Y, randCarnivoreMaxAge(), cooldown, home);
    }

    private static int randCarnivoreMaxAge(){
        Random rand = new Random();
        return rand.nextInt(6) + 7; // 7-12;
    }

    public static int randCooldown(){
        Random rand = new Random();
        return rand.nextInt(3) + 1; //0 thru 2
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
                    if (home.isEmpty(birthLoc[0], birthLoc[1])) {
                        home.incrementCarnivoreCount();
                        int newID = home.getCarnivoreCount();
                        Carnivore newCarnivore = new Carnivore(newID, birthLoc[0], birthLoc[1], Carnivore.randCooldown(), home);
                        home.addEntity(newCarnivore);
                        decreaseEnergy(getBirthEnergy());
                        foundBirthSpot = true;
                    }
                }
            }
        }
    }
}
