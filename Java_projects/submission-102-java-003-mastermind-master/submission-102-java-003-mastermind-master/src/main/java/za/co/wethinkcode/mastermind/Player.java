package za.co.wethinkcode.mastermind;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.*;

public class Player {
    private final Scanner inputScanner;
    private int guesses;

    public Player(){
        this.inputScanner = new Scanner(System.in);
        this.guesses = 12;
    }

    public Player(InputStream inputStream){
        this.inputScanner = new Scanner(inputStream);
        this.guesses = 12;
    }

    /**
     * Gets a guess from user via text console.
     * This must prompt the user to re-enter a guess until a valid 4-digit string is entered, or until the user enters `exit` or `quit`.
     *
     * @return the value entered by the user
     */
    public String getGuess(){
        String val = "";
        String patt = "[1-8]{4}";
        Pattern p = Pattern.compile(patt);
        while (val.equals("")) {
            // System.out.println("fucking shit");
            System.out.println("Input 4 digit code:");
            val = this.inputScanner.nextLine().strip();

            if (!p.matcher(val).matches()) {
                System.out.println("Please enter exactly 4 digits (each from 1 to 8).");
                val = "";
            }
        }
        // System.out.println("fuck");
        this.guesses--;
        return val;
    }

    public Boolean noGuesses() {
        return this.guesses==0;
    }

    public int getGuesses() {
        return this.guesses;
    }

}
