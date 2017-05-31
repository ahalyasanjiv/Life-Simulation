import java.util.ArrayList;
import java.util.Random;
/**
 * Represents the earth on which the different plants and animals live on.
 */
public class Earth {

    /** Holds each entity currently on the earth. */
    private Entity[][] grid;

    /** Contains each carnivore currently on the grid. */
    private ArrayList<Carnivore> carnivoreList;
    /** Contains each herbivore currently on the grid. */
    private ArrayList<Herbivore> herbivoreList;
    /** Contains each bush currently on the grid. */
    private ArrayList<Bush> bushList;
    /** Contains each grass currently on the grid. */
    private ArrayList<Grass> grassList;
    /** Contains each rock currently on the grid. */
    private ArrayList<Rock> rockList;

    /** Number of rows on the grid. */
    private int rows;
    /** Number of columns on the grid. */
    private int columns;

    /** Number of clock cycles until next plant cycle. */
    private int nextPlantCycle;
    /** Number of clock cycles that have passed since the last plant cycle. */
    private int timePassedSincePlantCycle;

    /** Number of bush that have been created so far. */
    private int bushCount = 0;
    /** Number of grass that have been created so far. */
    private int grassCount = 0;
    /** Number of herbivores that have been created so far. */
    private int herbivoreCount = 0;
    /** Number of carnivores that have been created so far. */
    private int carnivoreCount = 0;
    /** Number of rocks that have been created so far. */
    private int rockCount = 0;

    /** Number of bush currently on grid. */
    private int bushPop = 0;
    /** Number of grass currently on grid. */
    private int grassPop = 0;
    /** Number of herbivore currently on grid. */
    private int herbivorePop = 0;
    /** Number of carnivore currently on grid. */
    private int carnivorePop = 0;

    /**
     * Constructor for Earth. Initializes carnivores, herbivores, bushes, and grass to random locations on the grid.
     * @param rows Number of rows on the grid.
     * @param columns Number of columns on the grid.
     */
    public Earth(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        grid = new Entity[rows][columns];

        carnivoreList = new ArrayList<Carnivore>();
        herbivoreList = new ArrayList<Herbivore>();
        bushList = new ArrayList<Bush>();
        grassList = new ArrayList<Grass>();
        rockList = new ArrayList<Rock>();

        // set plant cycle
        Random rn = new Random();
        int randomNum =  rn.nextInt(5-3+1) + 3;
        nextPlantCycle = randomNum;
        timePassedSincePlantCycle = 0;

        for(int i=0; i<rows; i++) {
            for(int j=0; j<columns; j++) {
                double randomDecimal = Math.random();
                if (randomDecimal < 0.07) {
                    incrementCarnivoreCount();
                    int newID = getCarnivoreCount();
                    Carnivore newCarnivore = new Carnivore(newID, j,i, this);
                    addEntity(newCarnivore);
                    changeCarnivorePopulation(1);
                }
                else if (randomDecimal < 0.18) {
                    incrementHerbivoreCount();
                    int newID = getHerbivoreCount();
                    Herbivore newHerbivore = new Herbivore(newID, j,i, this);
                    addEntity(newHerbivore);
                    changeHerbivorePopulation(1);
                }
                else if (randomDecimal < 0.28) {
                    incrementGrassCount();
                    int newID = getGrassCount();
                    Grass newGrass = new Grass(newID, j,i, this);
                    addEntity(newGrass);
                    changeGrassPopulation(1);
                }
                else if (randomDecimal < 0.36) {
                    incrementBushCount();
                    int newID = getBushCount();
                    Bush newBush = new Bush(newID, j,i, this);
                    addEntity(newBush);
                    changeBushPopulation(1);
                }
                else if (randomDecimal < 0.51){
                    incrementRockCount();
                    int newID = getRockCount();
                    Rock newRock = new Rock(newID, j, i, this);
                    addEntity(newRock);
                }

            }
        }

    }

