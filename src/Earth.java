/**
 * Created by ahalyasanjiv on 5/16/17.
 */

import java.util.ArrayList;
import java.util.Random;

public class Earth {
    //
    private Entity[][] grid;

    // ArrayLists to hold each entity currently on the grid
    private ArrayList<Carnivore> carnivoreList;
    private ArrayList<Herbivore> herbivoreList;
    private ArrayList<Plant> plantList;

    // Number of rows and columns on the grid
    private int rows;
    private int columns;

    // Variables to keep track of when to grow plants
    private int nextPlantCycle;
    private int timePassedSincePlantCycle;

    // Number of organisms of each type that has been created thus far
    private int plantCount = 0;
    private int herbivoreCount = 0;
    private int carnivoreCount = 0;

    // Number of organisms of each type currently on grid
    private int plantPop = 0;
    private int herbivorePop = 0;
    private int carnivorePop = 0;

    public Earth(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        grid = new Entity[rows][columns];

        carnivoreList = new ArrayList<Carnivore>();
        herbivoreList = new ArrayList<Herbivore>();
        plantList = new ArrayList<Plant>();

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
                    Carnivore newCarnivore = new Carnivore(newID, j,i, Carnivore.randCooldown(), this);
                    addEntity(newCarnivore);
                    changeCarnivorePopulation(1);
                }
                else if (randomDecimal < 0.18) {
                    incrementHerbivoreCount();
                    int newID = getPlantCount();
                    Herbivore newHerbivore = new Herbivore(newID, j,i, Herbivore.randCooldown(), this);
                    addEntity(newHerbivore);
                    changeHerbivorePopulation(1);
                }
                else if (randomDecimal < 0.38) {
                    incrementPlantCount();
                    int newID = getPlantCount();
                    Plant newPlant = new Plant(newID, j,i, this);
                    addEntity(newPlant);
                    changePlantPopulation(1);
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
        ArrayList<Plant> tempPlantList = new ArrayList<Plant>();
        for(Plant plant:plantList)
            tempPlantList.add(plant);
        for(Carnivore carnivore:tempCarnivoreList)
            carnivore.update();
        for(Herbivore herbivore:tempHerbivoreList)
            herbivore.update();
        for(Plant plant:tempPlantList)
            plant.update();

    }

    // plants new plants at random locations
    public void plantRandomly() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double randomDecimal = Math.random();
                // plant new plants
                if (isEmpty(j,i)) {
                    if(randomDecimal > 0.90) {
                        incrementPlantCount();
                        int newID = getPlantCount();
                        Plant newPlant = new Plant(newID, j,i, this);
                        addEntity(newPlant);
                        changePlantPopulation(1);
                    }
                }
            }
        }
        // reset time passed since last plant cycle to 0
        timePassedSincePlantCycle = 0;
        // set the new plant cycle to a random number between 3 and 5 (inclusive)
        Random rn = new Random();
        int randomNum =  rn.nextInt(4-2+1) + 2;
        nextPlantCycle = randomNum;
    }

    public int[][] getSurroundCoords(Entity n){
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

    public int[][] get2RadiusCoords(Entity n){
        /* the coordinates return in the following pattern:
             8  9 10 11 12
            23  0  1  2 13
            22  7  n  3 14
            21  6  5  4 15
            20 19 18 17 16
         */

        int x = n.getX();
        int y = n.getY();
        int[][] surrounding = {{x-1, y-1},
                {x,y-1}, {x+1,y-1},{x+1,y}, {x+1,y+1}, {x,y+1},{x-1,y+1},{x-1,y},
                {x-2,y-2}, //8
                {x-1, y-2}, //9
                {x, y-2}, //10
                {x+1, y-2}, //11
                {x+2, y-2}, //12
                {x+2, y-1},
                {x+2, y},
                {x+2, y+1},
                {x+2, y+2},
                {x+1, y+2},
                {x, y+2},
                {x-1, y+2},
                {x-2, y+2},
                {x-2, y+1},
                {x-2, y},
                {x-2, y-1}
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

    protected void addEntity(Plant n){
        int X = n.getX();
        int Y = n.getY();

        if (isEmpty(X,Y)) {
            grid[Y][X] = n; //grid[row][col]
            plantList.add(n);
        }
        else{
            System.out.printf("plant: location %d,%d is not empty%n", X, Y);
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
        entity.changeLocation(1,1);
        entity.setStatus(false);
        if (entity instanceof Plant)
            plantList.remove(entity);
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
    private static String symbol(Entity n){
        if (n instanceof Carnivore){
            return "@";
        } else if (n instanceof Herbivore){
            return "&";
        } else if(n instanceof Plant) {
            return "*";
        } else return ".";
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
    public void changePlantPopulation(int change){
        plantPop+=change;
    }

    // changeHerbivorePopulation: gives current number of herbivores on grid
    public void changeHerbivorePopulation(int change){
        herbivorePop+=change;
    }

    // changeCarnivorePopulation: gives current number of carnivores on grid
    public void changeCarnivorePopulation(int change){
        carnivorePop+=change;
    }

    // increment methods for organism counts
    public void incrementPlantCount(){ plantCount++; }
    public void incrementHerbivoreCount(){ herbivoreCount++; }
    public void incrementCarnivoreCount(){ carnivoreCount++; }

    // get methods for organism counts
    public int getPlantCount(){ return plantCount; }
    public int getHerbivoreCount() { return herbivoreCount; }
    public int getCarnivoreCount() { return carnivoreCount; }

    // Get methods for entity lists
    public ArrayList<Carnivore> getCarnivoreList(){return carnivoreList;}
    public ArrayList<Herbivore> getHerbivoreList(){return herbivoreList;}
    public ArrayList<Plant> getPlantList() {return plantList;}

    public boolean isSpaceAvailable(Entity entity){
        //checks whether there is empty space around the entity
        int[][] surrounding = getSurroundCoords(entity);
        for (int[] row: surrounding){
            if (isValidCoordinate(row[0],row[1])){
                if (isEmpty(row[0],row[1])){
                    return true;
                }
            }
        } return false;
    }

    // checks if there is space available in the entire grid
    public boolean isSpaceAvailable(){
        for(Entity[] row: grid){
            for(Entity col: row){
                if (col == null) return true;
            }
        }
        return false;
    }

    // checks if food is available surrounding a specified animal
    public boolean isFoodAvailable(Animal animal){
        //checks whether there is food space around carnivore
        int[][] surrounding = get2RadiusCoords(animal);
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
}