package za.co.wethinkcode.examples.hangman;

import java.util.*;
import java.io.*;

public class Player {
    private final Scanner scanner;
    private boolean quit;
    private int chances;

    /**
     * Constructor
     * @param in specified input stream
     */
    public Player(InputStream in) {
       this.scanner = new Scanner(in);
       this.quit = false;
       this.chances = 5;
    }

    /**
     * Constructor
     */
    public Player() {
        this.scanner = new Scanner(System.in);
        this.quit = false;
        this.chances = 5;
    }

    /**
     * method to specify get the filename
     * @return String filename
     */
    public String getWordsFile() {
        String filename = this.scanner.nextLine();
        return filename.isBlank() ? "short_words.txt" : filename; 
    }

    /**
     * method to get the input(guess) of the player.
     * @return String guess
     */
    public String getGuess() {
        String text = scanner.nextLine();
        this.quit = text.equalsIgnoreCase("quit") || text.equalsIgnoreCase("exit");                     
        return text;
    }

    /**
     * GETTER for quit (getQuit)
     * @return quit
     */
    public boolean wantsToQuit() {                                                                      
        return this.quit;
    }

    /**
     * method to decrease chances
     */
    public void lostChance() {
        if (!this.hasNoChances()) {                                                                     
            this.chances--;                                                                             
        }
    }

    /**
     * method to check if chances are zero
     * @return true/false
     */
    public boolean hasNoChances() {
        return this.chances == 0;                                                                  
    }

    /**
     * getter for chances
     * @return int chances
     */
    public int getChances() {
        return this.chances;
    }
}