    /**
     * Updates grid's state for one clock cycle.
     */
    public void clockCycle() {
        timePassedSincePlantCycle++;
        // if it is time for plant cycle, then plant
        if (timePassedSincePlantCycle == nextPlantCycle) {
            plantRandomly();
        }
        // clone the lists of entities so that we update only the entities on the grid at the beginning of the cycle
        ArrayList<Carnivore> tempCarnivoreList = new ArrayList<Carnivore>();
        for(Carnivore carnivore:carnivoreList)
            tempCarnivoreList.add(carnivore);
        ArrayList<Herbivore> tempHerbivoreList = new ArrayList<Herbivore>();
        for(Herbivore herbivore:herbivoreList)
            tempHerbivoreList.add(herbivore);
        ArrayList<Bush> tempBushList = new ArrayList<Bush>();
        for(Bush bush:bushList)
            tempBushList.add(bush);
        ArrayList<Grass> tempGrassList = new ArrayList<Grass>();
        for(Grass grass:grassList)
            tempGrassList.add(grass);
        for(Carnivore carnivore:tempCarnivoreList)
            carnivore.update();
        for(Herbivore herbivore:tempHerbivoreList)
            herbivore.update();
        for(Bush bush:tempBushList)
            bush.update();
        for(Grass grass:tempGrassList)
            grass.update();

    }

