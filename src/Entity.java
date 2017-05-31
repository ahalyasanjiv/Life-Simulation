/**
 * Represents an entity on the grid.
 */
public abstract class Entity {

    /**
     * ID of the entity.
     */
    private final int ID;

    /**
     * X coordinate of entity.
     */
    private int x;

    /**
     * Y coordinate of entity.
     */
    private int y;

    /**
     * Age of entity.
     */
    private int age;

    /**
     * Maximum age of entity.
     */
    private final int MAX_AGE;

    /**
     * Whether or not the entity is alive.
     */
    private boolean isAlive;

    /**
     * The earth on which the entity resides.
     */
    public Earth home;

    /**
     * Constructor of Entity. Initializes the ID, location, maximum age, and home of the entity.
     * @param ID ID to assign to entity.
     * @param X X coordinate of entity's location.
     * @param Y Y coordinate of entity's location.
     * @param MaxAge Maximum age of given entity.
     * @param home  Earth that entity resides in (its home).
     */
    public Entity(int ID, int X, int Y, int MaxAge, Earth home){
        this.ID = ID;
        x = X;
        y = Y;
        age = 0;
        MAX_AGE = MaxAge;
        isAlive = true;
        this.home = home;
    }

    /**
     * Sets the X coordinate of the entity's location.
     * @param X X coordinate of the entity's location.
     */
    protected void setX(int X){
        x = X;
    }

    /**
     * Sets the Y coordinate of the entity's location.
     * @param Y Y coordinate of the entity's location.
     */
    protected void setY(int Y){
        y = Y;
    }

    /**
     * Changes the location of the entity to another specified location.
     * @param X X coordinate of location to move entity to.
     * @param Y Y coordinate of location to move entity to.
     */
    public void changeLocation(int X, int Y){
        setX(X);
        setY(Y);
    }

    /**
     * Increases the age of entity by one.
     */
    public void increaseAge(){
        age++;
    }

    /**
     * Gets the X coordinate of entity's current location.
     * @return X coordinate of entity's current location.
     */
    public int getX(){
        return x;
    }

    /**
     * Gets the Y coordinate of entity's current location.
     * @return Y coordinate of entity's current location.
     */
    public int getY(){
        return y;
    }

    /**
     * Gets ID of entity.
     * @return ID of entity.
     */
    public int getID(){
        return ID;
    }

    /**
     * Gets age of entity.
     * @return Age of entity.
     */
    public int getAge(){
        return age;
    }

    /**
     * Gets maximum age of entity.
     * @return Maximum age of entity.
     */
    public int getMaxAge(){
        return MAX_AGE;
    }

    /**
     * Get status of whether entity is alive or not.
     * @return Whether entity is alive (true is alive, false if dead).
     */
    public boolean getStatus(){
        return isAlive;
    }

    /**
     * Changes entity's living status.
     * @param status Whether the entity is alive or not (true is alive, false if dead).
     */
    public void setStatus(boolean status){ isAlive = status; }

    /**
     * Updates the current state of the entity.
     */
    abstract public void update();
}

