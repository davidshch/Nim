package davids.unit3;

import java.util.*;

/* Name: David Shcherbatykh 
* Tuesday, Nov 7 
* ICS3U 
* Description: The game of Nim is where the user is prompted for if they go first or the computer does 
* and then they take turns taking 1-3 inclusive stones from a random starting amount of stones from 15-30 inclusive. 
* Whoever takes the final stone loses. 
*/
public class Nim {
    
    public static Scanner input = new Scanner(System.in); // Access level is public static to be able to use Objects in all methods
    public static Random rand = new Random(); 
    
    public static String RESET = "\u001B[0m"; // Coloured output
    public static String RED = "\u001B[31m";
    public static String MAGENTA = "\u001B[45m";
    
    
    public static void main(String[] args) {
        int stones = rand.nextInt(15, 31); // Generates random starting amount of stones

        boolean userTurn = whoIsFirst();
        
        if (userTurn) { // Player goes first only if user inputted 0
            stones = playerTurn(stones);
        }
        
        while (true) {
            stones = computerTurn(stones); 
            if (stones <= 0) {
                System.out.println(MAGENTA + "The player beats the computer!" + RESET);
                break;
            }
            stones = playerTurn(stones);
            if (stones <= 0) {
                System.out.println(MAGENTA + "The computer beats the player!" + RESET);
                break;
            }
        }
    }
    
    /**
     * Prompts the user for 0 or 1 for if the player's prompt (turn) is first or the computer's one is, respectively
     * 
     * @return boolean indicating if the user's turn will be first
     */
    public static boolean whoIsFirst(){
        int first;
        boolean userTurn;
        
        do {
            System.out.print("Who goes first? 0 for player and 1 for computer: ");
            try {
                first = input.nextInt();
                if (first == 0) { // If user enters 0 then the player's turn will go first or if user enters 1 then computer's turn is first by updating userTurn
                    userTurn = true; 
                    break;
                } else if (first == 1) {
                    userTurn = false;
                    break;
                }
            } catch (InputMismatchException e) {
                input.nextLine();
            }
        } while (true);
        
        return userTurn;
    }
    
    /**
     * Removes 1-3 stones from total stones based on user input
     *
     * @param stones the total stones there are starting from the beginning stones value
     * @return subtracted stones value
     */
    
    public static int playerTurn(int stones){
        int take;
        
        do {
            try {
                if (stones == 1) { // To check if plural is needed
                    System.out.print("There is " + stones + " stone. How many would you like? ");
                } else {
                    System.out.print("There are " + stones + " stones. How many would you like? ");
                }
                take = input.nextInt();
                if (take > 3 || take < 1) { // Inputs less than 1 or greater than 3 are deemed invalid
                    System.out.println(RED + "Please choose from 1, 2, or 3 stones." + RESET);
                    continue;
                } else if (stones == 1 && take != 1) { // Any input that isn't 1 when there is 1 stone left is deemed invalid
                    System.out.println(RED + "You can only take one stone" + RESET);
                    continue;
                } else if (stones == 2 && take == 3) { // 3 as input is deemed invalid when there are 2 stones left
                    System.out.println(RED + "Invalid number of stones." + RESET);
                    continue;
                }
                stones -= take; // Subtracts input from total stones
            } catch (InputMismatchException e) { 
                System.out.println(RED + "Invalid number of stones." + RESET);
                input.nextLine();
            }
            return stones;
        } while (true);
    }

    /**
     * Removes 1-3 stones from total stones based on a win strategy as the computer.
     * Because you want your opponent to take the last stone and we have the options of taking up to three stones,
     * the strategy is to repeatedly take as many stones to make your opponent take from a multiple of four plus one 
     * because no matter how many stones they take, your opponent will be left with one stone that they must take.
     * For example, when your opponent has to take from five stones (a multiple of four plus one) and they take one
     * stone, you can take three and win, if they take two stones, you can take 2 and win, and finally if they take
     * three stones, you can take one and win. 
     * 
     * @param stones the total stones there are starting from the beginning stones value
     * @return subtracted stones value
     */
    
    public static int computerTurn(int stones) {
        int stonesTaken;

        if (stones == 1) { // Computer will always take one stone if there is only one left
            stonesTaken = 1;
            System.out.println("There is " + stones + " stone. The computer takes " + stones + " stone.");
            stones -= stonesTaken; // Removes 1 from total stones
            return stones;
        }

        switch (stones % 4) {
            case 0 -> { // If the remainder of the stones / 4 is 0 then the computer will take 3 to put player at a disadvantage
                stonesTaken = 3;
                System.out.println("There are " + stones + " stones. The computer takes " + stonesTaken + " stones.");
                stones -= stonesTaken; 
            }
            case 1 -> { // If remainder of stones / 4 is 1 then the computer is at a disadvantage and will take a random amount of stones because it can't do anything else
                stonesTaken = rand.nextInt(1, 4);

                if (stonesTaken == 1) { // Check if plural is needed
                    System.out.println("There are " + stones + " stones. The computer takes " + stonesTaken + " stone.");
                } else {
                    System.out.println("There are " + stones + " stones. The computer takes " + stonesTaken + " stones.");
                }
                stones -= stonesTaken;
            }
            case 2 -> { // If remainder of stones / 4 is 2 then computer will take 1 to put player at a disadvantage
                stonesTaken = 1;
                System.out.println("There are " + stones + " stones. The computer takes " + stonesTaken + " stone.");
                stones -= stonesTaken;
            }
            case 3 -> { // If remainder of stones / 4 is 2 then computer will take 2 to put player at a disadvantage
                stonesTaken = 2;
                System.out.println("There are " + stones + " stones. The computer takes " + stonesTaken + " stones.");
                stones -= stonesTaken;
            }
        }
        return stones;
    }

}