    /**
     * Plants bushes and grass at random empty locations on earth. Resets the number of cycles until the next plant cycle.
     */
    public void plantRandomly() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double randomDecimal = Math.random();
                // plant new plants
                if (isEmpty(j,i)) {
                    if(randomDecimal < 0.25) {
                        incrementBushCount();
                        int newID = getBushCount();
                        Bush newBush = new Bush(newID, j,i, this);
                        addEntity(newBush);
                        changeBushPopulation(1);
                    }
                    else if (randomDecimal < 0.60) {
                        incrementGrassCount();
                        int newID = getGrassCount();
                        Grass newGrass = new Grass(newID, j,i, this);
                        addEntity(newGrass);
                        changeGrassPopulation(1);
                    }
                }
            }
        }

        // reset time passed since last plant cycle to 0
        timePassedSincePlantCycle = 0;
        // set the new plant cycle to a random number between 3 and 5
        Random rn = new Random();
        int randomNum =  rn.nextInt(3) + 3;
        nextPlantCycle = randomNum;
    }

    /**
     * Gets the immediate coordinates within a one space radius of a given entity.
     * @param entity The entity for which we are looking for the surrounding coordinates of.
     * @return A 2D array containing 8 coordinates surrounding a location.
     *          Each two-element array within the returned array represents a the X and Y coordinate of a location.
     *          the coordinates return in the following pattern (where n represents the location given):
     *          0 1 2
     *          7 n 3
     *          6 5 4
     */
    public int[][] getImmediateCoordinates(Entity entity){
        int x = entity.getX();
        int y = entity.getY();
        int[][] surrounding = {{x-1, y-1},
                {x,y-1}, {x+1,y-1},{x+1,y}, {x+1,y+1}, {x,y+1},{x-1,y+1},{x-1,y}};

        return surrounding;
    }

    /**
     * Gets the immediate coordinates within a one space radius of a given location.
     * @param x The X coordinate for the location we looking for around.
     * @param y The Y coordinate for the location we looking for around.
     * @return A 2D array containing 8 coordinates surrounding a location.
     *          Each two-element array within the returned array represents a the X and Y coordinate of a location.
     *          the coordinates return in the following pattern (where n represents the location given):
     *          0 1 2
     *          7 n 3
     *          6 5 4
     *
     */
    public int[][] getImmediateCoordinates(int x, int y){
        /* the coordinates return in the following pattern (where n represents the location given):
            0 1 2
            7 n 3
            6 5 4
         */
        int[][] surrounding = {{x-1, y-1},
                {x,y-1}, {x+1,y-1},{x+1,y}, {x+1,y+1}, {x,y+1},{x-1,y+1},{x-1,y}};
        return surrounding;
    }

    /**
     * Gets the extended coordinates surrounding a given entity within a six space radius.
     * @param entity The entity for which we are finding the surrounding coordinates of.
     * @return A 2D array containing the extended coordinates surrounding the specified entity within a six space radius.
     *          Each two-element array within the returned array represents a the X and Y coordinate of a location.
     *          The coordinates will return in the following order:
     *
     *           167 120 121 122 123  124 125  126 127 128 129 130 131
     *           166 119 80  81  82   83   84  85  86  87  88  89  132
     *           165 118 79  48  49   50   51  52  53  54  55  90  133
     *           164 117 78  47  24   25   26  27  28  29  56  91  134
     *           163 116 77  46   8    9   10  11  12  30  57  92  135
     *           162 115 76  45  23    0    1   2  13  31  58  93  136
     *           161 114 75  44  22    7 ENTITY 3  14  32  59  94  137
     *           160 113 74  43  21    6    5   4  15  33  60  95  138
     *           159 112 73  42  20   19   18  17  16  34  61  96  139
     *           158 111 72  41  40   39   38  37  36  35  62  97  140
     *           157 110 71  70  69   68   67  66  65  64  63  98  141
     *           156 109 108 107 106 105  104  103 102 101 100 99  142
     *           155 154 153 152 151 150  149  148 147 146 145 144 143
     *
     */
    public int[][] getExtendedCoordinates(Entity entity){
        int x = entity.getX();
        int y = entity.getY();
        int[][] surrounding = {
                {x-1,y-1},{x,y-1},{x+1,y-1},{x+1,y},{x+1,y+1},{x,y+1},{x-1,y+1},{x-1,y},{x-2,y-2},{x-1,y-2}, // 0-9
                {x,y-2},{x+1, y-2},{x+2,y-2},{x+2,y-1},{x+2, y},{x+2,y+1},{x+2, y+2},{x+1,y+2},{x,y+2},{x-1, y+2}, // 10-19
                {x-2, y+2},{x-2,y+1},{x-2,y},{x-2,y-1},{x-2,y-3},{x-1,y-3},{x,y-3},{x+1,y-3},{x+2,y-3},{x+3,y-3}, // 20-29
                {x+3,y-2},{x+3,y-1},{x+3,y},{x+3,y+1},{x+3,y+2},{x+3,y+3},{x+2,y+3},{x+1,y+3},{x,y+3},{x-1,y+3},  // 30-39
                {x-2,y+3},{x-3,y+3},{x-3,y+2},{x-3,y+1},{x-3,y},{x-3,y-1},{x-3,y-2},{x-3,y-3},{x-3,y-4},{x-2,y-4}, // 40-49
                {x-1,y-4},{x,y-4},{x+1,y-4},{x+2,y-4},{x+3,y-4},{x+4,y-4},{x+4,y-3},{x+4,y-2},{x+4,y-1},{x+4,y}, // 50-59
                {x+4,y+1},{x+4,y+2},{x+4,y+3},{x+4,y+4},{x+3,y+4},{x+2,y+4},{x+1,y+4},{x,y+4},{x-1,y+4},{x-2,y+4}, // 60-69
                {x-3,y+4},{x-4,y+4},{x-4,y+3},{x-4,y+2},{x-4,y+1},{x-4,y},{x-4,y-1},{x-4,y-2},{x-4,y-3},{x-4,y-4},  // 70-79
                {x-4,y-5},{x-3,y-5},{x-2,y-5},{x-1,y-5},{x,y-5},{x+1,y-5},{x+2,y-5},{x+3,y-5},{x+4,y-5},{x+5,y-5}, // 80-89
                {x+5,y-4},{x+5,y-3},{x+5,y-2},{x+5,y-1},{x+5,y},{x+5,y+1},{x+5,y+2},{x+5,y+3},{x+5,y+4},{x+5,y+5}, // 90-99
                {x+4,y+5},{x+3,y+5},{x+2,y+5},{x+1,y+5},{x,y+5},{x-1,y+5},{x-2,y+5},{x-3,y+5},{x-4,y+5},{x-5,y+5}, // 100-109
                {x-5,y+4},{x-5,y+3},{x-5,y+2},{x-5,y+1},{x-5,y},{x-5,y-1},{x-5,y-2},{x-5,y-3},{x-5,y-4},{x-5,y-5}, // 110-119
                {x-5,y-6},{x-4,y-6},{x-3,y-6},{x-2,y-6},{x-1,y-6},{x,y-6},{x+1,y-6},{x+2,y-6},{x+3,y-6},{x+4,y-6}, // 120-129
                {x+5,y-6},{x+6,y-6},{x+6,y-5},{x+6,y-4},{x+6,y-3},{x+6,y-2},{x+6,y-1},{x+6,y},{x+6,y+1},{x+6,y+2}, // 130-139
                {x+6,y+3},{x+6,y+4},{x+6,y+5},{x+6,y+6},{x+5,y+6},{x+4,y+6},{x+3,y+6},{x+2,y+6},{x+1,y+6},{x,y+6}, // 140-149
                {x-1,y+6},{x-2,y+6},{x-3,y+6},{x-4,y+6},{x-5,y+6},{x-6,y+6},{x-6,y+5},{x-6,y+4},{x-6,y+3},{x-6,y+2}, // 150-159
                {x-6,y+1},{x-6,y},{x-6,y-1},{x-6,y-2},{x-6,y-3},{x-6,y-4},{x-6,y-5},{x-6,y-6} // 160-167
                 };
        return surrounding;
    }

    /**
     * Adds carnivore to carnivore list.
     * @param carnivore Carnivore to add to the list.
     */
    protected void addEntity(Carnivore carnivore){
        int X = carnivore.getX();
        int Y = carnivore.getY();

        if (isEmpty(X,Y)) {
            grid[Y][X] = carnivore; //grid[row][col]
            carnivoreList.add(carnivore);
        }
        else{
            System.out.printf("carnivore: location %d,%d is not empty%n", X, Y);
        }
    }

    /**
     * Adds herbivore to herbivore list.
     * @param herbivore Herbivore to add to the list.
     */
    protected void addEntity(Herbivore herbivore){
        int X = herbivore.getX();
        int Y = herbivore.getY();

        if (isEmpty(X,Y)) {
            grid[Y][X] = herbivore; //grid[row][col]
            herbivoreList.add(herbivore);
        }
        else{
            System.out.printf("herbivore: location %d,%d is not empty%n", X, Y);
        }
    }

    /**
     * Adds bush to bush list.
     * @param bush Bush to add to list.
     */
    protected void addEntity(Bush bush){
        int X = bush.getX();
        int Y = bush.getY();

        if (isEmpty(X,Y)) {
            grid[Y][X] = bush; //grid[row][col]
            bushList.add(bush);
        }
        else{
            System.out.printf("bush: location %d,%d is not empty%n", X, Y);
        }
    }

    /**
     * Adds grass to grass list.
     * @param grass Grass to add to list.
     */
    protected void addEntity(Grass grass){
        int X = grass.getX();
        int Y = grass.getY();

        if (isEmpty(X,Y)) {
            grid[Y][X] = grass; //grid[row][col]
            grassList.add(grass);
        }
        else{
            System.out.printf("grass: location %d,%d is not empty%n", X, Y);
        }
    }

    /**
     * Adds rock to rock list.
     * @param rock Rock to add to list.
     */
    protected void addEntity(Rock rock){
        int X = rock.getX();
        int Y = rock.getY();

        if (isEmpty(X,Y)) {
            grid[Y][X] = rock; //grid[row][col]
            rockList.add(rock);
        }
        else{
            System.out.printf("rock: location %d,%d is not empty%n", X, Y);
        }
    }

    /**
     * Checks if a given location on the grid is occupied by a herbivore.
     * @param X X coordinate of location.
     * @param Y Y coordinate of location.
     * @return Whether the given location on the grid is occupied by a herbivore.
     */
    public boolean isHerbivore(int X, int Y) {
        return (grid[Y][X] instanceof Herbivore);
    }

    /**
     * Checks if a given location on the grid is occupied by a plant.
     * @param X X coordinate of location.
     * @param Y Y coordinate of location.
     * @return Whether the given location on the grid is occupied by a plant.
     */
    public boolean isPlant(int X, int Y) {
        return (grid[Y][X] instanceof Plant);
    }

    /**
     * Moves an entity to a specified location.
     * @param entity Entity to move.
     * @param X X coordinate of location to move entity.
     * @param Y Y coordinate of location to move entity.
     */
    protected void moveEntity(Entity entity, int X, int Y){
        if (isValidCoordinate(X,Y)) {
            grid[entity.getY()][entity.getX()] = null;
            entity.changeLocation(X, Y);
            grid[Y][X] = entity;
        }
    }

    /**
     * Removes a specified entity from the grid and from its respective list.
     * @param entity The entity that is to be deleted from the grid.
     */
    protected void removeEntity(Entity entity){
        grid[entity.getY()][entity.getX()] = null;
        entity.setStatus(false);
        if (entity instanceof Bush)
            bushList.remove(entity);
        else if (entity instanceof Grass)
            grassList.remove(entity);
        else if (entity instanceof Herbivore)
            herbivoreList.remove(entity);
        else if (entity instanceof Carnivore)
            carnivoreList.remove(entity);
    }

    protected Entity getEntity(int X, int Y){
        return grid[Y][X];
    }

    /**
     * Checks if a given location is empty (has no entity occupying it).
     * @param X X coordinate of location to check.
     * @param Y Y coordinate of location to check.
     * @return Whether the given location is empty.
     */
    public boolean isEmpty(int X, int Y){
        //checks if location is empty
        if (X < 0 || Y < 0 || X >= columns || Y >= rows){
            //check whether pair of coordinates is within bounds
            return false;
        }
        if (grid[Y][X] == null){
            return true;
        }
        return false;
    }

    /**
     * Returns the string representation of a specified entity.
     * @param entity Entity for which we are getting the string representation of.
     * @return String representation of a specified entity.
     */
    private static String symbol(Entity entity) {
        if (entity instanceof Carnivore) {
            return "@";
        } else if (entity instanceof Herbivore) {
            return "&";
        } else if (entity instanceof Bush) {
            return "*";
        } else if (entity instanceof Grass){
            return "^";
        }else if (entity instanceof Rock) {
            return "#";
        }
        else return ".";
    }

    /**
     * Gives the string representation of the grid.
     * @return The string representation of the grid.
     */
    @Override
    public String toString(){
        String s = "";
        for (Entity[] row: grid){
            for (Entity col: row){
                s = s + " " + symbol(col);
            }
            s = s + "\n";

        }
        return s;
    }

    /**
     * Checks if a given location is valid coordinate on the grid.
     * @param X X coordinate of the location.
     * @param Y Y coordinate of the location.
     * @return Whether the given location is valid coordinate on the grid.
     */
    public boolean isValidCoordinate(int X, int Y){
        //check whether pair of coordinates is within bounds
        if(X >= 0 && Y >= 0 && X < columns && Y < rows)
            return true;
        return false;
    }

    /**
     * Changes the current bush population.
     * @param change The number to add to the current bush population. (+1 to add one, -1 to subtract one)
     */
    public void changeBushPopulation(int change){
        bushPop+=change;
    }

    /**
     * Changes the current grass population.
     * @param change The number to add to the current grass population. (+1 to add one, -1 to subtract one)
     */
    public void changeGrassPopulation(int change) { grassPop+=change; }

    /**
     * Changes the current herbivore population.
     * @param change The number to add to the current herbivore population. (+1 to add one, -1 to subtract one)
     */
    public void changeHerbivorePopulation(int change){
        herbivorePop+=change;
    }


    /**
     * Changes the current carnivore population.
     * @param change The number to add to the current carnivore population. (+1 to add one, -1 to subtract one)
     */
    public void changeCarnivorePopulation(int change){
        carnivorePop+=change;
    }


    /**
     * Increments the number of bushes.
     */
    public void incrementBushCount(){ bushCount++; }

    /**
     * Increments the number of grass.
     */
    public void incrementGrassCount(){ grassCount++; }

    /**
     * Increments the number of herbivores.
     */
    public void incrementHerbivoreCount(){ herbivoreCount++; }

    /**
     * Increments the number of carnivores.
     */
    public void incrementCarnivoreCount(){ carnivoreCount++; }

    /**
     * Increments the number of rocks.
     */
    public void incrementRockCount(){rockCount++;}

    /**
     * Gets the number of bushes that have been created.
     * @return Number of bushes that have been created.
     */
    public int getBushCount(){ return bushCount; }

    /**
     * Gets the number of grass that have been created.
     * @return Number of grass that have been created.
     */
    public int getGrassCount(){ return grassCount; }

    /**
     * Gets the number of herbivores that have been created.
     * @return Number of herbivores that have been created.
     */
    public int getHerbivoreCount() { return herbivoreCount; }

    /**
     * Gets the number of carnivores that have been created.
     * @return Number of carnivores that have been created.
     */
    public int getCarnivoreCount() { return carnivoreCount; }

    /**
     * Gets the number of carnivores that have been created.
     * @return Number of rocks that have been created.
     */
    public int getRockCount(){return rockCount;}

    /**
     * Gets the list of carnivores currently on the grid.
     * @return List of carnivores currently on the grid.
     */
    public ArrayList<Carnivore> getCarnivoreList(){return carnivoreList;}

    /**
     * Gets the list of herbivores currently on the grid.
     * @return List of herbivores currently on the grid.
     */
    public ArrayList<Herbivore> getHerbivoreList(){return herbivoreList;}

    /**
     * Gets the list of bushes currently on the grid.
     * @return Gets the list of bushes currently on the grid.
     */
    public ArrayList<Bush> getBushList() {return bushList;}

    /**
     * Gets the list of grass currently on the grid.
     * @return List of grass currently on the grid.
     */
    public ArrayList<Grass> getGrassList() {return grassList;}

    /**
     * Checks whether there is an empty spot that is one space away from a given entity.
     * @param entity The entity for which we are looking if there is space nearby.
     * @return Whether there is an empty spot one space from the given entity.
     */
    public boolean isSpaceAvailable(Entity entity){
        //checks whether there is empty space around the entity
        int[][] surrounding = getImmediateCoordinates(entity);
        for (int[] row: surrounding){
            if (isValidCoordinate(row[0],row[1])){
                if (isEmpty(row[0],row[1])){
                    return true;
                }
            }
        } return false;
    }

    /**
     * Checks if food is available within one space of a given animal
     * @param animal The animal for which we are looking for food.
     * @return Whether or not there is food within a space away from the given animal.
     */
    public boolean isFoodInImmediateRadius(Animal animal) {
        int[][] surrounding = getImmediateCoordinates(animal);
        for (int[] row: surrounding){
            if (isValidCoordinate(row[0],row[1])){
                if (animal instanceof Herbivore && getEntity(row[0],row[1]) instanceof Plant)
                    return true;
                else if (animal instanceof Carnivore && getEntity(row[0],row[1]) instanceof Herbivore)
                    return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is food within one space away from a certain location given the coordinates and the predator's type.
     * @param x The x coordinate of the location we are investigating.
     * @param y The y coordinate of the location we are investigating.
     * @param animalType The type of the animal that will be eating the food. 1 stands for herbivore and 2 stands for carnivore.
     * @return Whether or not there is food within a space away from the given location.
     */
    public boolean isFoodInImmediateRadius(int x, int y, int animalType) {
        int[][] surrounding = getImmediateCoordinates(x,y);
        for (int[] row: surrounding){
            if (isValidCoordinate(row[0],row[1])){
                if (animalType == 1 && getEntity(row[0],row[1]) instanceof Plant)
                    return true;
                else if (animalType == 2 && getEntity(row[0],row[1]) instanceof Herbivore)
                    return true;
            }
        }
        return false;
    }

    /**
     * Finds location of food that is one space away of a specified animal.
     * @param animal The animal for which we are looking for food.
     * @return The x and y coordinates of the food's location.
     */
    public int[] findFoodInImmediateRadius(Animal animal) {
        int[][] surrounding = getImmediateCoordinates(animal);
        for (int[] row: surrounding){
            if (isValidCoordinate(row[0],row[1])){
                if (animal instanceof Herbivore && getEntity(row[0],row[1]) instanceof Plant)
                    return row;
                else if (animal instanceof Carnivore && getEntity(row[0],row[1]) instanceof Herbivore)
                    return row;
            }
        }
        return null;
    }

    /**
     * Finds if food is in the outer radius of an animal (within six spaces away).
     * @param animal The animal for which we are looking for food.
     * @return Whether there is food in the outer radius of the animal.
     */
    public boolean isFoodInOuterRadius(Animal animal){
        //checks whether there is food space around carnivore
        int[][] surrounding = getExtendedCoordinates(animal);
        for (int[] row: surrounding){
            if (isValidCoordinate(row[0],row[1])){
                if (animal instanceof Herbivore && getEntity(row[0],row[1]) instanceof Plant)
                    return true;
                else if (animal instanceof Carnivore && getEntity(row[0],row[1]) instanceof Herbivore)
                    return true;
            }
        }
        return false;
    }

    /**
     * Gets the grid where are the entities are located.
     * @return Grid that holds entities.
     */
    public Entity[][] getGrid(){
        return grid;
    }
}
