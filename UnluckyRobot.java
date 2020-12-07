package unluckyrobot;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author David A-T
 * 
 */
public class UnluckyRobot {
    /**
     * The main frame for all the methods inside of a loop
     * @param args 
     */
    public static void main(String[] args) {
        // Variables
        Scanner console = new Scanner(System.in);
        int totalScore = 300;
        int itrCount = 0;
        int reward;
        char direction;
        int x = 0;
        int y = 0;
        
        // Loop
        while (!isGameOver(x, y, totalScore, itrCount)){
            displayInfo(x, y, itrCount, totalScore);
            direction = inputDirection();
            switch(direction){
                // User Input 'u'
                case 'u':
                    if(doesExceed(x, y, direction)){
                        totalScore -= 10;
                        System.out.println("Exceed boundary, -2000 damage applied");
                        totalScore -= 2000;
                        itrCount++;
                        newline();
                        continue;
                    }
                    else {
                        totalScore -= 10;
                        y++;
                        reward = punishOrMercy(direction, reward());
                        totalScore += reward;
                        itrCount++;
                        newline();
                        continue;
                    }
                // User Input 'd'    
                case 'd':
                    totalScore -= 50;
                    if(doesExceed(x, y, direction)){
                        System.out.println("Exceed boundary, -2000 damage applied");
                        totalScore -= 2000;
                        itrCount++;
                        newline();
                        continue;
                    }
                    else {
                        y--;
                        reward = punishOrMercy(direction, reward());
                        totalScore += reward;
                        itrCount++;
                        newline();
                        continue;
                    }
                // User Input 'r'    
                case 'r':
                    totalScore -= 50;
                    if(doesExceed(x, y, direction)){
                        System.out.println("Exceed boundary, -2000 damage applied");
                        totalScore -= 2000;
                        itrCount++;
                        newline();
                        continue;
                    }
                    else {
                        x++;
                        reward = reward();
                        reward = punishOrMercy(direction, reward);
                        totalScore += reward;
                        itrCount++;
                        newline();
                        continue;
                    }
                // User Input 'l'    
                default:
                    totalScore -= 50;
                    if(doesExceed(x, y, direction)){
                        System.out.println("Exceed boundary, -2000 damage applied");
                        totalScore -= 2000;
                        itrCount++;
                        newline();
                    }
                    else {
                        x--;
                        reward = punishOrMercy(direction, reward());
                        totalScore += reward;
                        itrCount++;
                        newline();
                    }
            }
        } 
        
        System.out.print("Please enter your name (only two words): ");
        String name = toTitleCase(console.nextLine());
        evaluation(totalScore, name);
    }
   
    /**
     * To display the info about the current iteration
     * @param x location on the x axis
     * @param y location on the y axis
     * @param itrCount the number of the current iteration
     * @param totalScore the total amount of points gathered
     */
    public static void displayInfo(int x, int y, int itrCount, int totalScore){
        // Output
        System.out.printf("For point (X=%d, Y=%d) at iterations: %d the total score is: %d\n", x, y, itrCount, totalScore);
    }
    
    public static boolean doesExceed(int x, int y, char direction){
        // Return
        if(x == 4 && direction == 'r'){
            return true;
        }
        else if(x == 0 && direction == 'l'){
            return true;
        }
        else if(y == 4 && direction == 'u'){
            return true;
        }
        else if(y == 0 && direction == 'd'){
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * To randomly generate our points for this iteration
     * @return the amount of points randomly generated
     */
    public static int reward(){
        // Random
        Random rand = new Random();
        int random = rand.nextInt(6) + 1;
        // Output & Return
        switch(random){
            case 1:
                System.out.println("Dice 1, reward: -100");
                return -100;
            case 2:
                System.out.println("Dice 2, reward: -200");
                return -200;
            case 3: 
                System.out.println("Dice 3, reward: -300");
                return -300;
            case 4:   
                System.out.println("Dice 4, reward: 300");
                return 300;
            case 5:         
                System.out.println("Dice 5, reward: 400");
                return 400;
            default:
                System.out.println("Dice 6, reward: 600");
                return 600;
        }
    }
    
    /**
     * To judge if the player gets to not have his negative points or gets them
     * @param direction the direction of which we want to travel [l,r,u,d]
     * @param reward the randomly generated points
     * @return either 0 and or the same value as reward based on randomness
     */
    public static int punishOrMercy(char direction, int reward){
        // Random
        Random rand = new Random();
        int random = rand.nextInt(2);
        // Output & Return
        if(reward < 0 && direction == 'u'){
            if(random == 0){
                System.out.println("Coin: tail | Mercy, the negative reward is removed.");
                return 0;
            }
            else {
                System.out.println("Coin: head | No Mercy, the negative reward is applied.");
                return reward;
            }
        }
        else {
            return reward;
        } 
    }
    
    /**
     * To convert a String into the title case form "Word"
     * @param str the String to be converted
     * @return the converted String
     */
    public static String toTitleCase(String str){
        // Variable
        String str2 = "";
        // Loop
        for(int i = 0; i < str.length(); i++){
            if(i == 0){
                str2 += Character.toUpperCase(str.charAt(0));
            }
            else if(i < str.indexOf(" ")){
                str2 += Character.toLowerCase(str.charAt(i));
            }
            else if(i == str.indexOf(" ") + 1){
                str2 += Character.toUpperCase(str.charAt(i));
            }
            else {
                str2 += Character.toLowerCase(str.charAt(i));
            }
        }

        return str2;
    }
    
    /**
     * To evaluate if the player is victorious or has failed
     * @param totalScore the score to be judged
     * @param name the name of the player
     */
    public static void evaluation(int totalScore, String name){
        // Output
        if(totalScore >= 2000){
            System.out.printf("Victory, %s, your score is %d\n", name, totalScore);
        }
        else {
            System.out.printf("Mission failed, %s, your score is %d\n", name, totalScore);
        }
    }
    
    /**
     * To get the direction from the player
     * @return a properly inputted direction
     */
    public static char inputDirection(){
        // Variables
        Scanner console = new Scanner(System.in);
        boolean condition;
        char direction;
        // Loop
        do {
            System.out.print("Please input a valid direction: ");
            direction = console.next().charAt(0);
            if(direction == 'u' || direction == 'd' || direction == 'l' || direction == 'r'){
                break;
            }
            else {
                condition = false;
            }
        } while (!condition);
        
        //Return
        return direction;
    }
    
    /**
     * To judge if the game is over or not based on winning conditions
     * @param x location on the x axis
     * @param y location on the y axis
     * @param totalScore the total amount of points gathered
     * @param itrCount the number of the current iteration
     * @return true or false based on if the game is over or not
     */
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount){
        // Return
        if(x == 4 && y == 0){
            return true;
        }
        else if(x == 4 && y == 4){
            return true;
        }
        else if(itrCount > 20){
            return true;
        }
        else if(totalScore < -1000){
            return true;
        }
        else if(totalScore > 2000){
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * To create a newline
     */
    public static void newline(){
        System.out.println("");
    }
}
