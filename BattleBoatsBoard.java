import java.util.List;

//nguy4068
//Ngan Nguyen
public class BattleBoatsBoard {
    private int[][] board;// board used to display the boats
    public int squareSize;// the size of square for each game mode
    private String mode;// mode of each game (Standard or Expert)
    private int totalShip;// Total ship each game has
    public int turn; // Total turn that users can use
    private int remainShip; // Remain ship after each time user fire
    private String[][] board2; // Board used to display whether the ship is hit or missed for user
    private int[] numHit; // Used to track the hit times of each boat
    private int[] boardCollection; // Array present the length of each ship in the board
    private int droneTime;// The total number of droneTime
    private int missileTime;// The total number of missile
    private int shot;// The total number of shot

    /**
     * Constructor
     * Initialize specific data for each game
     * @param mode : The mode the user choose to play (Expert or Standard)
     */
    public BattleBoatsBoard( String mode){
        this.mode = mode;
        if (this.mode.equals("Standard")){
            board = new int[8][8];
            this.totalShip = 5;
            this.squareSize = 8;
            this.remainShip = 5;
            this.droneTime = 1;
            this.missileTime = 1;

        } else if (this.mode.equals("Expert")){
            this.totalShip = 10;
            board = new int[12][12];
            this.squareSize = 12;
            this.remainShip = 10;
            this.droneTime = 2;
            this.missileTime = 2;
        }
        setPresentBoard(squareSize);
        setNumHit(totalShip);
//        placeBoats();
    }
    public int[][] getBoard(){
        return board;
    }
    public int getNumBoat() { return totalShip;}
    public int getRemainShip() { return remainShip;}
    public int getMissileTime() { return missileTime;}
    public int getDroneTime() { return droneTime;}
    public int getShot() { return shot;}
    public int getTurn() {return turn;}

    /**
     * Random to find the direction and the position to place a boat
     * @param length: Length of the boat
     * @param name: specific number for each boat to be distinguished on the Boat Board
     */
    public void placeEachBoat(int length, int name){
               boolean check = false;
               int x = 0;
               int y = 0;
               int randomDirection = 0;
               while (!check) {
                   randomDirection = random(2);
                   x = random(squareSize);
                   y = random(squareSize);
                   check = check(x,y,length,randomDirection);

               }
               fillIn(x,y,length,randomDirection,name);

    }

