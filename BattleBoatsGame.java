//nguy4068
//Ngan Nguyen
import java.util.Scanner;
public class BattleBoatsGame {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args){
        System.out.println(" Welcome to Battle Boat. Choose the mode you want to play (Expert or Standard):");
        //Set up the mode for the game
        String mode = input.nextLine();
        String getIn;
        String[] Input;
        while ( !mode.equals("Expert") && ! mode.equals("Standard")) {
            System.out.println("please type in valid in put Standard or Expert");
            mode = input.nextLine();
        }
            BattleBoatsBoard game = new BattleBoatsBoard(mode);

        // game start
        while (game.getRemainShip() > 0) {// Game will end if remaining ship is 0
            System.out.println("choose a coordinate to hit (you can also type 'drone' or 'missile' to use the power, 'print' to see the full board)");
            getIn = input.nextLine();
            System.out.println();
            Input = getIn.split(" ");
            // When input is "print", "drone" or "missile"
            if (Input.length == 1) {
                if (Input[0].equals("drone")) {
                    if (game.getDroneTime() > 0) {// check whether drone is still available for use
                        System.out.println("Would you like to scan a row or a column, type in r for row and c for column");
                        String choice = input.nextLine();
                        while (!choice.equals("r") && !choice.equals("c")) {
                            System.out.println("invalid input try again!");
                            choice = input.nextLine();

                        }
                        System.out.println("What row or column would you like to scan?");
                        int number = input.nextInt();
                        while (number < 0 || number > game.squareSize) {
                            System.out.println("Please type in a valid number");
                            number = input.nextInt();

                        }
                        game.drone(choice, number);
                        input.nextLine();
                    } else {
                        System.out.println("Drone has been used the max amount of time");
                    }


                } else if (Input[0].equals("missile")) {
                    if (game.getMissileTime() > 0) {// check whether missile is still available for use
                        System.out.println("Type in the coordinate you want to send a missile");
                        String get = input.nextLine();
                        String[] coordinate = get.split(" ");
                        int xCor = Integer.parseInt(coordinate[0]);
                        int yCor = Integer.parseInt(coordinate[1]);
                        while (xCor < 0 || yCor < 0 || xCor >= game.squareSize || yCor >= game.squareSize) {
                            System.out.println(" Invalid! Type in a valid coordinate");
                            get = input.nextLine();
                            coordinate = get.split(" ");
                            xCor = Integer.parseInt(coordinate[0]);
                            yCor = Integer.parseInt(coordinate[1]);
                        }
                        game.missile(xCor, yCor);
                        game.disPlay();

                    } else {
                        System.out.println("Missile has been used the max amount of time");
                    }



                } else if (Input[0].equals("print")){
                    game.print();
                } else {
                    System.out.println("Wrong input !");
                }
            //When input is a pair of coordinate
            } else if (Input.length == 2) {
                int xCor = Integer.parseInt(Input[0]);
                int yCor = Integer.parseInt(Input[1]);
                game.fire(xCor, yCor);
            } else {
                System.out.println("Wrong input, you must type in two integers for coordinate, 'print' for print, 'missile' for missile or 'drone' for drone ");
            }
        }
        System.out.println("End game!");
        System.out.println("You have used " + game.getShot() + " shots");
        System.out.println("You have used " + game.getTurn() + " turns");


    }
}
