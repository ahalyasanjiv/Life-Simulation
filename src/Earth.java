/**
 * Created by ahalyasanjiv on 5/16/17.
 */

import java.util.ArrayList;
import java.util.Random;

public class Earth {
    // Grid will hold each entity currently on the earth
    private Entity[][] grid;

    // ArrayLists to hold each entity currently on the grid
    private ArrayList<Carnivore> carnivoreList;
    private ArrayList<Herbivore> herbivoreList;
    private ArrayList<Bush> bushList;
    private ArrayList<Grass> grassList;
    private ArrayList<Rock> rockList;

    // Number of rows and columns on the grid
    private int rows;
    private int columns;

    // Variables to keep track of when to grow plants
    private int nextPlantCycle;
    private int timePassedSincePlantCycle;

    // Number of organisms of each type that has been created thus far
    private int bushCount = 0;
    private int grassCount = 0;
    private int herbivoreCount = 0;
    private int carnivoreCount = 0;
    private int rockCount = 0;

    // Number of organisms of each type currently on grid
    private int bushPop = 0;
    private int grassPop = 0;
    private int herbivorePop = 0;
    private int carnivorePop = 0;

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

    // clockCycle(): method to update grid's state for one clock cycle

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

    // plants new plants at random locations
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

    public int[][] getImmediateCoordinates(Entity n){
        /* the coordinates return in the following pattern:
            0 1 2
            7 n 3
            6 5 4
         */

        int x = n.getX();
        int y = n.getY();
        int[][] surrounding = {{x-1, y-1},
                {x,y-1}, {x+1,y-1},{x+1,y}, {x+1,y+1}, {x,y+1},{x-1,y+1},{x-1,y}};

        return surrounding;
    }

    public int[][] getImmediateCoordinates(int x, int y){
        /* the coordinates return in the following pattern:
            0 1 2
            7 n 3
            6 5 4
         */

        int[][] surrounding = {{x-1, y-1},
                {x,y-1}, {x+1,y-1},{x+1,y}, {x+1,y+1}, {x,y+1},{x-1,y+1},{x-1,y}};

        return surrounding;
    }

    public int[][] getExtendedCoordinates(Entity n){
        /* the coordinates return in the following pattern:

 -6  -5  -4  -3 -2 -1  0 +1 +2 +3 +4 +5  +6

 167 120                             130 131    -6
     119  80                         89         -5
          79 48 49 50 51 52 53 54 55            -4
          78 47 24 25 26 27 28 29 56            -3
          77 46  8  9 10 11 12 30 57            -2
          76 45 23  0  1  2 13 31 58            -1
          75 44 22  7  n  3 14 32 59             0
          74 43 21  6  5  4 15 33 60            +1
          73 42 20 19 18 17 16 34 61            +2
          72 41 40 39 38 37 36 35 62            +3
          71 70 69 68 67 66 65 64 63            +4
     109                             99 142     +5
 155 154                                143     +6
         */
        int x = n.getX();
        int y = n.getY();
        int[][] surrounding
                = {
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

    protected void addEntity(Carnivore n){
        int X = n.getX();
        int Y = n.getY();

        if (isEmpty(X,Y)) {
            grid[Y][X] = n; //grid[row][col]
            carnivoreList.add(n);
        }
        else{
            System.out.printf("carnivore: location %d,%d is not empty%n", X, Y);
        }
    }

    protected void addEntity(Herbivore n){
        int X = n.getX();
        int Y = n.getY();

        if (isEmpty(X,Y)) {
            grid[Y][X] = n; //grid[row][col]
            herbivoreList.add(n);
        }
        else{
            System.out.printf("herbivore: location %d,%d is not empty%n", X, Y);
        }
    }

    protected void addEntity(Bush n){
        int X = n.getX();
        int Y = n.getY();

        if (isEmpty(X,Y)) {
            grid[Y][X] = n; //grid[row][col]
            bushList.add(n);
        }
        else{
            System.out.printf("bush: location %d,%d is not empty%n", X, Y);
        }
    }

    protected void addEntity(Grass n){
        int X = n.getX();
        int Y = n.getY();

        if (isEmpty(X,Y)) {
            grid[Y][X] = n; //grid[row][col]
            grassList.add(n);
        }
        else{
            System.out.printf("grass: location %d,%d is not empty%n", X, Y);
        }
    }

    protected void addEntity(Rock n){
        int X = n.getX();
        int Y = n.getY();

        if (isEmpty(X,Y)) {
            grid[Y][X] = n; //grid[row][col]
            rockList.add(n);
        }
        else{
            System.out.printf("rock: location %d,%d is not empty%n", X, Y);
        }
    }

    public boolean isHerbivore(int X, int Y) {
        return (grid[Y][X] instanceof Herbivore);
    }

    public boolean isPlant(int X, int Y) {
        return (grid[Y][X] instanceof Plant);
    }

    protected void moveEntity(Entity entity, int X, int Y){
        if (isValidCoordinate(X,Y)) {
            grid[entity.getY()][entity.getX()] = null;
            entity.changeLocation(X, Y);
            grid[Y][X] = entity;
        }
    }

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

    // Method that represents each entity as a symbol on the grid
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

    // Method that prints out grid
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

    public boolean isValidCoordinate(int X, int Y){
        //check whether pair of coordinates is within bounds
        if(X >= 0 && Y >= 0 && X < columns && Y < rows)
            return true;
        return false;
    }

    // changePlantPopulation: gives current number of plants on grid
    public void changeBushPopulation(int change){
        bushPop+=change;
    }

    public void changeGrassPopulation(int change) { grassPop+=change; }

    // changeHerbivorePopulation: gives current number of herbivores on grid
    public void changeHerbivorePopulation(int change){
        herbivorePop+=change;
    }

    // changeCarnivorePopulation: gives current number of carnivores on grid
    public void changeCarnivorePopulation(int change){
        carnivorePop+=change;
    }

    // increment methods for organism counts
    public void incrementBushCount(){ bushCount++; }
    public void incrementGrassCount(){ grassCount++; }
    public void incrementHerbivoreCount(){ herbivoreCount++; }
    public void incrementCarnivoreCount(){ carnivoreCount++; }
    public void incrementRockCount(){rockCount++;}

    // get methods for organism counts
    public int getBushCount(){ return bushCount; }
    public int getGrassCount(){ return grassCount; }
    public int getHerbivoreCount() { return herbivoreCount; }
    public int getCarnivoreCount() { return carnivoreCount; }
    public int getRockCount(){return rockCount;}

    // Get methods for entity lists
    public ArrayList<Carnivore> getCarnivoreList(){return carnivoreList;}
    public ArrayList<Herbivore> getHerbivoreList(){return herbivoreList;}
    public ArrayList<Bush> getBushList() {return bushList;}
    public ArrayList<Grass> getGrassList() {return grassList;}

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

    // Checks if food is available within one space of a given animal
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

    // Checks if food is available within two spaces of a given animal
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

    public Entity[][] getGrid(){
        return grid;
    }
}
