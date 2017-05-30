/**
 * Created by macaron on 5/30/17.
 */
public class Rock extends Entity {
    public Rock(int ID, int X, int Y, Earth home){
        super(ID, X, Y, 0, home);
    }

    @Override
    public void update(){
        increaseAge();
    }
}
