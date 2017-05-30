/**
 * Created by ahalyasanjiv on 5/16/17.
 */
import java.util.Random;

abstract public class Plant extends Entity {
    public Plant(int ID, int X, int Y, Earth home){
        super(ID, X, Y, randPlantMaxAge(), home);
    }

    @Override
    public void update() {
        increaseAge();
        if (getAge() > getMaxAge())
            home.removeEntity(this);
    }

    private static int randPlantMaxAge(){
        Random rand = new Random();
        return rand.nextInt(6) + 20; /// 20-25
    }


}
