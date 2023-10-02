package za.co.wethinkcode.examples.hangman;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Hangman {
    /**
     * main method for project
     * @param args args
     * @throws IOException for files
     */
    public static void main(String[] args) throws IOException {
        Player player = new Player();
        Answer wordToGuess = pickWord(player);
        Answer currentAnswer = start(player, wordToGuess);
        String message = run(player, wordToGuess, currentAnswer);                                       
        System.out.println(message);                                                                    
    }


    /**
     * Method to pick random word from file
     * @param player instance of player class
     * @return Answer The selected word in an Aswer Object.
     */
    private static Answer pickWord(Player player) throws IOException {                            
        Random random = new Random();

        System.out.println("Words file? [leave empty to use short_words.txt]");
        String fileName = player.getWordsFile();
        String randomWord;
        List<String> words = Files.readAllLines(Path.of(fileName));

        int randomIndex = random.nextInt(words.size());
        randomWord = words.get(randomIndex).trim();
        
        return new Answer(randomWord);
    }    

    /**
     * Method to start off game, gets the initial blanked out word
     * @param player (redundant)
     * @param wordToGuess (Definitely not cryptic variable name for the 'secret' word)
     * @return Answer object of wordToGuess with a random hint
     */
    private static Answer start(Player player, Answer wordToGuess) {                                    
        Answer currentAnswer = wordToGuess.generateRandomHint();
        System.out.println("Guess the word: " + currentAnswer);
        return currentAnswer;
    }

    /**
     * Game loop.
     * @param player the player instance
     * @param wordToGuess The 'secret' word.
     * @param currentAnswer blanked out word
     * @return String containing status/result of game. 
     */
    private static String run(Player player, Answer wordToGuess, Answer currentAnswer) {                  
        while (!currentAnswer.equals(wordToGuess)) {
            String guess = player.getGuess();
            if (player.wantsToQuit()) {
                return "Bye!";                                                                          
            }

            char guessedLetter = guess.charAt(0);
            if (currentAnswer.isGoodGuess(wordToGuess, guessedLetter)) {                                
                currentAnswer = wordToGuess.getHint(currentAnswer, guess.charAt(0));
                System.out.println(currentAnswer);
            } else {
                player.lostChance();
                System.out.println("Wrong! Number of guesses left: " + player.getChances());
                if (player.hasNoChances()) {
                    return "Sorry, you are out of guesses. The word was: " + wordToGuess;               
                }
            }
        }
        return "That is correct! You escaped the noose .. this time.";                                  
    }
}