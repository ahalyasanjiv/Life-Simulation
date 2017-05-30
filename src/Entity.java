/**
 * Created by ahalyasanjiv on 5/16/17.
 */
public abstract class Entity {
    private final int id;
    private int x;
    private int y;
    private int age; //increases per cycle
    private final int MAX_AGE; //Entity dies after hitting MAX_AGE
    private boolean isAlive;
    public Earth home;

    public Entity(int ID, int X, int Y, int MaxAge, Earth home){
        id = ID;
        x = X;
        y = Y;
        age = 0;
        MAX_AGE = MaxAge;
        isAlive = true;
        this.home = home;
    }

    protected void setX(int X){
        x = X;
    }
    protected void setY(int Y){
        y = Y;
    }

    public void changeLocation(int X, int Y){
        setX(X);
        setY(Y);
    }

    public void increaseAge(){
        age++;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getID(){
        return id;
    }
    public int getAge(){
        return age;
    }
    public int getMaxAge(){
        return MAX_AGE;
    }
    public boolean getStatus(){
        return isAlive;
    }
    public void setStatus(boolean status){ isAlive = status; }

    abstract public void update();
}