    /**
     *place all boats on the Boat Board
     */
    public void placeBoats() {
        if (this.mode.equals("Standard")) {
            placeEachBoat(2,1);
            placeEachBoat(3,2);
            placeEachBoat(3,3);
            placeEachBoat(4,4);
            placeEachBoat(5,5);

        } else if (this.mode.equals("Expert")) {

            placeEachBoat(2,1);
            placeEachBoat(2,2);
            placeEachBoat(3,3);
            placeEachBoat(3,4);
            placeEachBoat(3,5);
            placeEachBoat(3,6);
            placeEachBoat(4,7);
            placeEachBoat(4,8);
            placeEachBoat(5,9);
            placeEachBoat(5,10);
        }
    }
    public int[][] possibleCoordinates1(int length){
        int[][] listCoords;
        int countLength = 0;
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if (j + length < board.length){
                    countLength ++;
                }
            }
        }
        listCoords = new int[countLength][2];
        int startIndex = 0;
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if (j + length < board.length){
                    listCoords[startIndex] = new int[]{i,j};
                    startIndex++;
                }

            }
        }
        return listCoords;
    }
    public int[][] possibleCoordinates2(int length){
        int[][] listCoords;
        int countLength = 0;
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if (j + length < board.length){
                    countLength ++;
                }
            }
        }
        listCoords = new int[countLength][2];
        int startIndex = 0;
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if (j + length < board.length){
                    listCoords[startIndex] = new int[] {j,i};
                    startIndex++;
                }

            }
        }
        return listCoords;
    }

    public boolean placeBoatRecursive(int[] ListBoat, int startIndex){
        if (1 <= startIndex && startIndex - 1< ListBoat.length){
            if (!checkPresent(startIndex)){
                int timeFail = 0;
                int direction = random(2);
                int[][] listCoordinates = new int[0][0];
                if (direction == 0){
                    listCoordinates = possibleCoordinates1(ListBoat[startIndex-1]);
                } else if (direction == 1) {
                    listCoordinates = possibleCoordinates2(ListBoat[startIndex-1]);
                }
                int spots = random(listCoordinates.length);
                int x = listCoordinates[spots][0];
                int y = listCoordinates[spots][1];
                while (!check(x,y,ListBoat[startIndex-1],direction) && listCoordinates.length > 0){
                    timeFail++;
                    listCoordinates = delete(listCoordinates,spots);
                    spots = random(listCoordinates.length);
                    x = listCoordinates[spots][0];
                    y = listCoordinates[spots][1];
                }
                if (listCoordinates.length == 0 && startIndex > 1){
                    placeBoatRecursive(ListBoat,startIndex-1);
                }
                fillIn(x,y,ListBoat[startIndex-1],direction,startIndex);
                placeBoatRecursive(ListBoat,startIndex + 1);

            } else if (checkPresent(startIndex)){
                removeBoat(startIndex);
                int timeFail = 0;
                int direction = random(2);
                int[][] listCoordinates = new int[0][0];
                if (direction == 0){
                    listCoordinates = possibleCoordinates1(ListBoat[startIndex-1]);
                } else if (direction == 1) {
                    listCoordinates = possibleCoordinates2(ListBoat[startIndex-1]);
                }
                int spots = random(listCoordinates.length);
                int x = listCoordinates[spots][0];
                int y = listCoordinates[spots][1];
                while (!check(x,y,ListBoat[startIndex-1],direction) && listCoordinates.length > 0){
                    timeFail++;
                    listCoordinates = delete(listCoordinates,spots);
                    spots = random(listCoordinates.length);
                    x = listCoordinates[spots][0];
                    y = listCoordinates[spots][1];
                }
                if (listCoordinates.length == 0 && startIndex > 1){
                    placeBoatRecursive(ListBoat,startIndex-1);
                }
                fillIn(x,y,ListBoat[startIndex-1],direction,startIndex);
                placeBoatRecursive(ListBoat,startIndex + 1);

            }

        } else if (startIndex < 1) {
            return false;
        }
        return true;
    }

    public void removeBoat(int name){
        int startX = 0;
        int startY = 0;
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board.length; j++){
                if (board[i][j] == name){
                    startX = i;
                    startY = j;
                }
                break;
            }
        }
        if (board[startX][startY+1] == name){
            for (int i = startY;i < board.length && board[startX][i] == name  ; i++){
                board[startX][i] = 0;
            }
        } else if (board[startX+1][startY] == name){
            for (int i = startX; i < board.length && board[i][startY] == 0; i ++){
                board[i][startY] = 0;
            }
        }

    }
    public int[][] delete(int[][] array,int index){
        int[][] newArray = new int[array.length-1][2];
        int startIndex = 0;
        for (int i = 0; i < array.length && i!=index; i ++){
            newArray[startIndex] = array[i];
            startIndex++;
        }
        return newArray;
    }
    public boolean checkPresent(int name){
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == name) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Arrange each boat on the Boat Board
     * @param startX: the x coordinate of the start point
     * @param startY: the y coordinate of the start point

     * @param number: the specific number to mark each boat
     */
    public void fillIn(int startX, int startY, int length,int direction, int number){
        // check to place boat horizontal or vertical
        if (direction == 0){// horizontal
            for (int i = startY; i < startY + length; i++){
                board[startX][i] = number;
            }
        } else if (direction == 1){// vertical
            for (int i = startX; i < startX + length; i++){
                board[i][startY] = number;
            }
        }
    }

    /**
     * Set up the Board that will be displayed for user after every turn
     * @param length: the size of the present board
     */
    public void setPresentBoard(int length){
        board2 = new String[length][length];
        for (int i = 0; i < length; i++){
            for (int e = 0; e < length; e++){
                board2[i][e] = "-";
            }
        }

    }

    /**
     * Set up a list of the length of each boat
     * Set up the list to keep track of the time each boat it hit
     * @param numBoat: the total number of boat of a game
     */
    public void setNumHit(int numBoat){
        numHit = new int[numBoat];
        if (numBoat == 5){
            boardCollection = new int[5];
            boardCollection = new int[]{2, 3, 3, 4, 5};
        } if (numBoat == 10){
            boardCollection = new int[10];
            boardCollection = new int[]{2,2,3,3,3,3,4,4,5,5};
        }
    }

    /**
     * return a random number
     * @param num: the upper bound for the random number
     * @return a random number smaller than num
     */
    public int random(int num){
        double firstRan = (Math.random())*num;
        int  numRandom = (int) firstRan;
        return numRandom;
    }

    /**
     * check whether each boat overlaps the others
     * @param startX: the x coordinate of the start position
     * @param startY: the y coordinate of the end position
     * @return boolean value ( true if the boat does not overlap any other boat other wise false)
     */
    public boolean check(int startX, int startY, int length, int direction){
    boolean statement = true;
        if (direction == 0){
            if (startY + length - 1 >= board.length){
                statement = false;
            } else {
            for (int col = startY; col < startY + length; col++){
                if (board[startX][col] != 0){
                    statement = false;
                }
            }
            }
        } else if (direction == 1){ // vertical
            if (startX + length - 1 >= board.length){
                statement = false;
            } else {
                for (int row = startX; row < startX + length; row++) {
                    if (board[row][startY] != 0) {
                        statement = false;
                    }
                }
            }
        }
    return statement;
    }

    /**
     * Fire the coordinate that user enter, fill in the present boat "X" if they hit, "O" if they miss
     * @param xCor: the x coordinate of the fire position
     * @param yCor: the y coordinate of the fire position
     */
    public void fire(int xCor, int yCor){
        if (xCor > squareSize -1  || yCor > squareSize -1 || xCor < 0 || yCor <0){
            System.out.println("Penalty!");
            turn = turn + 2;
        } else if ( board[xCor][yCor] != 0){

            if (board2[xCor][yCor].equals("-")) {
                board2[xCor][yCor] = "X";

                numHit[board[xCor][yCor] -1] ++;
                if ( numHit[board[xCor][yCor] - 1] == boardCollection[board[xCor][yCor] - 1]){
                    System.out.println("Sunk");
                    remainShip --;
                } else {
                    System.out.println("Hit");
                }
                turn ++;
                // Update the board array , -1 for the hit spot
                board[xCor][yCor] = -1;
            } else if (! board2[xCor][yCor].equals("-")) {
                System.out.println("Penalty, you have already hit that spot");
                turn = turn + 2;
            }
            shot ++;
        // When that spot doesn't have boat
        } else if (board[xCor][yCor] == 0){
            if (board2[xCor][yCor].equals("-")) {
                System.out.println("Miss");
                board2[xCor][yCor] = "O";
                board[xCor][yCor] = -2; // Update the board array, -2 for miss spot
                turn ++;

            } else {
                System.out.println("Penalty - You have already fired that spot");
                turn = turn + 2;
            }
            shot++;

        }
        disPlay();
    }

    /**
     * Scan through a row or column and return the number of spots having boat
     * @param direction: drone will scan the board horizontally (direction = 0) or vertically (direction = 1)
     * @param index: the number of that row or column
     */
    public void drone(String direction, int index){
        int count = 0;
        if (direction.equals("r")){
            for (int i = 0; i < squareSize; i++){
                if (board[index][i] != 0){
                    count ++;
                }
            }
        } else if (direction.equals("c")){
            for (int i = 0; i < squareSize; i++){
                if (board[i][index] != 0){
                    count ++;
                }
            }
        }
        droneTime --;
        turn++;
        System.out.println("Drone has scanned " + count + " targets in specific region");

    }

    /**
     * Print out the board2 array which displays the current state of the game after every turn
     */
    public void disPlay(){
        String result = "";
        for (int row = 0; row < board2.length; row++){
            for (int col = 0; col < board2.length; col++){
                if (col == board2.length - 1){
                    result = result + board2[row][col] + "\r\n";
                } else {
                    result = result + board2[row][col] + ",";
                }
            }
        }
        System.out.println(result);
    }

    /**
     * send an missile to a specific coordinate, fire the nearest spots surrounding that spot if the coordinate is valid
     * @param xCor: the x coordinate of the spot the missile is launched to
     * @param yCor: the y coodinate of the spot the missile is launched to
     */
    public void missile(int xCor, int yCor) {
            // initialize an array to store coordinates of the spots surrounding (xCor,yCor)
            int[][] position = new int[9][2];
            int count = 0;
            for (int i = xCor - 1; i < xCor + 2; i++) {
                for (int j = yCor - 1; j < yCor + 2; j++) {
                    position[count][0] = i;
                    position[count][1] = j;
                    count++;
                }
            }
            // loop through all the coordinates stored in position array
            for (int i = 0; i < position.length; i++) {
                // only fire the spot if the coordinate is valid
                if (position[i][0] >= 0 && position[i][0] < squareSize && position[i][1] >= 0 && position[i][1] < squareSize) {
                    int x = position[i][0];
                    int y = position[i][1];
                    // if that spot has boat
                    if (board[x][y] != 0) {
                        // if that spot has not been hit yet ( if it has already been hit, nothing will change)
                        if (board2[x][y].equals("-")) {
                            board2[x][y] = "X";
                            numHit[board[x][y] -1] ++;
                            // check whether the missile will sunk any boat
                            if (numHit[board[x][y] -1] == boardCollection[board[x][y] -1]){
                                remainShip--;
                                System.out.println("Sunk!");
                            }
                            // update the board array, -1 for the hit spot
                            board[x][y] = -1;

                        }
                    } else {// the that spot does not have a boat
                        board2[x][y] = "O";
                        board[x][y] = -2; // update the board array, -2 for the missed spot
                    }

                }
            }
            missileTime--;
            turn++;

    }

    /**
     * print out the board array, reveal the complete board of boat for the user
     */
    public void print(){
            String result = "";
            for (int row = 0; row < board.length; row++){
                for (int col = 0; col < board.length; col++){
                    if (col == board.length - 1){
                        result = result + board[row][col] + "\r\n";
                    } else {
                        result = result + board[row][col] + ",";
                    }
                }
            }
            System.out.println(result);
    }
    public static void main(String[] args){
        BattleBoatsBoard a = new BattleBoatsBoard("Standard");
        int[] ListBoat = new int[] {2,3,3,4,5,7};
        a.placeBoatRecursive(ListBoat,1);
        a.print();
    }
   
}

